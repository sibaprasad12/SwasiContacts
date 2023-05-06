package com.swasi.utility.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


/**
 * Created by Sibaprasad Mohanty on 16/04/2023.
 * siba.x.prasad@gmail.com
 */

@Parcelize
@Entity(tableName = "video_table")
class VideoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var videoName: String = "",
    var videoUrl: String = "",
    var videoThumbnailUrl: String = ""
) : Parcelable