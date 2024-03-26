package com.nevermore.demo.fragments.app_list

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nevermore.demo.R
import com.nevermore.demo.base.Loading
import com.nevermore.demo.base.Success
import com.nevermore.demo.base.XDiffItemCallback
import com.nevermore.demo.databinding.FragmentAppListBinding
import com.nevermore.demo.databinding.ItemAppInfoBinding
import com.nevermore.demo.databinding.ItemAppInfoGridBinding
import kotlinx.coroutines.launch

/**
 * 11.0 获取已安装列表需声明包可见 <queries>
 *
 *
 *
 * @author: xct
 * create on: 2022/8/26 10:48
 *
 */
class AppListFragment : Fragment() {

    private lateinit var viewBinding: FragmentAppListBinding

    private val viewModel by viewModels<AppListViewModel>()

    private val appListAdapter by lazy { AppListAdapter() }

    private val appGridAdapter by lazy { AppGridAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentAppListBinding.inflate(inflater, container, false)
            .also { viewBinding = it }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycle.addObserver(PackageChangeReceiver(requireContext()){

            viewModel.onPackageChanged()

        })
        viewBinding.apply {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = appListAdapter

            floatBtn.setOnClickListener {
                if (it.isSelected) {
                    floatBtn.setImageResource(R.drawable.ic_grid)
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = appListAdapter
                } else {
                    floatBtn.setImageResource(R.drawable.icon_list)
                    recyclerView.layoutManager = GridLayoutManager(context, 5)
                    recyclerView.adapter = appGridAdapter
                }
                it.isSelected = !it.isSelected
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.appList.collect {
                    when (it) {
                        is Success -> {
                            viewBinding.progressBar.isVisible = false
                            appListAdapter.submitList(it.value)
                            appGridAdapter.submitList(it.value)
                            viewBinding.toolBar.title = "已安装App数量:${it.value.size}"
                        }
                        is Loading -> viewBinding.progressBar.isVisible = true
                        else -> viewBinding.progressBar.isVisible = false
                    }

                }
            }
        }
    }

    inner class AppViewHolder(val vb: ItemAppInfoBinding) : RecyclerView.ViewHolder(vb.root)

    inner class AppListAdapter : ListAdapter<AppItem, AppViewHolder>(XDiffItemCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            val binding =
                ItemAppInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AppViewHolder(binding)
        }

        override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
            val item: AppItem = getItem(position)
            holder.vb.apply {
                tvAppName.text = item.appName
                ivAppIcon.setImageDrawable(item.appIcon)
                root.setOnClickListener {
                    item.onItemClick(it.context)
                }
            }
        }
    }

    inner class AppGridViewHolder(val vb: ItemAppInfoGridBinding) : RecyclerView.ViewHolder(vb.root)

    inner class AppGridAdapter : ListAdapter<AppItem, AppGridViewHolder>(XDiffItemCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppGridViewHolder {
            val binding =
                ItemAppInfoGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AppGridViewHolder(binding)
        }

        override fun onBindViewHolder(holder: AppGridViewHolder, position: Int) {
            val item: AppItem = getItem(position)
            holder.vb.apply {
                ivAppIcon.setImageDrawable(item.appIcon)
                root.setOnClickListener {
                    item.onItemClick(it.context)
                }
            }
        }
    }
}