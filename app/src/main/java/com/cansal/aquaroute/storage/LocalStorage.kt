package com.cansal.aquaroute.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.cansal.aquaroute.models.OrdersForOwnerToCustomer
import com.cansal.aquaroute.models.Subscribers
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalStorage(context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPref = EncryptedSharedPreferences.create(
        "GENERAL",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var appFirstOpen: Boolean
        get() = sharedPref.getBoolean("KEY_FIRSTOPEN", true)
        set(value) = sharedPref.edit().putBoolean("KEY_FIRSTOPEN", value).apply()


    var loggedInEmail: String
        get() = sharedPref.getString("KEY_EMAIL", "")!!
        set(value) = sharedPref.edit().putString("KEY_EMAIL", value).apply()

    var loggedInName: String
        get() = sharedPref.getString("KEY_NAME", "")!!
        set(value) = sharedPref.edit().putString("KEY_NAME", value).apply()

    var loggedInPhoneNumber: String
        get() = sharedPref.getString("KEY_NUMBER", "")!!
        set(value) = sharedPref.edit().putString("KEY_NUMBER", value).apply()

    var loggedInStationName: String?
        get() = sharedPref.getString("KEY_STATION_NAME", "")
        set(value) = sharedPref.edit().putString("KEY_STATION_NAME", value).apply()

    var loggedInAddress: String
        get() = sharedPref.getString("KEY_ADDRESS", "")!!
        set(value) = sharedPref.edit().putString("KEY_ADDRESS", value).apply()


    var ordersPending: Int
        get() = sharedPref.getInt("KEY_PENDING", 0)
        set(value) = sharedPref.edit().putInt("KEY_PENDING", value).apply()

    var ordersCompleted: Int
        get() = sharedPref.getInt("KEY_COMPLETED", 0)
        set(value) = sharedPref.edit().putInt("KEY_COMPLETED", value).apply()

    var jugsInStock: Int
        get() = sharedPref.getInt("KEY_JUGSINSTOCK", 0)
        set(value) = sharedPref.edit().putInt("KEY_JUGSINSTOCK", value).apply()

    private inline fun <reified T> Gson.toJson(src: T): String {
        return toJson(src, object : TypeToken<T>() {}.type)
    }

    private inline fun <reified T> Gson.fromJson(json: String): T {
        return fromJson(json, object : TypeToken<T>() {}.type)
    }

    var ordersList: List<OrdersForOwnerToCustomer>
        get() {
            val json: String? = sharedPref.getString("KEY_ORDER_LIST", null)
            return if (json != null) {
                Gson().fromJson(json)
            } else {
                emptyList()
            }
        }
        set(value) {
            val gson = Gson()
            val json = gson.toJson(value)
            sharedPref.edit().putString("KEY_ORDER_LIST", json).apply()
        }

    var subscriberList: List<Subscribers>
        get() {
            val json: String? = sharedPref.getString("KEY_ORDER_LIST", null)
            return if (json != null) {
                Gson().fromJson(json)
            } else {
                emptyList()
            }
        }
        set(value) {
            val gson = Gson()
            val json = gson.toJson(value)
            sharedPref.edit().putString("KEY_ORDER_LIST", json).apply()
        }

}
