package ru.nowandroid.youtube.rostselmash

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home_profile.*
import ru.nowandroid.youtube.rostselmash.activities.ShowStateActivity

class HomeProfileActivity : AppCompatActivity() {

    private var CHANNEL_ID = "MY_ID"

    enum class ProviderType {
        BASIC,
        GOOGLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_profile)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        // Сохранение состояния
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        // Вызов уведомления
        CreateNotificationChannel()
        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification)
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("YouTitle")
                .setSmallIcon(R.drawable.ic_notifications)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        button6.setOnClickListener{
            with(NotificationManagerCompat.from(this)) {
                notify(0, builder.build())
            }
        }

        button4.setOnClickListener {
            val intent = Intent(this, ShowStateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setup(email: String, provider: String) {

        title = "Профиль"

        emailTextView.text = email
        providerTextView.text = provider

        logOutButton.setOnClickListener {

            // Сохранение состояния
            val prefs =  getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    // Функция выдающая PUSH-уведомления
    private fun CreateNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = "App Notification"
            val descriptionText = "This is my discription nofificate"
            val importnace: Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importnace).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }
}
