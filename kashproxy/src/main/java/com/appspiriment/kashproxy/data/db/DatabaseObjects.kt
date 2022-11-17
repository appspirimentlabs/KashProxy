package com.appspiriment.kashproxy.data.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.equinox.prologix.common.datalocal.db.KashProxyDatabase
import java.util.concurrent.Executors

class DatabaseObjects(context: Context) {

    val appDatabase: KashProxyDatabase = Room.databaseBuilder(context, KashProxyDatabase::class.java, "kashproxy-api")
        .fallbackToDestructiveMigration()
        .setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
            println("SQL Query: $sqlQuery SQL Args: $bindArgs")
        }, Executors.newSingleThreadExecutor())
        .build()

    val mappingDao = appDatabase.mappingDao()
}
