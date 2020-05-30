package ru.nowandroid.youtube.rostselmash.profiles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_profile.*
import ru.nowandroid.youtube.rostselmash.preference.MyPreference
import ru.nowandroid.youtube.rostselmash.R
import ru.nowandroid.youtube.rostselmash.activities.ShowStateActivity

class HomeProfileActivity : AppCompatActivity() {

    enum class ProviderType {
        BASIC,
        GOOGLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_profile)

        // SharedPreference - Отпечаток
        val preference = MyPreference(this)
        var loginCount = preference.getLoginCount()

        // SharedPreference - Права
        val preferenceAdmin = MyPreference(this)
        var loginCountAdmin = preferenceAdmin.getLoginCountAdmin()

        // Авторизационные данные
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setupOut(email ?: "", provider ?: "")

        // Сохранение состояния
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        showPanel.setOnClickListener {
            val intent = Intent(this, ShowStateActivity::class.java)
            startActivity(intent)
        }

        // Включение FingerPrint
        onFingerBtn.setOnClickListener{
            loginCount = 2
            preference.setLoginCount(loginCount)
            Toast.makeText(this, "Для запуска авторизации по отпечатку, необходимо перезапустить приложение",Toast.LENGTH_LONG).show()
        }

        // Выключение FingerPrint
        offFingerBtn.setOnClickListener {
            loginCount = 1
            preference.setLoginCount(loginCount)
            Toast.makeText(this, "Для отключения авторизации по отпечатку, необходимо перезапустить приложение",Toast.LENGTH_LONG).show()
        }

        // Выход администратора, без сброса настроект
        adminOutBtn.setOnClickListener {
            // Сохранение состояния
            val prefs =  getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            loginCount = 1
            preference.setLoginCount(loginCount)

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

            Toast.makeText(this, "Отпечаток пальца сброшен, настройки сохранены, войдите в учётную запись для его активации",Toast.LENGTH_LONG).show()
        }

        // Открытие окна настройки прав
        adminRights.setOnClickListener {
            val intent = Intent(this, AdminProfileActivity::class.java)
            startActivity(intent)
        }

        // Показ состояния отпечатка
        if (loginCount == 1) {
            textView4.text = "Выключен"
        }
        if (loginCount == 2) {
            textView4.text = "Включен"
        }
        else {
            textView4.text = "Выключен"
        }
    }

    private fun setupOut(email: String, provider: String) {

        title = "Профиль"

        // SharedPreference - Отпечаток
        val preference = MyPreference(this)
        var loginCount = preference.getLoginCount()

        // SharedPreference - Права
        val preferenceAdmin = MyPreference(this)
        var loginCountAdmin = preferenceAdmin.getLoginCountAdmin()

        emailTextView.text = email
        providerTextView.text = provider

        if (email == "rnd.programmer.mike@gmail.com") {
            adminOutBtn.visibility = View.VISIBLE
            adminRights.visibility = View.VISIBLE
        }
        else {
            adminOutBtn.visibility = View.INVISIBLE
            adminRights.visibility = View.INVISIBLE
        }

        logOutButton.setOnClickListener {

            // Сохранение состояния
            val prefs =  getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()

            // Выключение FingerPrint
            loginCount = 1
            preference.setLoginCount(loginCount)
            // Удаление прав администратора
            loginCountAdmin = 1
            preferenceAdmin.setLoginCountAdmin(loginCountAdmin)

            Toast.makeText(this, "Отпечаток пальца и и настройки доступа сброшены, войдите в учётную запись для повторной настройки",Toast.LENGTH_LONG).show()
        }
    }
}
