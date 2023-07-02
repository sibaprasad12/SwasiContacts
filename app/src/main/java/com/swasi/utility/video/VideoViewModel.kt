package com.swasi.utility.video

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swasi.utility.database.SwasiContactRoomDatabase
import com.swasi.utility.model.VideoEntity
import com.swasi.utility.model.VideoModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoViewModel() : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    // val allContacts: Flow<List<VideoModel>> = repository.allVideos

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(videoModel: VideoModel) = viewModelScope.launch {
//        repository.insert(videoModel)
    }

    val favouriteVideos = MutableLiveData<List<VideoEntity>>()

    fun getAllVideos(context: Context) {
        viewModelScope.launch {
            try {
                val countriesFromDb =
                    SwasiContactRoomDatabase.getDatabase(context).videoDao().getAllVideo()
                favouriteVideos.postValue(countriesFromDb)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

    fun insertAllVideos(context: Context, listVideos: List<VideoEntity>) {
        viewModelScope.launch {
            try {
                SwasiContactRoomDatabase.getDatabase(context).videoDao().insertAllVideos(listVideos)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

    fun updateVideo(context: Context, videoEntity: VideoEntity) {
        viewModelScope.launch {
            try {
                SwasiContactRoomDatabase.getDatabase(context).videoDao().updateVideo(videoEntity)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

    fun insertVideo(context: Context, videoEntity: VideoEntity) {
        viewModelScope.launch {
            try {
                SwasiContactRoomDatabase.getDatabase(context).videoDao().insertVideo(videoEntity)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }

    fun getVideoCount(context: Context) =
        SwasiContactRoomDatabase.getDatabase(context).videoDao().getRowCount()


    fun deleteVideo(context: Context, videoEntity: VideoEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                SwasiContactRoomDatabase.getDatabase(context).videoDao()
                    .deleteByUserId(videoEntity.id)
            } catch (e: Exception) {
                Log.i("TAG", e.message.toString())
            }
        }
    }


}

//class VideoViewModelFactory(private val repository: VideoRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return VideoViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
