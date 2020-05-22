package ru.nowandroid.youtube.rostselmash

import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class NewsActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    // Проверка соединения
    private var context = this
    private var connectivity: ConnectivityManager? = null
    private var connectInfo: NetworkInfo? = null
    private var toastConnectedInfo = ""
    private var toastDisconnectedInfo = "Отсутствует соединение с интернетом"
    private val duration = Toast.LENGTH_SHORT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Проверка на наличие соеднинения
        if (checkConnect()) {

            Toast.makeText(context, toastConnectedInfo, duration)
        } else {

            Toast.makeText(context, toastDisconnectedInfo, duration).show()
            this.finish()
        }

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,  null)
    }

    private fun checkConnect() : Boolean {

        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(connectivity != null) {

            connectInfo = connectivity!!.activeNetworkInfo

            if (connectInfo != null) {

                if (connectInfo!!.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
        }
        return false
    }
}
