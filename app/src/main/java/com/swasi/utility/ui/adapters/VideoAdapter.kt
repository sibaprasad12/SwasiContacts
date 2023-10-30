package com.swasi.utility.ui.adapters

import android.view.*
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.swasi.utility.BR
import com.swasi.utility.databinding.ItemviewVideoBinding
import com.swasi.utility.listeners.VideoClickListener
import com.swasi.utility.model.VideoEntity

/**
 * Created by Sibaprasad Mohanty on 01/01/2022.
 * sp.dobest@gmail.com
 */

class VideoAdapter(val listener: VideoClickListener) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var contactList: MutableList<VideoEntity> = ArrayList()

    class VideoViewHolder(
        private val binding: ItemviewVideoBinding,
        listener: VideoClickListener) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {

        var entity: VideoEntity? = null

        fun bind(obj: VideoEntity, listener: VideoClickListener) {
            binding.root.setOnCreateContextMenuListener(this)
            binding.setVariable(BR.videoModel, obj)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            p1: View?,
            p2: ContextMenu.ContextMenuInfo?
        ) {
            val edit: MenuItem? = menu?.add(Menu.NONE, 1, 1, "Edit")
            val delete: MenuItem? = menu?.add(Menu.NONE, 2, 2, "Delete")
            edit?.setOnMenuItemClickListener(onEditMenu)
            delete?.setOnMenuItemClickListener(onEditMenu)
        }

        private val onEditMenu: MenuItem.OnMenuItemClickListener =
            MenuItem.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    1 -> {
                        listener.onVideoEdit(entity!!)
                    }
                    2 -> {
                        listener.onVideoDelete(entity!!)
                    }
                }
                true
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val weatherBinding: ItemviewVideoBinding =
            ItemviewVideoBinding.inflate(inflater, parent, false)
        return VideoViewHolder(weatherBinding, listener)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item, listener)
    }

    override fun getItemCount() = differ.currentList.size

    private val weatherDiffCallback: DiffUtil.ItemCallback<VideoEntity> =
        object : DiffUtil.ItemCallback<VideoEntity>() {
            override fun areItemsTheSame(
                oldWeather: VideoEntity,
                newWeather: VideoEntity
            ): Boolean {
                return oldWeather.videoName == newWeather.videoName
            }

            override fun areContentsTheSame(
                oldWeather: VideoEntity,
                newWeather: VideoEntity
            ): Boolean {
                return oldWeather.videoName == newWeather.videoName
            }
        }

    private val differ: AsyncListDiffer<VideoEntity> =
        AsyncListDiffer(this, weatherDiffCallback)

    fun setVideos(contacts: MutableList<VideoEntity>) {
        if (contacts.isNotEmpty()) {
            contactList = contacts
            differ.submitList(contacts)
        }
    }
}