package com.appspiriment.kashproxy.utils.extentions

import org.json.JSONException
import org.json.JSONObject


fun String?.formatToJson(): String? {
    return try {
        this?.let { JSONObject(it).toString(4).trim() }
    } catch (ignored: JSONException) {
        this
    }
}