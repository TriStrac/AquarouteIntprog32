package com.cansal.aquaroute.storage

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

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
}
