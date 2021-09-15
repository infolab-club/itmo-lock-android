package club.infolab.itmo_lock.domain

import com.ttlock.bl.sdk.api.TTLockClient
import com.ttlock.bl.sdk.callback.ControlLockCallback
import com.ttlock.bl.sdk.constant.ControlAction
import com.ttlock.bl.sdk.entity.ControlLockResult
import com.ttlock.bl.sdk.entity.LockError

object DoorLock {

    fun unlock(
        mac: String,
        tokenLock: String,
        onSuccess: () -> Unit,
        onError: (LockError) -> Unit
    ) {
        TTLockClient.getDefault().controlLock(
            ControlAction.UNLOCK,
            tokenLock,
            mac,
            object : ControlLockCallback {
                override fun onFail(error: LockError) {
                    onError(error)
                }

                override fun onControlLockSuccess(p0: ControlLockResult?) {
                    onSuccess()
                }
            })
    }

    fun lock(token: List<Char>) {
        return
    }
}