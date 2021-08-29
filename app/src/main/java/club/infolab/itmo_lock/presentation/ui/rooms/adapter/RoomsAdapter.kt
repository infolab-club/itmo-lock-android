package club.infolab.itmo_lock.presentation.ui.rooms.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import club.infolab.itmo_lock.R
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.databinding.ItemRoomBinding

class RoomsAdapter : RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {

    private var rooms = AsyncListDiffer(this, RoomsDiffUtils())

    fun setData(data: List<Room>) {
        rooms.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        return RoomsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_room,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        Log.d("BIND", "bind pos $position")
        holder.binding.roomNumber.text = rooms.currentList[position].number
        holder.binding.goToRoom.setOnClickListener {
            onClickGoToRoom(rooms.currentList[position], holder)
        }

        holder.binding.root.setOnClickListener {
            onClickGoToRoom(rooms.currentList[position], holder)
        }
    }

    private fun onClickGoToRoom(room: Room, holder: RoomsViewHolder) {
        holder.binding.root.findNavController().navigate(R.id.action_mainFragment_to_lockFragment)
    }

    override fun getItemCount(): Int {
        return rooms.currentList.size
    }

    class RoomsViewHolder(val binding: ItemRoomBinding) : RecyclerView.ViewHolder(binding.root)
}
