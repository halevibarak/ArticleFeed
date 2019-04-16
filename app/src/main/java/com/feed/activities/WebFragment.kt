package com.feed.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.webkit.WebViewClient
import com.feed.R
import kotlinx.android.synthetic.main.fragment_web.*


class WebFragment : Fragment() {

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
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(link)
        webView.setOnKeyListener(View.OnKeyListener {  _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && webView.canGoBack()) {
                webView.goBack()
                return@OnKeyListener true
            }
            false
        })
    }
    companion object {
        const val LINK = "LINK"
    }

}
