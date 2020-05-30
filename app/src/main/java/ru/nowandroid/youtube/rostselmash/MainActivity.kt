package ru.nowandroid.youtube.rostselmash

import android.app.Service
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ru.nowandroid.youtube.rostselmash.charts.BarChartActivity
import ru.nowandroid.youtube.rostselmash.charts.PieChartActivity
import ru.nowandroid.youtube.rostselmash.charts.RadarChartActivity
import ru.nowandroid.youtube.rostselmash.profiles.ProfileActivity
import ru.nowandroid.youtube.rostselmash.web.ContactWebActivity
import ru.nowandroid.youtube.rostselmash.web.ProductWebActivity


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // Навигационное меню
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    // Проверка соединения
    private var context = this
    private var connectivity: ConnectivityManager? = null
    private var connectInfo: NetworkInfo? = null
    private var toastConnectedInfo = "Соединение с интернетом установлено"
    private var toastDisconnectedInfo = "Отсутствует соединение с интернетом"
    private val duration = Toast.LENGTH_SHORT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate)

        // Проверка на наличие соеднинения
        if (checkConnect()) {

            Toast.makeText(context, toastConnectedInfo, duration).show()
        } else {

            Toast.makeText(context, toastDisconnectedInfo, duration).show()
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.app_name, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_Profile -> {

                // Открытие окна профиля
                intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_News -> {

                // Открытие окна новостей
                intent = Intent(this, NewsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_ProductWeb -> {

                // Открытие продукции
                intent = Intent(this, ProductWebActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_BarChart -> {

                // Открытие диаграммы производства
                intent = Intent(this, BarChartActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_PieChart -> {

                // Открытие диаграммы соотношения производства
                intent = Intent(this, PieChartActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_RadarChart -> {

                // Открытие диаграммы прибыли предприятия
                intent = Intent(this, RadarChartActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_ContactsInfoWeb -> {

                // Открытие окна контактов предприятия
                intent = Intent(this, ContactWebActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_AboutApp -> {

                // Открытие окна о приложении
                intent = Intent(this, AboutApp::class.java)
                startActivity(intent)
                return true
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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








