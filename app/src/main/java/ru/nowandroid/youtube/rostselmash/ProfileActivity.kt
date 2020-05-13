package ru.nowandroid.youtube.rostselmash

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setup()
    }

    private fun setup() {

        title = "Авторизация"

        // Регистрация
        signUpButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailEditText.text.toString(),
                            passwordEditText.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHomeProfile(it.result?.user?.email ?: "", HomeProfileActivity.ProviderType.BASIC)
                            } else {
                                showAlert()
                            }
                        }

            }
        }

        // Вход
        logInButton.setOnClickListener {
            if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {

                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(emailEditText.text.toString(),
                                passwordEditText.text.toString()).addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHomeProfile(it.result?.user?.email ?: "", HomeProfileActivity.ProviderType.BASIC)
                            } else {
                                showAlert()
                            }
                        }
            }
        }
    }

    // Открытие профиля после авторизации
    private fun showHomeProfile(email: String, provider: HomeProfileActivity.ProviderType) {

        val homeIntent = Intent(this, HomeProfileActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }


    // Предупреждение
    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ошибка")
        builder.setMessage("Авторизация пользователя не удалась")
        builder.setPositiveButton("Продолжить", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
