package club.infolab.itmo_lock.ui.rooms

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.databinding.RoomItemBinding
import club.infolab.itmo_lock.model.Room

class RoomsAdapter() : RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {
    var rooms: ArrayList<Room>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        return RoomsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.room_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        Log.d("BIND", "bind pos $position")
        holder.binding.roomNumber.text = rooms!![position].number
        holder.binding.goToRoom.setOnClickListener {
            onClickGoToRoom(rooms!![position])
        }
    }

    private fun onClickGoToRoom(room: Room) {

    }

    override fun getItemCount(): Int {
        return rooms?.size ?: 0
    }

    class RoomsViewHolder(val binding: RoomItemBinding) : RecyclerView.ViewHolder(binding.root)
}
