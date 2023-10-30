package com.swasi.utility.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.swasi.utility.BR
import com.swasi.utility.databinding.ItemviewImageBinding
import com.swasi.utility.model.ImageModel

/**
 * Created by Sibaprasad Mohanty on 01/01/2022.
 * sp.dobest@gmail.com
 */

class ImageAdapter() :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var imageList: MutableList<ImageModel> = ArrayList()

    class ImageViewHolder(private val binding: ItemviewImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: ImageModel) {
            binding.setVariable(BR.image, obj)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val weatherBinding: ItemviewImageBinding =
            ItemviewImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(weatherBinding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = imageList[position]
        holder.bind(item)
    }

    override fun getItemCount() = imageList.size
}