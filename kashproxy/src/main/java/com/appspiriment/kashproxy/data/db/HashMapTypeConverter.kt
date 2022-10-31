package com.appspiriment.kashproxy.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object HashMapTypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String?): HashMap<Int, Pair<Int, Boolean>> {
        return value?.let{Gson().fromJson(value, object : TypeToken<HashMap<Int, Pair<Int, Boolean>>>() {}.type)}?: hashMapOf()
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: HashMap<Int, Pair<Int, Boolean>>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
}