package ru.nowandroid.youtube.nowjsoupandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.activity_bottom.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        // SplashScreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom)

        //Load Fragment
        loadFragment(ExploreFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigationExplore -> {
                    loadFragment(ExploreFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigationDashboard -> {
                    loadFragment(DashboardFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigationProfile -> {
                    loadFragment(ProfileFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        }
    }
}




