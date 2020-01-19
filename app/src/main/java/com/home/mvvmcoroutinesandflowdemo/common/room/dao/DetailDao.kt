package com.home.mvvmcoroutinesandflowdemo.common.room.dao

import androidx.room.*
import com.home.mvvmcoroutinesandflowdemo.common.room.models.DetailEntity
import com.home.mvvmcoroutinesandflowdemo.common.room.models.DetailModel

@Dao
interface DetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailEntity(detailEntity: DetailEntity)

    @Query("DELETE FROM DetailEntity")
    suspend fun deleteDetailEntity()

    @Transaction
    suspend fun deleteDetailAndInsert(detailModel: DetailModel) {
        deleteDetailEntity()
        detailModel.list.forEach {
            insertDetailEntity(it)
        }
    }

    @Transaction
    @Query("SELECT * FROM DetailEntity")
    suspend fun getDetailList(): List<DetailEntity>?
}