package com.nevermore.base.adapter.factory

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.nevermore.base.adapter.BaseRVAdapter
import com.nevermore.base.adapter.BindingViewHolder
import com.nevermore.base.ext.asInstance
import com.nevermore.base.ext.withMatchLayoutParams

//<editor-fold desc="defines">
private const val TAG = "AdapterItem"

typealias RVVH = RecyclerView.ViewHolder
typealias XBaseAdapter = BaseRVAdapter<*, *>
typealias ViewProvider = (Context) -> View
//</editor-fold>

fun interface IVHCreator<out VH : RVVH> {
    fun create(parent: ViewGroup, viewType: Int, adapter: XBaseAdapter): VH
}

inline fun <reified VB : ViewBinding> bindingVHCreator(): IVHCreator<BindingViewHolder<VB>> {
    return IVHCreator { parent, _, _ -> BindingViewHolder.create<VB>(parent) }
}

fun createVHCreator(viewProvider: ViewProvider) =
    IVHCreator<RecyclerView.ViewHolder> { parent, _, _ ->
        object : RecyclerView.ViewHolder(viewProvider(parent.context).also { it.withMatchLayoutParams() }) {}
    }


fun interface IVHBinder<in T, VH : RVVH> {
    fun bind(vh: VH, item: T, position: Int, adapter: XBaseAdapter)
}

fun interface IViewTypeProvider {
    fun getItemViewType(adapter: XBaseAdapter, position: Int): Int
}


//region adapterTypeItem
interface AdapterTypeItem<T, VH : RVVH> : IVHCreator<VH>, IVHBinder<T, VH>, IViewTypeProvider

internal data class AdapterTypeItemWrapper<T, TSub : T, VH : RVVH, VHSub : VH>(
    private val adapterTypeItem: AdapterTypeItem<TSub, VHSub>,
    private val viewType: Int = 0,
) :
    AdapterTypeItem<T, VH> {

    var viewTypeProvider: IViewTypeProvider = IViewTypeProvider { _, _ -> viewType }
    override fun create(parent: ViewGroup, viewType: Int, adapter: XBaseAdapter): VH =
        adapterTypeItem.create(parent, viewType, adapter)


    override fun bind(vh: VH, item: T, position: Int, adapter: XBaseAdapter) {
        adapterTypeItem.bind(vh as VHSub, item as TSub, position, adapter)
    }

    private fun isTypeValid(type: Int) = type != RecyclerView.INVALID_TYPE

    override fun getItemViewType(adapter: XBaseAdapter, position: Int): Int {
        val type = adapterTypeItem.getItemViewType(adapter, position)
        //override
        if (isTypeValid(type)) {
            return viewTypeProvider.getItemViewType(adapter, position)
        }
        return type
    }

}

class MultiTypesItemBuilder<T, VH : RVVH> {
    private val items = mutableSetOf<AdapterTypeItem<T, VH>>()

    private var viewTypeProvider: IViewTypeProvider? = null

    /**
     * 设置自定义viewType,不设置则使用自增长viewType
     */
    fun setViewTypeProvider(vtProvider: IViewTypeProvider) {
        viewTypeProvider = vtProvider
    }

    fun <TSub : T, VHSub : VH> addItem(item: AdapterTypeItem<TSub, VHSub>) {
        items.add(AdapterTypeItemWrapper(item, items.size))
    }

    fun build(): List<AdapterTypeItem<T, VH>> {
        return items.toList().apply {
            viewTypeProvider?.let {
                items.forEach { item ->
                    item.asInstance<AdapterTypeItemWrapper<*, *, *, *>>()?.viewTypeProvider = it
                }
            }
        }
    }
}

data class SimpleAdapterTypeItem<T, VH : RVVH>(
    val vhCreator: IVHCreator<VH>,
    val vhBinder: IVHBinder<T, VH>,
    val vtProvider: IViewTypeProvider
) : AdapterTypeItem<T, VH> {
    override fun create(parent: ViewGroup, viewType: Int, adapter: XBaseAdapter): VH {
        return vhCreator.create(parent, viewType, adapter)
    }

    override fun bind(vh: VH, item: T, position: Int, adapter: XBaseAdapter) {
        vhBinder.bind(vh, item, position, adapter)
    }

    override fun getItemViewType(adapter: XBaseAdapter, position: Int): Int {
        return vtProvider.getItemViewType(adapter, position)
    }
}

data class MultiTypeAdapterTypeItem<T, VH : RVVH>(val items: List<AdapterTypeItem<T, VH>>) : AdapterTypeItem<T, VH> {

    //key:viewType
    private val itemMap = mutableMapOf<Int, AdapterTypeItem<T, VH>>()

    override fun create(parent: ViewGroup, viewType: Int, adapter: XBaseAdapter): VH {
        return itemMap.getOrElse(viewType) {
            Log.e(TAG, "viewType[$viewType] not found in adapter[$adapter]")
            items[0]
        }.create(parent, viewType, adapter)
    }

    override fun bind(vh: VH, item: T, position: Int, adapter: XBaseAdapter) {
        itemMap[vh.itemViewType]?.bind(vh, item, position, adapter)
    }

    override fun getItemViewType(adapter: XBaseAdapter, position: Int): Int {
        var type = RecyclerView.INVALID_TYPE
        for (item in items) {
            type = item.getItemViewType(adapter, position)
            if (type != RecyclerView.INVALID_TYPE) {
                itemMap[type] = item
                break
            }
        }
        if (type == RecyclerView.INVALID_TYPE) {
            throw IllegalStateException(
                "viewType is invalid at position:$position,item:${adapter.getItem(position)} in adapter[${adapter.tag}] "
            )
        }
        return type
    }
}

//<editor-fold desc="functions">
fun <T, VH : RVVH> newSimpleItem(
    vhCreator: IVHCreator<VH>,
    vhBinder: IVHBinder<T, VH>,
    vtProvider: IViewTypeProvider
): AdapterTypeItem<T, VH> = SimpleAdapterTypeItem(vhCreator, vhBinder, vtProvider)


fun <T, VH : RVVH> newItem(
    vhCreator: IVHCreator<VH>,
    vhBinder: IVHBinder<T, VH>,
    viewTypePredictor: (XBaseAdapter, Int) -> Boolean
): AdapterTypeItem<T, VH> = newSimpleItem(vhCreator, vhBinder) { adapter, position ->
    if (viewTypePredictor(adapter, position)) 0 else RecyclerView.INVALID_TYPE
}

inline fun <reified T> instancePredictor(): (XBaseAdapter, Int) -> Boolean =
    { baseRVAdapter, position -> baseRVAdapter.isInstanceAt<T>(position) }


inline fun <reified T> newItemWithViewProvider(
    noinline viewProvider: ViewProvider,
    vhBinder: IVHBinder<T, RVVH> = IVHBinder { _, _, _, _ -> }
): AdapterTypeItem<T, RecyclerView.ViewHolder> =
    newItem(createVHCreator(viewProvider), vhBinder, instancePredictor<T>())

inline fun <reified VB : ViewBinding, reified T> newItemWithVB(
    vhBinder: IVHBinder<T, BindingViewHolder<VB>>
): AdapterTypeItem<T, BindingViewHolder<VB>> =
    newItem(bindingVHCreator(), vhBinder, instancePredictor<T>())
//</editor-fold>
