package com.swasi.utility.imagepicker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.swasi.utility.BuildConfig
import java.io.File

class MyLifecycleObserver(private val registry: ActivityResultRegistry) : DefaultLifecycleObserver {

    companion object {
        val TAG: String = MyLifecycleObserver.javaClass.name
    }

    private lateinit var getGalleryImageResult: ActivityResultLauncher<String>
    private lateinit var getCameraImageResult: ActivityResultLauncher<Uri>
    private lateinit var tempCameraImageUri: Uri

    override fun onCreate(owner: LifecycleOwner) {

        getGalleryImageResult =
            registry.register("gallery", owner, ActivityResultContracts.GetContent()) { uri ->
                // Handle the returned Uri
                Log.i(TAG, "URI $uri")
            }

        getCameraImageResult =
            registry.register("camera", owner, ActivityResultContracts.TakePicture()) { isSuccess ->
                // Handle the returned Uri
                Log.i(TAG, "URI $tempCameraImageUri")
                if (isSuccess) {
                    tempCameraImageUri.let { uri ->
                        //
                    }
                }
            }
    }

    fun selectImageFromGallery() = getGalleryImageResult.launch("image/*")

    private fun getTmpFileUri(context: Context): Uri {
        val tmpFile =
            File.createTempFile("utility_${System.currentTimeMillis()}", ".png", context.cacheDir)
                .apply {
                    createNewFile()
//            deleteOnExit()
                }

        return FileProvider.getUriForFile(
            context,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    fun captureImage(context: Context) {
        getTmpFileUri(context).let { uri ->
            tempCameraImageUri = uri
            getCameraImageResult.launch(tempCameraImageUri)
        }
    }
}

