package com.uts.tamuin166.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uts.tamuin166.data.entity.GuestEntity166
import com.uts.tamuin166.databinding.ItemGuest166Binding
import com.uts.tamuin166.utils.DateHelper166

class GuestAdapter166(
    private val onItemClick166: (GuestEntity166) -> Unit
) : ListAdapter<GuestEntity166, GuestAdapter166.GuestViewHolder166>(DiffCallback166) {

    override fun onCreateViewHolder(parent166: ViewGroup, viewType166: Int): GuestViewHolder166 {
        val binding166 = ItemGuest166Binding.inflate(
            LayoutInflater.from(parent166.context),
            parent166,
            false
        )
        return GuestViewHolder166(binding166)
    }

    override fun onBindViewHolder(holder166: GuestViewHolder166, position166: Int) {
        val guestItem166 = getItem(position166)
        holder166.bind166(guestItem166)
    }

    inner class GuestViewHolder166(
        private val itemBinding166: ItemGuest166Binding
    ) : RecyclerView.ViewHolder(itemBinding166.root) {

        fun bind166(guest166: GuestEntity166) {
            itemBinding166.tvGuestName166.text = guest166.nama_166
            itemBinding166.tvGuestAddress166.text = guest166.alamat_166
            itemBinding166.tvGuestPurpose166.text = guest166.keperluan_166
            itemBinding166.tvGuestTimestamp166.text = DateHelper166.formatTimestamp166(guest166.timestamp_166)

            itemBinding166.root.setOnClickListener {
                onItemClick166(guest166)
            }
        }
    }

    companion object DiffCallback166 : DiffUtil.ItemCallback<GuestEntity166>() {
        override fun areItemsTheSame(
            oldItem166: GuestEntity166,
            newItem166: GuestEntity166
        ): Boolean {
            return oldItem166.id_166 == newItem166.id_166
        }

        override fun areContentsTheSame(
            oldItem166: GuestEntity166,
            newItem166: GuestEntity166
        ): Boolean {
            return oldItem166 == newItem166
        }
    }
}
