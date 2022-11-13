package com.appspiriment.kashproxy.di

import android.content.Context
import com.appspiriment.kashproxy.data.db.DatabaseObjects
import com.appspiriment.kashproxy.data.db.LocalRoomDataSource
import com.appspiriment.kashproxy.data.preference.isKashProxyMappingEnabled
import com.appspiriment.kashproxy.data.preference.saveKashProxyMappingEnabled
import com.appspiriment.kashproxy.data.repository.ResponseMappingRepository
import com.appspiriment.kashproxy.ui.main.KashProxyActivity
import com.appspiriment.kashproxy.ui.model.MapUrlModel

object KashProxyLib {

    private var databaseObjects: DatabaseObjects? = null
    private var mappingRepository: ResponseMappingRepository? = null

    fun initialize(context: Context) {
        databaseObjects = DatabaseObjects(context).also { dataObj ->
            mappingRepository = ResponseMappingRepository(
                LocalRoomDataSource(dataObj.mappingDao)
            )
        }
    }

    fun showMappingActivity(context: Context, mapModel: MapUrlModel? = null) {
        KashProxyActivity.show(context, mapModel)
    }

    fun getLaunchIntent(context: Context) = KashProxyActivity.getLauncherIntent(context)


    fun enableKashProxyMapping(context: Context, enabled: Boolean) = context.saveKashProxyMappingEnabled(enabled)
    fun isKashProxyMapping(context: Context) = context.isKashProxyMappingEnabled()

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