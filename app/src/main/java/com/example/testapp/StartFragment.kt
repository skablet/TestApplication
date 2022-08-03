package com.example.testapp

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapp.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val bindingStart = FragmentStartBinding.inflate(inflater)
        bindingStart.webView.settings.allowContentAccess = true
        bindingStart.webView.settings.allowFileAccess = true
        bindingStart.webView.loadUrl("file:///android_asset/terms.html")


        bindingStart.bAccept.setOnClickListener {
            activity?.getSharedPreferences(activity
                ?.packageName,MODE_PRIVATE)
                ?.edit()?.putBoolean("hasVisited", true)?.apply()

            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, WebViewFragment.newInstance())
                .commit()
        }

        bindingStart.bDecline.setOnClickListener(){
            requireActivity().finish()
        }


        return bindingStart.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = StartFragment()

    }
}
