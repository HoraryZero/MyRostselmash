package ru.nowandroid.youtube.rostselmash.finger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_finger.*
import ru.nowandroid.youtube.rostselmash.MainActivity
import ru.nowandroid.youtube.rostselmash.MyPreference
import ru.nowandroid.youtube.rostselmash.R

class FingerActivity : AppCompatActivity() {

    private val TAG = FingerActivity::getLocalClassName.toString()

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var biometricManager: BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {

        // Splashscreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        // SharedPreference
        val preference = MyPreference(this)
        var loginCount = preference.getLoginCount()

        if (loginCount == 2) {setContentView(R.layout.activity_finger)

            biometricManager = BiometricManager.from(this)
            val executor = ContextCompat.getMainExecutor(this)

            checkBiometricStatus(biometricManager)

            biometricPrompt = BiometricPrompt(this,executor,
                    object : BiometricPrompt.AuthenticationCallback(){
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            super.onAuthenticationError(errorCode, errString)
                            showToast("Аутентификация не удалась, причина: $errString")
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            goToHomeActivity()
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            showToast("Аутентификация не удалась")
                        }
                    })


            promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Биометрическая авторизация в приложении")
                    .setDescription("Используйте биометрический датчик для авторизации")
                    .setNegativeButtonText("Использовать Email")
                    .build()

            login.setOnClickListener {
                biometricPrompt.authenticate(promptInfo)
            }
        }
        else {
            goToHomeActivity()
        }
    }

    private fun showToast(message : String){
        Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
    }

    // Need opened MainActivity (need Fix Bag - Navigation Menu)
    private fun goToHomeActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun checkBiometricStatus(biometricManager: BiometricManager){
        when(biometricManager.canAuthenticate()){
            BiometricManager.BIOMETRIC_SUCCESS->
                Log.d(TAG, "checkBiometricStatus: App can use biometric authenticate")

            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->
                Log.e(TAG, "checkBiometricStatus: No biometric features available in this device")

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->
                Log.e(TAG, "checkBiometricStatus: Biometric features currently unavailable")

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED->
                Log.e(TAG, "checkBiometricStatus: The user has'nt enrolled with any biometric configuration"+
                        " in this device")
        }
    }
}

