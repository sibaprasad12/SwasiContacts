package com.swasi.utility.ui.video

import androidx.annotation.WorkerThread
import com.swasi.utility.ui.database.VideoDao
import com.swasi.utility.model.VideoModel

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class VideoRepository(private val videoDao: VideoDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    //  val allVideos: Flow<List<VideoModel>> = videoDao.getAllVideo()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(video: VideoModel) {
        // videoDao.insert(video)
    }
}
