package ru.nowandroid.youtube.rostselmash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_profile.*
import ru.nowandroid.youtube.rostselmash.activities.ShowStateActivity

class HomeProfileActivity : AppCompatActivity() {

    enum class ProviderType {
        BASIC,
        GOOGLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_profile)

        // SharedPreference
        val preference = MyPreference(this)
        var loginCount = preference.getLoginCount()

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setupOut(email ?: "", provider ?: "")

        // Сохранение состояния
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        button4.setOnClickListener {
            val intent = Intent(this, ShowStateActivity::class.java)
            startActivity(intent)
        }

        button6.setOnClickListener{
            // Включение FingerPrint
            loginCount = 2
            preference.setLoginCount(loginCount)
        }

        button7.setOnClickListener {
            // Выключение FingerPrint
            loginCount = 1
            preference.setLoginCount(loginCount)
        }

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

        // SharedPreference
        val preference = MyPreference(this)
        var loginCount = preference.getLoginCount()

        emailTextView.text = email
        providerTextView.text = provider

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
        }
    }
}
