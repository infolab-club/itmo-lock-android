package club.infolab.itmo_lock.domain

import club.infolab.itmo_lock.config.AppConfig
import com.ttlock.bl.sdk.api.TTLockClient
import com.ttlock.bl.sdk.callback.ControlLockCallback
import com.ttlock.bl.sdk.constant.ControlAction
import com.ttlock.bl.sdk.entity.LockError
import io.reactivex.rxjava3.core.Completable

object DoorLock {

    fun unlock(
        tokenLock: String,
        onSuccess: () -> Unit,
        onError: (LockError) -> Unit
    ): Completable =
        Completable.fromAction {
            TTLockClient.getDefault().controlLock(
                ControlAction.UNLOCK,
                tokenLock,
                AppConfig.LOCK_MAC,
                object : ControlLockCallback {
                    override fun onFail(error: LockError) {
                        onError(error)
                    }

                    override fun onControlLockSuccess(
                        lockAction: Int,
                        battery: Int,
                        uniqueId: Int
                    ) {
                        onSuccess()
                    }

                })
    }

    fun lock(token: List<Char>) {
        return
    }
}