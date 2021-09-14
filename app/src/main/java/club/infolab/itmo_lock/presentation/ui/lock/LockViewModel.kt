package club.infolab.itmo_lock.presentation.ui.lock

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.data.entity.KeyObj
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.data.entity.UserInfo
import club.infolab.itmo_lock.data.repository.LockRepository
import club.infolab.itmo_lock.domain.DoorLock
import club.infolab.itmo_lock.domain.LockStatus
import com.tinkoffsirius.koshelok.repository.shared.AccountShared
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class LockViewModel(
    private val accountShared: AccountShared,
    private val lockRepository: LockRepository
) : ViewModel() {

    val lockedStatus = MutableLiveData(LockStatus.LOCKED)

    var room: Room? = null

    val userInfo = MutableLiveData<UserInfo>()

    private var disposable = CompositeDisposable()

    init {
        disposable += accountShared.getUserInfo()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    userInfo.postValue(it)
                },
                onError = {
                    Log.e("LOCK_VM", it.message.toString())
                }
            )
    }

    fun unlock() {
        lockedStatus.postValue(LockStatus.WAITING)
//        lockRepository.getRoomToken(room!!.id, KeyObj.token)
//            .flatMapCompletable {
//                DoorLock.unlock(
//                    client = default,
//                    tokenLock = it.roomKey,
//                    onSuccess = {
//                        lockedStatus.postValue(LockStatus.UNLOCKED)
//                    },
//                    onError = {
//                        Log.e("LOCK_VM", it.errorMsg)
//                        lockedStatus.postValue(LockStatus.ERROR)
//                    })
//            }
//            .observeOn(Schedulers.io())
//            .subscribeOn(AndroidSchedulers.mainThread())
//            .subscribeBy(
//                onComplete = {  },
//                onError = { lockedStatus.postValue(LockStatus.ERROR) }
//            )
        /* Doesn't work in another thread (with RxJava3) */
        lockRepository.getRoomToken(room!!.id, KeyObj.token)
            .doAfterSuccess { it ->
                DoorLock.unlock(
                    mac = it.mac,
                    tokenLock = it.roomKey,
                    onSuccess = {
                        lockedStatus.postValue(LockStatus.UNLOCKED)
                    },
                    onError = {
                        lockedStatus.postValue(LockStatus.ERROR)
                    }
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onError = {
                    lockedStatus.postValue(LockStatus.ERROR)
                }
            )
//        TTLockClient.getDefault().controlLock(
//            ControlAction.UNLOCK,
//            AppConfig.LOCK_DATA,
//            AppConfig.LOCK_MAC,
//            object : ControlLockCallback {
//                override fun onFail(error: LockError) {
//                    lockedStatus.postValue(LockStatus.ERROR)
//                }
//
//                override fun onControlLockSuccess(p0: ControlLockResult?) {
//                    lockedStatus.postValue(LockStatus.UNLOCKED)
//
//                }
//            })
    }

    fun setRoomData(room: Room) {
        this.room = room
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
