package com.swasi.utility.utils

import android.content.Context
import android.os.Environment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Sibaprasad Mohanty on 02/01/2022.
 * Spm Limited
 * sp.dobest@gmail.com
 */

class ImageUtils {
    companion object {
        @Throws(IOException::class)
        fun createTempImageFile(context: Context): File {
            // Create an image file name
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            return File.createTempFile(
                "WorkSafe_$timeStamp", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )
        }
    }
}