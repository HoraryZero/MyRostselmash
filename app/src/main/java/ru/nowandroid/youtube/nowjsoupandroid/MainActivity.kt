package ru.nowandroid.youtube.nowjsoupandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_bottom.*
import ru.nowandroid.youtube.nowjsoupandroid.fragments.DashboardFragment
import ru.nowandroid.youtube.nowjsoupandroid.fragments.ExploreFragment
import ru.nowandroid.youtube.nowjsoupandroid.fragments.NotificationFragment
import ru.nowandroid.youtube.nowjsoupandroid.fragments.ProfileFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // SplashScreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom)

        val exploreFragment = ExploreFragment()
        val dashboardFragment = DashboardFragment()
        val notificationFragment = NotificationFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(exploreFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.explore -> makeCurrentFragment(exploreFragment)
                R.id.dashboard -> makeCurrentFragment(dashboardFragment)
                R.id.notifications -> makeCurrentFragment(notificationFragment)
                R.id.profile -> makeCurrentFragment(profileFragment)

            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, fragment)
                commit()
            }
}





