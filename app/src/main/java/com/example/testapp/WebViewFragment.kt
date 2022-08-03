package com.example.testapp

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val bindingWeb = FragmentWebViewBinding.inflate(inflater)

        bindingWeb.webViewFrag.settings.javaScriptEnabled = true
        val url = activity?.getSharedPreferences(activity?.packageName,MODE_PRIVATE)
            ?.getString(PREF_URL, "https://dou.ua/")
        bindingWeb.webViewFrag.loadUrl(url!!)


        val myWebView = bindingWeb.webViewFrag
        myWebView.webViewClient = object :WebViewClient(){

            override fun onPageFinished(view: WebView?, url: String?){
                super.onPageFinished(view, url)
                activity?.getSharedPreferences(activity?.packageName,MODE_PRIVATE)?.edit()
                    ?.putString(PREF_URL, url)?.apply()
            }
        }


        myWebView.canGoBack()
        myWebView.setOnKeyListener(View.OnKeyListener
        { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                && event.action == MotionEvent.ACTION_UP
                && myWebView.canGoBack()) {
                myWebView.goBack()

                return@OnKeyListener true
            }
            false
        })
        return bindingWeb.root
    }


    companion object {
        fun newInstance(): WebViewFragment{
            return WebViewFragment()
        }
    }
}




