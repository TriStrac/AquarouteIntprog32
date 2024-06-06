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
        get() = sharedPref.getBoolean("KEY_FIRST_OPEN", true)
        set(value) = sharedPref.edit().putBoolean("KEY_FIRST_OPEN", value).apply()


    var loggedInEmail: String
        get() = sharedPref.getString("KEY_EMAIL", "")!!
        set(value) = sharedPref.edit().putString("KEY_EMAIL", value).apply()

    var loggedInName: String
        get() = sharedPref.getString("KEY_NAME", "")!!
        set(value) = sharedPref.edit().putString("KEY_NAME", value).apply()

    var loggedInPhoneNumber: String
        get() = sharedPref.getString("KEY_NUMBER", "")!!
        set(value) = sharedPref.edit().putString("KEY_NUMBER", value).apply()

    var loggedInStationName: String
        get() = sharedPref.getString("KEY_STATION_NAME", "")!!
        set(value) = sharedPref.edit().putString("KEY_STATION_NAME", value).apply()

    var loggedInRegion: String
        get() = sharedPref.getString("KEY_REGION", "")!!
        set(value) = sharedPref.edit().putString("KEY_REGION", value).apply()
    var loggedInStreet: String
        get() = sharedPref.getString("KEY_STREET", "")!!
        set(value) = sharedPref.edit().putString("KEY_STREET", value).apply()
    var loggedInUnit: String
        get() = sharedPref.getString("KEY_UNIT", "")!!
        set(value) = sharedPref.edit().putString("KEY_UNIT", value).apply()

    var customerFilledJugs: String
        get() = sharedPref.getString("KEY_CUSTOMER_FILLED_JUGS", "")!!
        set(value) = sharedPref.edit().putString("KEY_CUSTOMER_FILLED_JUGS", value).apply()

    var customerJugsInStations: String
        get() = sharedPref.getString("KEY_CUSTOMER_STATION_JUGS", "")!!
        set(value) = sharedPref.edit().putString("KEY_CUSTOMER_STATION_JUGS", value).apply()

    var customerTotalJugs: String
        get() = sharedPref.getString("KEY_CUSTOMER_TOTAL_JUGS", "")!!
        set(value) = sharedPref.edit().putString("KEY_CUSTOMER_TOTAL_JUGS", value).apply()

    var ownerOrdersPending: String
        get() = sharedPref.getString("KEY_OWNER_PENDING", "")!!
        set(value) = sharedPref.edit().putString("KEY_OWNER_PENDING", value).apply()

    var ownerOrdersCompleted: String
        get() = sharedPref.getString("KEY_OWNER_COMPLETED", "")!!
        set(value) = sharedPref.edit().putString("KEY_OWNER_COMPLETED", value).apply()

    var ownerJugsInStock: String
        get() = sharedPref.getString("KEY_OWNER_JUGS_IN_STOCK", "")!!
        set(value) = sharedPref.edit().putString("KEY_OWNER_JUGS_IN_STOCK", value).apply()


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
            val json: String? = sharedPref.getString("KEY_SUBS_LIST", null)
            return if (json != null) {
                Gson().fromJson(json)
            } else {
                emptyList()
            }
        }
        set(value) {
            val gson = Gson()
            val json = gson.toJson(value)
            sharedPref.edit().putString("KEY_SUBS_LIST", json).apply()
        }

}
