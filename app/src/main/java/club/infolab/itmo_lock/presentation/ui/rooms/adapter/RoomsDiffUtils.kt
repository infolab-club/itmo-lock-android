package club.infolab.itmo_lock.presentation.ui.rooms.adapter

import androidx.recyclerview.widget.DiffUtil
import club.infolab.itmo_lock.data.entity.Room

class RoomsDiffUtils : DiffUtil.ItemCallback<Room>() {

    override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean =
        oldItem === newItem

    override fun areContentsTheSame(
        oldItem: Room,
        newItem: Room
    ): Boolean = oldItem == newItem
}
