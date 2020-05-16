package ru.nowandroid.youtube.rostselmash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ru.nowandroid.youtube.rostselmash.charts.BarChartActivity
import ru.nowandroid.youtube.rostselmash.charts.PieChartActivity
import ru.nowandroid.youtube.rostselmash.charts.RadarChartActivity
import ru.nowandroid.youtube.rostselmash.web.ContactWebActivity
import ru.nowandroid.youtube.rostselmash.web.ProductWebActivity


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {

        // SplashScreen
//        Thread.sleep(2000)
//        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigate)

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

                // Open Activity
                intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_News -> {

                // Open Activity
                intent = Intent(this, NewsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_ProductWeb -> {

                // Open Activity
                intent = Intent(this, ProductWebActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_BarChart -> {

                // Open Activity
                intent = Intent(this, BarChartActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_PieChart -> {

                // Open Activity
                intent = Intent(this, PieChartActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_RadarChart -> {

                // Open Activity
                intent = Intent(this, RadarChartActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_ContactsInfoWeb -> {

                // Open Activity
                intent = Intent(this, ContactWebActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.nav_AboutApp -> {

                // Open Activity
                intent = Intent(this, AboutApp::class.java)
                startActivity(intent)
                return true
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}








