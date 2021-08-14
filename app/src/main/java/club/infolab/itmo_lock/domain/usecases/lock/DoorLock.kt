package club.infolab.itmo_lock.domain.usecases.lock

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData

object DoorLock : Lock {
    var status = MutableLiveData(LockStatus.LOCKED)

    init {
        Log.d("LOCK", "LOCKED")
    }

    override fun unlock(token: List<Char>) {
        status.value = LockStatus.WAITING
        Log.d("LOCK", "WAITING 2sec")
        Handler(Looper.getMainLooper()).postDelayed( {
            status.value = LockStatus.UNLOCKED
            Log.d("LOCK", "UNLOCKED")
        }, 3000)
//
//        sleep(10000)
//        status.value = LockStatus.LOCKED
//        Log.d("LOCK", "LOCKED")

    }

    override fun lock(token: List<Char>) {
        return
    }
}