package club.infolab.itmo_lock.domain

import club.infolab.itmo_lock.config.AppConfig
import com.ttlock.bl.sdk.api.TTLockClient
import com.ttlock.bl.sdk.callback.ControlLockCallback
import com.ttlock.bl.sdk.constant.ControlAction
import com.ttlock.bl.sdk.entity.ControlLockResult
import com.ttlock.bl.sdk.entity.LockError
import io.reactivex.rxjava3.core.Completable

object DoorLock {

    fun unlock(
        tokenLock: String,
        onSuccess: () -> Unit,
        onError: (LockError) -> Unit
    ): Completable =
        Completable.fromCallable {
            TTLockClient.getDefault().controlLock(
                ControlAction.UNLOCK,
                AppConfig.LOCK_DATA,
                AppConfig.LOCK_MAC,
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