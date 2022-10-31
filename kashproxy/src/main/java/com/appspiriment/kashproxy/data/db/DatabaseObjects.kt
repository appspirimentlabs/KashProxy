package com.appspiriment.kashproxy.data.db

import android.content.Context
import androidx.room.Room
import com.equinox.prologix.common.datalocal.db.KashProxyDatabase

class DatabaseObjects(context: Context) {

    val appDatabase: KashProxyDatabase = Room.databaseBuilder(context, KashProxyDatabase::class.java, "kashproxy-api")
        .fallbackToDestructiveMigration()
        .build()

    val mappingDao = appDatabase.mappingDao()
}