package com.swasi.utility.ui.messages

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swasi.utility.BR
import com.swasi.utility.databinding.ItemviewSmsBinding

/**
 * Created by Sibaprasad Mohanty on 01/01/2022.
 * sp.dobest@gmail.com
 */
class SmsAdapter : ListAdapter<SmsData, SmsAdapter.SmsViewHolder>(SmsDiffCallBack()) {

    class SmsViewHolder(
        private val binding: ItemviewSmsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: SmsData) {
            binding.setVariable(BR.smsdata, obj)
            binding.executePendingBindings()
        }
    }

        private class SmsDiffCallBack : DiffUtil.ItemCallback<SmsData>() {
        override fun areItemsTheSame(oldItem: SmsData, newItem: SmsData): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: SmsData, newItem: SmsData): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemviewSmsBinding: ItemviewSmsBinding =
            ItemviewSmsBinding.inflate(inflater, parent, false)
        return SmsViewHolder(itemviewSmsBinding)
    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}