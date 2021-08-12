package club.infolab.itmo_lock.ui.rooms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.model.Room
import club.infolab.itmo_lock.repository.Repository

class RoomsViewModel(val repo: Repository) : ViewModel() {
    var rooms = MutableLiveData<ArrayList<Room>>()
    var viewingRooms: ArrayList<Room>? = null

    init {
        updateData()
    }

    fun updateData() {
        rooms.value = repo.getAccessibleRooms()
    }

    fun getRoomsData() : ArrayList<Room> {
        return rooms.value!!
    }
}