package com.uts.tamuin159.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.uts.tamuin159.data.entity.GuestEntity159
import com.uts.tamuin159.databinding.ItemGuest159Binding
import com.uts.tamuin159.utils.DateHelper159

class GuestAdapter159(
    private val onItemClick159: (GuestEntity159) -> Unit
) : ListAdapter<GuestEntity159, GuestAdapter159.GuestViewHolder159>(DiffCallback159) {

    override fun onCreateViewHolder(parent159: ViewGroup, viewType159: Int): GuestViewHolder159 {
        val binding159 = ItemGuest159Binding.inflate(
            LayoutInflater.from(parent159.context),
            parent159,
            false
        )
        return GuestViewHolder159(binding159)
    }

    override fun onBindViewHolder(holder159: GuestViewHolder159, position159: Int) {
        val guestItem159 = getItem(position159)
        holder159.bind159(guestItem159)
    }

    inner class GuestViewHolder159(
        private val itemBinding159: ItemGuest159Binding
    ) : RecyclerView.ViewHolder(itemBinding159.root) {

        fun bind159(guest159: GuestEntity159) {
            itemBinding159.tvGuestName159.text = guest159.nama_159
            itemBinding159.tvGuestAddress159.text = guest159.alamat_159
            itemBinding159.tvGuestPurpose159.text = guest159.keperluan_159
            itemBinding159.tvGuestTimestamp159.text = DateHelper159.formatTimestamp159(guest159.timestamp_159)

            itemBinding159.root.setOnClickListener {
                onItemClick159(guest159)
            }
        }
    }

    companion object DiffCallback159 : DiffUtil.ItemCallback<GuestEntity159>() {
        override fun areItemsTheSame(
            oldItem159: GuestEntity159,
            newItem159: GuestEntity159
        ): Boolean {
            return oldItem159.id_159 == newItem159.id_159
        }

        override fun areContentsTheSame(
            oldItem159: GuestEntity159,
            newItem159: GuestEntity159
        ): Boolean {
            return oldItem159 == newItem159
        }
    }
}
