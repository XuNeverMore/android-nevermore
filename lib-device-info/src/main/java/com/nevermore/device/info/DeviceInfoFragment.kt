package com.nevermore.device.info

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nevermore.device.info.utils.DeviceUtil
import java.util.Arrays

/**
 * 设备信息
 */
class DeviceInfoFragment : Fragment() {

    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return RecyclerView(inflater.context).also {
            rv = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ctx = view.context
        rv.apply {
            layoutManager = LinearLayoutManager(ctx)
            addItemDecoration(
                DividerItemDecoration(
                    ctx,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = InfoAdapter().apply {
                submitList(
                    getInfoList(ctx)
                )
            }

        }
    }


    private fun getDeviceDPI(context: Context) {
        val windowManager = ContextCompat.getSystemService(context, WindowManager::class.java)
        windowManager ?: return
        windowManager.defaultDisplay
    }


    private fun getInfoList(ctx: Context) = mutableListOf(
        "screenSize" to DeviceUtil.getMetrics(ctx).let { "${it.widthPixels}x${it.heightPixels}" },
        "dpi" to DeviceUtil.getMetrics(ctx)
            .let { metrics -> "xdpi:${metrics.xdpi},ydpi:${metrics.ydpi}\ndensity:${metrics.density},densityDpi:${metrics.densityDpi}" },

        Pair("IP地址", DeviceUtil.getIP(ctx)),
        "arc" to Arrays.toString(Build.SUPPORTED_ABIS),
        Pair("Build.MODEL", Build.MODEL),
        Pair("Build.BOARD", Build.BOARD),
        Pair("Build.MANUFACTURER", Build.MANUFACTURER),
        Pair("Build.BRAND", Build.BRAND),
        "Android系统版本" to
                "${Build.VERSION.SDK_INT},${Build.VERSION.CODENAME},${Build.VERSION.RELEASE}",
    )

    inner class InfoAdapter :
        ListAdapter<Pair<String, String>, ViewHolder>(object :
            DiffUtil.ItemCallback<Pair<String, String>>() {
            override fun areItemsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: Pair<String, String>,
                newItem: Pair<String, String>
            ): Boolean {
                return oldItem == newItem
            }
        }) {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_info, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val pair = getItem(position)
            holder.apply {
                tvTitle.text = pair.first
                tvInfo.text = pair.second
            }
        }

    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val tvInfo = itemView.findViewById<TextView>(R.id.tv_info)
    }
}