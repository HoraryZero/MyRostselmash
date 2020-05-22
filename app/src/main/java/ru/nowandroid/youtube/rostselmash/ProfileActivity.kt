package ru.nowandroid.youtube.rostselmash

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100

    // Проверка соединения
    private var context = this
    private var connectivity: ConnectivityManager? = null
    private var connectInfo: NetworkInfo? = null
    private var toastConnectedInfo = ""
    private var toastDisconnectedInfo = "Отсутствует соединение с интернетом"
    private val duration = Toast.LENGTH_SHORT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Проверка на наличие соеднинения
        if (checkConnect()) {

            Toast.makeText(context, toastConnectedInfo, duration)
        } else {

            Toast.makeText(context, toastDisconnectedInfo, duration).show()
            this.finish()
        }

        setup()
        session()
    }

    override fun onStart() {
        super.onStart()

        authLayout.visibility = View.VISIBLE
    }

    private fun session() {

        // Проверка авторизации
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null) {
            authLayout.visibility = View.INVISIBLE
            showHomeProfile(email, HomeProfileActivity.ProviderType.valueOf(provider))
        }
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

        // Вход Email
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

        // Вход Google
        googleButton.setOnClickListener {

            // Config
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {

                val account = task.getResult(ApiException::class.java)

                if (account != null) {

                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHomeProfile(account.email ?: "", HomeProfileActivity.ProviderType.GOOGLE)
                        } else {
                            showAlert()
                        }
                    }
                }
            } catch (e: ApiException) {
                showAlert()
            }
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
