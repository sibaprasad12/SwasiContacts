package com.swasi.utility.utils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.swasi.utility.R
import com.swasi.utility.model.ContactEntity
import java.io.File


/**
 * Created by Sibaprasad Mohanty on 04/01/2022.
 * Spm Limited
 * sp.dobest@gmail.com
 */

object BindingAdapters {

    @BindingAdapter("android:src")
    fun setImageUri(view: AppCompatImageView, imageUri: String?) {
        if (imageUri == null) {
            view.setImageURI(null)
        } else {
            view.setImageURI(Uri.parse(imageUri))
        }
    }

    @BindingAdapter("android:src")
    fun setImageUri(view: AppCompatImageView, imageUri: Uri?) {
        view.setImageURI(imageUri)
    }

    @BindingAdapter("android:src")
    fun setImageDrawable(view: AppCompatImageView, drawable: Drawable?) {
        view.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(AppCompatImageView: AppCompatImageView, resource: Int) {
        Log.i("setImageResource", "$resource")
        try {
            AppCompatImageView.setImageResource(resource)
        } catch (e: Exception) {
            Log.i("", "")
        }
    }

    @JvmStatic
    @BindingAdapter("swasiImage")
    fun setImageDrawable(AppCompatImageView: AppCompatImageView, contactEntity: ContactEntity) {
        if (contactEntity.imagePath.isNotEmpty()) {
            Picasso.get().load(Uri.fromFile(File(contactEntity.imagePath))).into(AppCompatImageView)
        } else {
            AppCompatImageView.setImageResource(contactEntity.drawable)
        }
    }

    @JvmStatic
    @BindingAdapter("swasiImage123")
    fun setImageResourceFromLocalStorage(
        AppCompatImageView: AppCompatImageView,
        currentPhotoPath: String
    ) {
        if (currentPhotoPath.isNotEmpty()) {
            Picasso.get().load(Uri.fromFile(File(currentPhotoPath))).into(AppCompatImageView)
        }
    }

    @JvmStatic
    @BindingAdapter("imageResource")
    fun setImageResourceByValue(appCompatImageView: AppCompatImageView, resourceId: Int) {
        appCompatImageView.setImageResource(resourceId)
    }

    @JvmStatic
    @BindingAdapter("videoImage")
    fun loadImage(imageview: AppCompatImageView, imageUrl: String) {
        Log.i("TAG", imageUrl)
        Picasso.get()
            .load(imageUrl)
            .error(R.mipmap.ic_launcher_round)
            .into(imageview)
    }
}