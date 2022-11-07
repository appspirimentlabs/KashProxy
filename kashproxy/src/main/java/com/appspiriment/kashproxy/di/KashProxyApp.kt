package com.appspiriment.kashproxy.di

import android.content.Context
import android.content.Intent
import com.appspiriment.kashproxy.data.db.DatabaseObjects
import com.appspiriment.kashproxy.data.db.LocalRoomDataSource
import com.appspiriment.kashproxy.data.repository.ResponseMappingRepository
import com.appspiriment.kashproxy.network.KashProxyInterceptor
import com.appspiriment.kashproxy.ui.main.KashProxyActivity
import com.appspiriment.kashproxy.ui.model.MapUrlModel

object KashProxyApp {

    private var databaseObjects: DatabaseObjects? = null
    private var mappingRepository: ResponseMappingRepository? = null

    fun initialize(context: Context) {
        databaseObjects = DatabaseObjects(context).also { dataObj ->
            mappingRepository = ResponseMappingRepository(
                LocalRoomDataSource(dataObj.mappingDao)
            )
        }
    }

    fun showMappingActivity(context: Context, mapModel:MapUrlModel? = null) {
        KashProxyActivity.show(context, mapModel)
    }

    fun getInterceptor(context: Context) = KashProxyInterceptor(context)

    internal fun getDatabaseObjects(): DatabaseObjects {
        return databaseObjects ?: run {
            throw RuntimeException("Library not properly initialized. Please call 'KashProxyApp.initialize(context)' in Application Class")
        }
    }

    internal fun getMappingRepository(): ResponseMappingRepository {
        return mappingRepository ?: run {
            throw RuntimeException("Library not properly initialized. Please call 'KashProxyApp.initialize(context)' in Application Class")
        }
    }
}