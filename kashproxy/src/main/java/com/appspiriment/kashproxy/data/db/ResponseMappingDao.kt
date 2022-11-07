package com.appspiriment.kashproxy.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ResponseMappingDao {
    /***************************************
     * Insert Items
     ***************************************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProxyMapping(proxyMappingEntity: ResponseMappingEntity)

    @Query("DELETE FROM responseMapping WHERE apiUrl=:apiUrl")
    suspend fun deleteProxyMapping(apiUrl: String)

    @Query("SELECT * FROM responseMapping")
    suspend fun getAllMapping(): List<ResponseMappingEntity>

    @Query("SELECT * FROM responseMapping WHERE apiUrl = :url LIMIT 1")
    suspend fun getMappingByUrl(url: String): ResponseMappingEntity?

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