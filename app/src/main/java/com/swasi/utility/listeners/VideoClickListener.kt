package com.swasi.utility.listeners

import com.swasi.utility.model.VideoEntity
import com.swasi.utility.model.VideoModel


/**
 * Created by Sibaprasad Mohanty on 04/01/2022.
 * Spm Limited
 * sp.dobest@gmail.com
 */

interface VideoClickListener {
    fun onVideoClick(video: VideoEntity)
    fun onVideoEdit(videoEntity: VideoEntity)
    fun onVideoDelete(videoEntity: VideoEntity)
}