package club.infolab.itmo_lock.presentation.ui.lock

import android.content.ContentValues.TAG
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
        lockRepository.getRoomToken(room!!.id, KeyObj.token)
            .flatMapCompletable {
                DoorLock.unlock(
                    tokenLock = it.roomKey,
                    onSuccess = {
                        lockedStatus.postValue(LockStatus.UNLOCKED)
                    },
                    onError = {
                        Log.e("LOCK_VM", it.errorMsg)
                        lockedStatus.postValue(LockStatus.ERROR)
                    })
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.computation())
            .subscribeBy(
                onError = { Log.e(TAG, it.message.toString()) }
            )
    }

    fun setRoomData(room: Room) {
        this.room = room
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
