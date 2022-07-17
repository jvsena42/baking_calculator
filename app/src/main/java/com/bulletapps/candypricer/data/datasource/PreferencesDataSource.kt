package com.bulletapps.candypricer.data.datasource

import android.content.SharedPreferences

class PreferencesDataSource(private val sharedPreferences: SharedPreferences) {

    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    fun <T: Any> getPref(key: String, defaultValue: T): T {
        return when (defaultValue::class.java.name) {
            java.lang.String::class.java.name -> sharedPreferences.getString(key, defaultValue as String)
            java.lang.Integer::class.java.name -> sharedPreferences.getInt(key, defaultValue as Int)
            java.lang.Boolean::class.java.name ->  sharedPreferences.getBoolean(key, defaultValue as Boolean)
            java.lang.Float::class.java.name ->  sharedPreferences.getFloat(key, defaultValue as Float)
            java.lang.Long::class.java.name ->  sharedPreferences.getLong(key, defaultValue as Long)
            else -> throw Exception("Unhandled returning type")
        } as T
    }

    fun setPref(key: String, value: Any) =
        when (value::class.java.name) {
            String::class.java.name -> sharedPreferences.edit().putString(key,value as String).apply()
            java.lang.Integer::class.java.name -> sharedPreferences.edit().putInt(key, value as Int).apply()
            java.lang.Boolean::class.java.name -> sharedPreferences.edit().putBoolean(key, (value as Boolean)).apply()
            java.lang.Float::class.java.name -> sharedPreferences.edit().putFloat(key, value as Float).apply()
            java.lang.Long::class.java.name -> sharedPreferences.edit().putLong(key, value as Long).apply()
            else -> throw Exception("Error trying to fetch ${value::class} type -> Unhandled returning type")
        }

    fun clearPref() {
        sharedPreferences.edit().clear().apply()
    }

    fun removeValue(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}