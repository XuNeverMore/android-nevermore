package com.nevermore.demo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.webkit.WebViewAssetLoader
import com.nevermore.base.utils.UtilStatusBar
import com.nevermore.base.webview.LocalContentWebViewClient
import com.nevermore.base.webview.bindWebViewToLifecycle
import com.nevermore.demo.databinding.FragmentWebviewBinding

/**
 *
 * @author: xct
 * create on: 2022/8/23 19:08
 *
 */
class WebViewFragment:Fragment() {
    private lateinit var viewBinding: FragmentWebviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentWebviewBinding.inflate(inflater, container, false)
            .also { viewBinding = it }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UtilStatusBar.setTranslucentStatus(requireActivity())
        initWebView()
    }

    private fun initWebView() {
        val web = viewBinding.web
        setAllowLocalContent(web)
        val urlIndex = "https://appassets.androidplatform.net/assets/effect/index.html"
        web.loadUrl(urlIndex)
        bindWebViewToLifecycle(web, lifecycle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        UtilStatusBar.clearTranslucentStatus(requireActivity())
    }

    fun setAllowLocalContent(webView: WebView) {
        WebView.setWebContentsDebuggingEnabled(true)
        val loader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(requireContext()))
            .build()
        webView.settings.apply {
            javaScriptEnabled = true
        }
        webView.webViewClient = LocalContentWebViewClient(loader)
    }
}