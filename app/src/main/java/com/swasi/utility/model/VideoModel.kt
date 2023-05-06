package com.swasi.utility.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_table")
data class VideoModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val url: String,
    val drawable: Int,
    val imageUrl: String
)
