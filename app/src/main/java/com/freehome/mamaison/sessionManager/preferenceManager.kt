package com.freehome.mamaison.sessionManager

import android.content.Context
import com.freehome.mamaison.models.responseFolder.AuthResponse

class SharedPrefManager private constructor(private val mCtx: Context) {

     val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id", -1) != -1
        }

    val client: AuthResponse
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return AuthResponse(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("nomClient", null),
                sharedPreferences.getString("prenomClient", null),
                sharedPreferences.getString("emailClient", null),
                sharedPreferences.getString("telephoneClient", null),
                sharedPreferences.getString("mot_de_passClient", null)
            )
        }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            val mInstance:SharedPrefManager = SharedPrefManager(mCtx)
            return  mInstance
        }

    }

}