package ru.nowandroid.youtube.rostselmash.profiles

import android.annotation.SuppressLint
import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_admin_profile.*
import ru.nowandroid.youtube.rostselmash.R
import ru.nowandroid.youtube.rostselmash.preference.MyPreference

class AdminProfileActivity : AppCompatActivity() {

    // Проверка соединения
    private var context = this
    private var connectivity: ConnectivityManager? = null
    private var connectInfo: NetworkInfo? = null
    private var toastConnectedInfo = ""
    private var toastDisconnectedInfo = "Отсутствует соединение с интернетом"
    private val duration = Toast.LENGTH_SHORT

    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile)

        // SharedPreference - Права
        val preferenceAdmin = MyPreference(this)
        var loginCountAdmin = preferenceAdmin.getLoginCountAdmin()

        // Проверка на наличие соеднинения
        if (checkConnect()) {
            Toast.makeText(context, toastConnectedInfo, duration)
        } else {
            Toast.makeText(context, toastDisconnectedInfo, duration).show()
            this.finish()
        }

        onRootBtn.setOnClickListener {
            loginCountAdmin = 2
            preferenceAdmin.setLoginCountAdmin(loginCountAdmin)
            finish()
            Toast.makeText(this, "Права администратора доступны, необходимо перезапустить приложение и авторизироваться",Toast.LENGTH_LONG).show()
        }

        offRootBtn.setOnClickListener {
            loginCountAdmin = 1
            preferenceAdmin.setLoginCountAdmin(loginCountAdmin)
            finish()
            Toast.makeText(this, "Права администратора заблокированы, необходимо перезапустить приложение и авторизироваться",Toast.LENGTH_LONG).show()
        }

        // Показ состояния отпечатка
        if (loginCountAdmin < 2) {
            textView6.text = "Заблокировано"
        }
        if (loginCountAdmin == 2) {
            textView6.text = "Доступно"
        }
        else {
            textView6.text = "Заблокировано"
        }
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