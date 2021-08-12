package club.infolab.itmo_lock.ui.lock

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.lock.DoorLock
import club.infolab.itmo_lock.lock.LockStatus

class LockViewModel : ViewModel() {
    val lockedStatus = MutableLiveData(LockStatus.LOCKED)

    init {
        DoorLock.status.observeForever {
            Log.d("VIEWMODEL", it.toString())
            lockedStatus.value = it
        }
    }

    fun unlock() {
        DoorLock.unlock(arrayListOf())
    }
}