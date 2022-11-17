package com.equinox.prologix.common.datalocal.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appspiriment.kashproxy.data.db.ResponseMappingDao
import com.appspiriment.kashproxy.data.db.ResponseMappingEntity


/*********************************************************
 * Class   :  AppDatabase
 * Author  :  Arun Nair
 * Created :  12/4/21
 *******************************************************
 * Purpose :  Application Database class
 *******************************************************
 * Rework Details:
 * 1) {Author} :  {Date} : {Details}
 *********************************************************/
@Database(entities = [ResponseMappingEntity::class],     version = 2)
abstract class KashProxyDatabase internal constructor () : RoomDatabase() {
    abstract fun mappingDao() : ResponseMappingDao
}
