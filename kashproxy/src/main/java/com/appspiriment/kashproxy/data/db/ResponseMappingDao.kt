package com.appspiriment.kashproxy.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel

@Dao
interface ResponseMappingDao {
    /***************************************
     * Insert Items
     ***************************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProxyMapping(proxyMappingEntity: ResponseMappingEntity)

    @Query("DELETE FROM responseMapping WHERE mappingId=:id")
    suspend fun deleteProxyMapping(id: Int)

    @Query("SELECT * FROM responseMapping")
    suspend fun getAllMapping(): List<ResponseMappingEntity>

    @Query("SELECT * FROM responseMapping WHERE mappingId = :id LIMIT 1")
    suspend fun getMappingById(id: Int): ResponseMappingEntity

    @Query(
        "SELECT * FROM responseMapping WHERE " +
                "protocol = :protocol AND " +
                "apiHost = :host "
    )
    suspend fun getMappingByHost(
        protocol: String,
        host: String
    ): List<ResponseMappingEntity>

}