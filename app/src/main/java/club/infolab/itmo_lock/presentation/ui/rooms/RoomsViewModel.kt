package club.infolab.itmo_lock.presentation.ui.rooms

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.data.repository.RoomsRepository
import com.tinkoffsirius.koshelok.repository.shared.AccountShared
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomsViewModel(
    private val repository: RoomsRepository,
    private val accountShared: AccountShared
) : ViewModel() {
    val rooms = MutableLiveData<List<Room>>()

    private val disposable = CompositeDisposable()

    init {
        disposable += updateData()
    }

    private fun updateData(): Disposable {
        return repository.getAccessibleRooms()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    rooms.postValue(it)
                },
                onError = { Log.e(TAG, it.message.toString()) }
            )
    }

    fun logout(onCompleteCallback: () -> Unit) {
        disposable += accountShared.removeAllData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onComplete = { onCompleteCallback() },
                onError = { Log.e("ROOMS_VM", it.message.toString()) }
            )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}