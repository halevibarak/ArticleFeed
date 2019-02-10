package com.feed.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import com.feed.R


class WebFragment : Fragment() {
    private var mWebView: WebView? = null

    val link: String
        get() {
            val bundle = this.arguments
            var link = ""
            if (bundle != null) {
                link = bundle.getString(LINK, "")

            }
            return link
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
                R.layout.fragment_web, container, false)
        retainInstance = true

        mWebView = rootView.findViewById(R.id.web_view2)
        val webSettings = mWebView!!.settings
        webSettings.javaScriptEnabled = true
        mWebView!!.webViewClient = WebViewClient()
        mWebView!!.loadUrl(link)
        mWebView!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && mWebView!!.canGoBack()) {
                mWebView!!.goBack()
                return@OnKeyListener true
            }
            false
        })
        return rootView
    }

    companion object {
        const val LINK = "LINK"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mWebView=null
    }
}
