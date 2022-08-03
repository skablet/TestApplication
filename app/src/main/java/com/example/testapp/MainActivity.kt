package com.example.testapp

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appsflyer.AppsFlyerLib
import com.example.testapp.databinding.ActivityMainBinding
import com.onesignal.OneSignal


const val PREF_URL = "PREF_URL"
const val ONESIGNAL_APP_ID = "c761acc9-f434-4610-a1f3-880105f44d27"
const val AF_DEV_KEY = "aXGAReAmuXByFjBgYJfLwf"



class MainActivity : AppCompatActivity() {

    private lateinit var checkNetworkConnection: CheckNetworkConnection
    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)


        AppsFlyerLib.getInstance().init(AF_DEV_KEY, null, this)
        AppsFlyerLib.getInstance().start(this)


        isNetworkAvailable()
        callNetworkConnection()


        if (!isNetworkAvailable()) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, NoInternetFragment())
                .commit()
        }
    }



    private fun callNetworkConnection() {
        val sp: SharedPreferences = getSharedPreferences(packageName,MODE_PRIVATE)
        val hasVisited = sp.getBoolean("hasVisited", false)
        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (!isConnected) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, NoInternetFragment())
                    .commit()
            }else if (!hasVisited) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, StartFragment())
                    .commit()
            } else {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame, WebViewFragment())
                    .commit()
            }
        }
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetwork
        return activeNetworkInfo != null
    }
}








