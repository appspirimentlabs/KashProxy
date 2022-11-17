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

    @Query("DELETE FROM responseMapping WHERE mappingId=:mappigId")
    suspend fun deleteProxyMapping(mappigId: Int)

    @Query("SELECT * FROM responseMapping ORDER BY apiUrl")
    suspend fun getAllMapping(): List<ResponseMappingEntity>

    @Query("SELECT * FROM responseMapping WHERE apiUrl=:url  LIMIT 1")
    suspend fun getMappingByUrl(url: String): ResponseMappingEntity?


    @Query("SELECT * FROM responseMapping WHERE apiUrl=:url AND mappingEnabled = 1 LIMIT 1")
    suspend fun getEnabledMappingByUrl(url: String): ResponseMappingEntity?

    @Query("SELECT * FROM responseMapping WHERE mappingId=:mappingId  LIMIT 1")
    suspend fun getMappingById(mappingId: Int): ResponseMappingEntity?

    @Query( "SELECT * FROM responseMapping WHERE " +
            "protocol = :protocol AND " +
            "apiHost = :host AND " +
            "path LIKE :path AND " +
            "queries LIKE :queries AND mappingEnabled = 1 LIMIT 1")
    suspend fun getMappingByHostPathAndQueries(
        protocol: String,
        host: String,
        path: String,
        queries: String
    ): ResponseMappingEntity?

    @Query( "SELECT * FROM responseMapping WHERE " +
            "protocol = :protocol AND " +
            "apiHost = :host AND " +
            "path LIKE :path AND " +
            "queries IS NULL AND mappingEnabled = 1 LIMIT 1")
    suspend fun getMappingByHostPath(
        protocol: String,
        host: String,
        path: String
    ): ResponseMappingEntity?

    @Query(
        "SELECT * FROM responseMapping WHERE " +
                "protocol = :protocol AND " +
                "apiHost = :host AND " +
                "path IS NULL AND " +
                "queries IS NULL AND mappingEnabled = 1 LIMIT 1")
    suspend fun getMappingByHost(
        protocol: String,
        host: String
    ): ResponseMappingEntity?

}
