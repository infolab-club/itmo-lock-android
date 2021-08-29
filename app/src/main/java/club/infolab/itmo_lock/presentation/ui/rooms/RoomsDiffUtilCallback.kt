package club.infolab.itmo_lock.presentation.ui.rooms

import androidx.recyclerview.widget.DiffUtil
import club.infolab.itmo_lock.data.entity.Room

class RoomsDiffUtilCallback(
    private val oldList: List<Room>,
    private val newList: List<Room>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}