package com.appspiriment.kashproxy.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.appspiriment.kashproxy.R

const val KEY_MAPPING_ENABLED = "kashProxyMappingEnabled"

fun Context.getKashProxyPrefs() : SharedPreferences {
    return getSharedPreferences(getString(R.string.kash_pref_file_name), Context.MODE_PRIVATE)
}

fun Context.saveStringPrefs(prefName: String, prefValue: String?) {
    getKashProxyPrefs().edit().apply {
        putString(prefName, prefValue)
    }.apply()
}

fun Context.saveBoolPrefs(prefName: String, prefValue: Boolean) {
    getKashProxyPrefs().edit().apply {
        putBoolean(prefName, prefValue)
    }.apply()
}

fun Context.saveIntPrefs(prefName: String, prefValue: Int) {
    getKashProxyPrefs().edit().apply {
        putInt(prefName, prefValue)
    }.apply()
}

fun Context.saveLongPrefs(prefName: String, prefValue: Long) {
    getKashProxyPrefs().edit().apply {
        putLong(prefName, prefValue)
    }.apply()
}

fun Context.saveFloatPrefs(prefName: String, prefValue: Float) {
    getKashProxyPrefs().edit().apply {
        putFloat(prefName, prefValue)
    }.apply()
}

fun Context.deletePref(prefName: String){
    getKashProxyPrefs().edit().remove(prefName).apply()
}

fun Context.getAllMappings(): List<String> {
    return getKashProxyPrefs().all.values.filterNotNull().filterIsInstance(
        String::class.java
    )
}

fun Context.getStringPrefs(prefName: String?, defValue: String?): String? {
    return prefName?.let {
        getKashProxyPrefs().getString(prefName, defValue)
    }
}

fun Context.getBoolPrefs(prefName: String, defValue: Boolean): Boolean {
    return getKashProxyPrefs().getBoolean(prefName, defValue)
}

fun Context.getIntPrefs(prefName: String, defValue: Int): Int {
    return getKashProxyPrefs().getInt(
        prefName,
        defValue
    )
}

fun Context.getLongPrefs(prefName: String, defValue: Long): Long {
    return getKashProxyPrefs().getLong(
        prefName,
        defValue
    )
}

fun Context.getFloatPrefs(prefName: String, defValue: Float): Float {
    return getKashProxyPrefs().getFloat(
        prefName,
        defValue
    )
}


fun Context.saveKashProxyMappingEnabled(enabled: Boolean){
    saveBoolPrefs(KEY_MAPPING_ENABLED, enabled)
}

fun Context.isKashProxyMappingEnabled() = getBoolPrefs(KEY_MAPPING_ENABLED, false)
