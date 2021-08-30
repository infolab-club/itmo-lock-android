package club.infolab.itmo_lock.presentation.ui.lock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.domain.DoorLock
import club.infolab.itmo_lock.domain.LockStatus

class LockViewModel : ViewModel() {

    val lockedStatus = MutableLiveData(LockStatus.LOCKED)

    var room: Room? = null

    init {
        DoorLock.status.observeForever {
            lockedStatus.value = it
        }
    }

    fun unlock() {
        DoorLock.unlock(arrayListOf())
    }

    fun setRoomData(room: Room) {
        this.room = room
    }
}
