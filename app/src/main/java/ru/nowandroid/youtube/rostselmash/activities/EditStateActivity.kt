package ru.nowandroid.youtube.rostselmash.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.AdapterView
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_edit_state.*
import ru.nowandroid.youtube.rostselmash.R
import ru.nowandroid.youtube.rostselmash.models.State
import ru.nowandroid.youtube.rostselmash.preference.MyPreference
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


class EditStateActivity : AppCompatActivity() {

    // Канал Push-уведомлений
    private var CHANNEL_ID = "MY_ID"

    // Объект ветки Firebase
    companion object {
        val EXTRA_NOTE = "state"
    }

    // Проверка соединения
    private var context = this
    private var connectivity: ConnectivityManager? = null
    private var connectInfo: NetworkInfo? = null
    private var toastConnectedInfo = "Соединение с интернетом установлено"
    private var toastDisconnectedInfo = "Отсутствует соединение с интернетом"
    private val duration = Toast.LENGTH_SHORT

    // Данные для работы с RealTime Database Firebase
    private val STORAGE_CODE: Int = 100
    lateinit var state: State
    lateinit var mDb: DatabaseReference
    private val userIDFB = "rLRL99WV0MOKMGBR8o9hNirH3162"

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ShowToast", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_state)

        // Проверка на наличие соеднинения
        if (checkConnect()) {
            Toast.makeText(context, toastConnectedInfo, duration)
        } else {
            Toast.makeText(context, toastDisconnectedInfo, duration).show()
            this.finish()
        }

        // SharedPreference - Права
        val preferenceAdmin = MyPreference(this)
        val loginCountAdmin = preferenceAdmin.getLoginCountAdmin()

        // SharedPreference - Данные авторизованного пользователя
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)

        if (!intent.hasExtra(EXTRA_NOTE)) finish()

        state = intent.getSerializableExtra(EXTRA_NOTE) as State
        stateTitle.text?.append(state.title)
        stateContent.text?.append(state.content)

        // DataBase
        mDb = FirebaseDatabase.getInstance().reference

        // Выдача времени с датой ($formatted)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formatted = current.format(formatter)

        // Предоставление доступа к элементам управления
        when {
            loginCountAdmin == 2 -> {
                saveBtn.visibility = View.VISIBLE
            }
            email == "rnd.programmer.mike@gmail.com" -> {
                saveBtn.visibility = View.VISIBLE
            }
            loginCountAdmin == 1 -> {
                saveBtn.visibility = View.INVISIBLE
            }
            else -> {
                saveBtn.visibility = View.INVISIBLE
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

                state = intent.getSerializableExtra(EXTRA_NOTE) as State
                stateTitle.text?.append(state.title)
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val nameItem = adapterView?.getItemAtPosition(position).toString()
                val inputEditTextBox : String = "$nameItem - $formatted"

                readSpinnerBtn.setOnClickListener {
                    state = intent.getSerializableExtra(EXTRA_NOTE) as State
                    stateTitle.text?.clear()
                    stateTitle.text?.append(inputEditTextBox)
                }
            }
        }

        // Вызов Push-уведомления
        CreateNotificationChannel()
        val notificationLayout = RemoteViews(packageName, R.layout.custom_notification)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("YouTitle")
                .setSmallIcon(R.drawable.ic_notifications)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        // Сохранение записи и вызов Push-уведомления
        saveBtn.setOnClickListener {
           //
            with(NotificationManagerCompat.from(this)) {
                notify(0, builder.build())
            }
            save()
        }

        saveBtnPDF.setOnClickListener {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                //
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

                    val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                }
                else {
                    savePDF()
                }
            }
            else {
                savePDF()
            }
        }
    }

    private fun savePDF() {
        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        val mFilePath =  Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))

            mDoc.open()

            val mText = stateContent.text.toString()

            mDoc.addAuthor("Ростсельмаш")
            mDoc.add(Paragraph(mText))
            mDoc.close()
            Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath",Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            STORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePDF()
                }
                else {
                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun save() {
        state.title = stateTitle.text.toString()
        state.content = stateContent.text.toString()

        val uid = userIDFB
        val path = "users/$uid/state"
        val key = if (state.id == "") mDb.child(path).push().key else state.id
        val childUpdates: MutableMap<String, Any> = HashMap()

        childUpdates["$path/$key"] = state.toMap()
        mDb.updateChildren(childUpdates).addOnCompleteListener { onSaveComplete() }
    }

    private fun onSaveComplete() {
        Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show()
        finish()
    }

    // Функция выдающая Push-уведомления
    private fun CreateNotificationChannel() {
        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //
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
