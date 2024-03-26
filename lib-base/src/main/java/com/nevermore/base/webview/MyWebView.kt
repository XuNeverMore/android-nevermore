package com.nevermore.base.webview

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.KeyEvent
import android.webkit.*
import androidx.annotation.RequiresApi


/**
 *
 * @author: xct
 * create on: 2022/8/17 14:15
 *
 */
class MyWebView : WebView {
    private var isNeedExe = true

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        settings.apply {
            setSupportZoom(false)
            builtInZoomControls = false
            defaultTextEncodingName = "utf-8"
            javaScriptEnabled = true
            defaultFontSize = 16
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            setGeolocationEnabled(true) //允许访问地址

            //允许访问多媒体
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            isVerticalScrollBarEnabled = false

            //加载https的兼容
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //两者都可以
                mixedContentMode = mixedContentMode
                setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            //先加载页面再加载图片，这里先禁止图片加载
            loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19

        }
        setVerticalScrollbarOverlay(false)
        isHorizontalScrollBarEnabled = false
        setHorizontalScrollbarOverlay(false)
        overScrollMode = OVER_SCROLL_NEVER
        isFocusable = true
        isHorizontalScrollBarEnabled = false
        isDrawingCacheEnabled = true

        webViewClient = mWebViewClient
        webChromeClient = mWebChromeClient
    }

    var mWebViewClient: WebViewClient = object : WebViewClient() {
        //https ssl证书问题，如果没有https的问题可以注释掉
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler,
            error: SslError?
        ) {
            // 接受所有网站的证书,Google不通过
            //使用下面的兼容写法
            val mHandler: SslErrorHandler
            mHandler = handler
            val builder: AlertDialog.Builder = AlertDialog.Builder(getContext())
            builder.setMessage("SSL validation failed")
            builder.setPositiveButton("Continue") { dialog, which ->
                mHandler.proceed()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                mHandler.cancel()
            }
            builder.setOnKeyListener(object : DialogInterface.OnKeyListener {
                override fun onKey(
                    dialog: DialogInterface?,
                    keyCode: Int,
                    event: KeyEvent?
                ): Boolean {
                    if (event != null && event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        mHandler.cancel()
                        dialog?.dismiss()
                        return true
                    }
                    return false

                }

            })
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        //页面加载完成，展示图片
        override fun onPageFinished(view: WebView?, url: String?) {
            if (!settings.getLoadsImagesAutomatically()) {
                settings.setLoadsImagesAutomatically(true)
            }
        }

        //在当前的webview中跳转到新的url
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (mListener != null) mListener!!.onInnerLinkChecked()
            if (Build.VERSION.SDK_INT < 26) {
                if (!TextUtils.isEmpty(url)) {
                    view.loadUrl(url)
                }
                return true
            }
            return false
        }

        //WebView加载错误的回调
        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            if (mListener != null) mListener!!.onWebLoadError()
        }

        //拦截WebView中的网络请求
        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest?
        ): WebResourceResponse? {
            return super.shouldInterceptRequest(view, request)
        }
    }
    var mWebChromeClient: WebChromeClient = object : WebChromeClient() {
        //获取html的title标签
        override fun onReceivedTitle(view: WebView?, title: String?) {
            if (mListener != null) mListener!!.titleChange(title)
            super.onReceivedTitle(view, title)
        }



        //获取页面加载的进度
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (mListener != null) mListener!!.progressChange(newProgress)
            super.onProgressChanged(view, newProgress)
            if (newProgress > 95 && isNeedExe) {
                isNeedExe = !isNeedExe
                if (newProgress == 100) {
                    //注入js代码测量webview高度
                    loadUrl("javascript:App.resize(document.body.getBoundingClientRect().height)")
                }
            }
        }

        // 指定源的网页内容在没有设置权限状态下尝试使用地理位置API。
        override fun onGeolocationPermissionsShowPrompt(
            origin: String?,
            callback: GeolocationPermissions.Callback
        ) {
            val allow = true // 是否允许origin使用定位API
            val retain = false // 内核是否记住这次制授权
            callback.invoke(origin, true, false)
        }

        // 之前调用 onGeolocationPermissionsShowPrompt() 申请的授权被取消时，隐藏相关的UI。
        override fun onGeolocationPermissionsHidePrompt() {}

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri?>?>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            //启动系统相册
//            CommUtils.getHandler().post {
//                if (mFilesListener != null) mFilesListener!!.onWebFileSelect(
//                    filePathCallback
//                )
//            }
            return true
        }
    }

    //网页状态的回调相关处理
    private var mListener: OnWebChangeListener? = null

    interface OnWebChangeListener {
        fun titleChange(title: String?)
        fun progressChange(progress: Int)
        fun onInnerLinkChecked()
        fun onWebLoadError()
    }

    fun setOnWebChangeListener(listener: OnWebChangeListener?) {
        mListener = listener
    }

    //网页选择图片文件的回调相关处理
    private var mFilesListener: OnWebChooseFileListener? = null

    interface OnWebChooseFileListener {
        fun onWebFileSelect(callback: ValueCallback<Array<Uri?>?>?)
    }

    fun setOnWebChooseFileListener(listener: OnWebChooseFileListener?) {
        mFilesListener = listener
    }

    /**
     * 暴露方法，是否滑动到底部
     */
    fun isScrollBottom(): Boolean {
        return contentHeight * scale == 1f * (height + scrollY)
    }
}