package club.infolab.itmo_lock.presentation.ui.rooms

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.data.repository.RoomsRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class RoomsViewModel(private val repository: RoomsRepository) : ViewModel() {
    val rooms = MutableLiveData<List<Room>>()

    init {
        updateData()
    }

    private fun updateData() {
        repository.getAccessibleRooms()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = {
                    rooms.postValue(it)
                },
                onError = { Log.e(TAG, it.message.toString()) }
            )
    }
}