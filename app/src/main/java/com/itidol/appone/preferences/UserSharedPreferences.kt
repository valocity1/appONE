package com.itidol.appone.preferences

import android.content.Context
import com.itidol.appone.constants.Constants

class UserSharedPreferences(context: Context)  {
    val preferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
    val editor = preferences.edit()

    fun setUserLoginCredentials(name: String, email: String, password: String) {
        editor.putString(Constants.USER_NAME, name)
        editor.putString(Constants.USER_EMAIL, email)
        editor.putString(Constants.USER_PASSWORD, password)
        editor.commit()
    }

    fun getUserLoginCredentials(): HashMap<String, String> {
        val map = HashMap<String, String>()
        map.put(Constants.USER_NAME, preferences.getString("name", "").toString())
        map.put(Constants.USER_EMAIL, preferences.getString("email", "").toString())
        map.put(Constants.USER_PASSWORD, preferences.getString("password", "").toString())
        return map
    }
}