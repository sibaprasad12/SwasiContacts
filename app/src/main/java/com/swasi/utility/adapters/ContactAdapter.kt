package com.swasi.utility.adapters

import android.view.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.swasi.utility.BR
import com.swasi.utility.databinding.ItemviewContactBinding
import com.swasi.utility.listeners.ContactClicklistener
import com.swasi.utility.model.ContactEntity

/**
 * Created by Sibaprasad Mohanty on 01/01/2022.
 * sp.dobest@gmail.com
 */

class ContactAdapter(val listener: ContactClicklistener) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var contactList: MutableList<ContactEntity> = ArrayList()

    class ContactViewHolder(
        private val binding: ItemviewContactBinding,
        listener: ContactClicklistener
    ) : RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener {
        var entity: ContactEntity? = null

        fun bind(obj: ContactEntity, listener: ContactClicklistener) {
            binding.root.setOnCreateContextMenuListener(this)
            binding.setVariable(BR.contact, obj)
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
                        listener.onEditClick(entity)
                    }
                    2 -> {
                        listener.onDeleteClick(entity)
                    }
                }
                true
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val weatherBinding: ItemviewContactBinding =
            ItemviewContactBinding.inflate(inflater, parent, false)
        return ContactViewHolder(weatherBinding, listener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.entity = item
        holder.bind(item, listener)
    }

    override fun getItemCount() = differ.currentList.size

    private val weatherDiffCallback: DiffUtil.ItemCallback<ContactEntity> =
        object : DiffUtil.ItemCallback<ContactEntity>() {
            override fun areItemsTheSame(
                oldWeather: ContactEntity,
                newWeather: ContactEntity
            ): Boolean {
                return oldWeather.name == newWeather.name
            }

            override fun areContentsTheSame(
                oldWeather: ContactEntity,
                newWeather: ContactEntity
            ): Boolean {
                return oldWeather.name == newWeather.name
            }
        }

    private val differ: AsyncListDiffer<ContactEntity> =
        AsyncListDiffer(this, weatherDiffCallback)

    fun setContactList(contacts: MutableList<ContactEntity>) {
        if (contacts.isNotEmpty()) {
            contactList = contacts
            differ.submitList(contacts)
        }
    }
}