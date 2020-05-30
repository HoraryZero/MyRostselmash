package ru.nowandroid.youtube.rostselmash.preference

import android.content.Context

class MyPreference (context: Context) {

    // Отпечаток
    val PREFERENCE_NAME = "SharedPreferenceExample"
    val PREFERENCE_LOGIN_COUNT = "LoginCount"
    //
    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getLoginCount() : Int{
        return preference.getInt(PREFERENCE_LOGIN_COUNT,0)
    }

    fun setLoginCount(count:Int) {
        val editor = preference.edit()
        editor.putInt(PREFERENCE_LOGIN_COUNT, count)
        editor.apply()
    }

    // Права доступа
    val PREFERENCE_NAME_ADMIN = "SharedPreferenceExampleAdmin"
    val PREFERENCE_LOGIN_COUNT_ADMIN = "LoginCountAdmin"
    //
    val preferenceAdmin = context.getSharedPreferences(PREFERENCE_NAME_ADMIN, Context.MODE_PRIVATE)

    fun getLoginCountAdmin() : Int{
        return preferenceAdmin.getInt(PREFERENCE_LOGIN_COUNT_ADMIN, 1)
    }

    fun setLoginCountAdmin(count:Int) {
        val editor = preferenceAdmin.edit()
        editor.putInt(PREFERENCE_LOGIN_COUNT_ADMIN, count)
        editor.apply()
    }
}