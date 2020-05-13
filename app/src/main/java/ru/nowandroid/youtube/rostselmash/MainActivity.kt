package ru.nowandroid.youtube.rostselmash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_bottom.*
import ru.nowandroid.youtube.rostselmash.fragments.DashboardFragment
import ru.nowandroid.youtube.rostselmash.fragments.ExploreFragment
import ru.nowandroid.youtube.rostselmash.fragments.NotificationFragment
import ru.nowandroid.youtube.rostselmash.fragments.ProfileFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // SplashScreen
        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        //Analytics Event
        val analytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("message", "Open app")
        analytics.logEvent("InitScreen", bundle)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

// Bottom Navigation

//        val exploreFragment = ExploreFragment()
//        val dashboardFragment = DashboardFragment()
//        val notificationFragment = NotificationFragment()
//        val profileFragment = ProfileFragment()
//
//        makeCurrentFragment(exploreFragment)
//
//        menu_bottom.setOnItemSelectedListener {id ->
//            when (id) {
//
//                R.id.explore -> makeCurrentFragment(exploreFragment)
//                R.id.dashboard -> makeCurrentFragment(dashboardFragment)
//                R.id.notifications -> makeCurrentFragment(notificationFragment)
//                R.id.profile -> makeCurrentFragment(profileFragment)
//            }
//            true
//        }
    }

//    private fun makeCurrentFragment(fragment: Fragment) =
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.fl_wrapper, fragment)
//                commit()
//            }
}








