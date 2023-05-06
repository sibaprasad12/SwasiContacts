package com.swasi.utility.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.swasi.utility.model.VideoEntity

@Dao
interface VideoDao {
    
    @Query("SELECT * FROM video_table ORDER BY id ASC")
    suspend fun getAllVideo(): List<VideoEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVideo(VideoEntity: VideoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllVideos(contactList: List<VideoEntity>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateVideo(VideoEntity: VideoEntity)

    @Query("DELETE FROM video_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM video_table")
    fun getRowCount(): LiveData<Int?>?

    @Query("DELETE FROM video_table WHERE id = :userId")
    fun deleteByUserId(userId: Int)
}
