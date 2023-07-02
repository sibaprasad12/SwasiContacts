package com.swasi.utility.utils

import android.content.Context
import com.swasi.utility.R
import com.swasi.utility.model.ContactEntity
import com.swasi.utility.model.ImageModel
import com.swasi.utility.model.VideoEntity
import com.swasi.utility.model.VideoModel

object ContactManager {
    fun getContactList(context: Context): MutableList<ContactEntity> {
        var listContact = mutableListOf<ContactEntity>()
        val contactNameArray = context.resources.getStringArray(R.array.contact_names)
        val contactNumberArray = context.resources.getStringArray(R.array.contact_numbers)
        val contactDrawableArray =
            context.resources.obtainTypedArray(R.array.contact_drawable_array)
        contactNameArray.forEachIndexed { index, s ->
            listContact.add(
                ContactEntity(
                    index + 1,
                    s,
                    contactNumberArray[index],
                    contactDrawableArray.getResourceId(index, -1),
                    ""
                )
            )
        }
        return listContact
    }

    fun getMyFavouriteVideoList(context: Context): MutableList<VideoEntity> {
        var listVideos = mutableListOf<VideoEntity>()
        val videoNameArray = context.resources.getStringArray(R.array.video_name)
        val videoUrlArray = context.resources.getStringArray(R.array.video_urls)
        videoUrlArray.forEachIndexed { index, s ->
            val imageUrl = getThumbnailUrl(s)
            listVideos.add(
                VideoEntity(
                    index,
                    videoNameArray[index],
                    videoUrlArray[index],
                    imageUrl
                )
            )
        }
        return listVideos
    }

    private fun getThumbnailUrl(url: String): String {
//        var imageUrl = "https://img.youtube.com/vi/<insert-youtube-video-id-here>/0.jpg"
        val index = url.indexOf("=")
        val imageThumbNail =
            "https://img.youtube.com/vi/${url.substring(index + 1, index + 12)}/0.jpg"
        return imageThumbNail
    }

    fun getImageList(context: Context): MutableList<ImageModel> {
        var listImage = mutableListOf<ImageModel>()

        val imageDrawableArray = context.resources.obtainTypedArray(R.array.contact_drawable_array)
        for (i in 1..19) {
            listImage.add(ImageModel(imageDrawableArray.getResourceId(i, -1)))
        }
        return listImage
    }
}