package club.infolab.itmo_lock.presentation.ui.rooms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.data.repository.Repository

class RoomsViewModel(private val repo: Repository) : ViewModel() {
    val rooms = MutableLiveData<List<Room>>()
    var viewingRooms: List<Room>? = null

    init {
        updateData()
    }

    fun updateData() {
        rooms.value = repo.getAccessibleRooms()
    }
}