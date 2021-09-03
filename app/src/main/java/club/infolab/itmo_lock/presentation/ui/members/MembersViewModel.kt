package club.infolab.itmo_lock.presentation.ui.members

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import club.infolab.itmo_lock.data.entity.KeyObj
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.data.entity.UserLocks
import club.infolab.itmo_lock.data.repository.MembersRepository
import club.infolab.itmo_lock.presentation.ui.rooms.adapter.Member
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

typealias Action = ((idUser: Long, idRoom: Long, token: String) -> Completable)

class MembersViewModel(
    private val membersRepository: MembersRepository
) : ViewModel() {

    var room: Room? = null

    val members = MutableLiveData<List<Member>>(listOf())

    private val disposable = CompositeDisposable()

    init {
        disposable += updateData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { members.postValue(it.toMembers(room!!)) },
                onError = { Log.e(TAG, it.message.toString()) }
            )
    }

    private fun updateData(): Single<List<UserLocks>> {
        return membersRepository.getUsers(KeyObj.token)
            .map { it.users }
    }

    fun changeUserAccess(member: Member) {
        val action = getAccessAction(member)
        disposable += action(member.idUser, room!!.id, KeyObj.token)
            .andThen(updateData())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeBy(
                onSuccess = { members.postValue(it.toMembers(room!!)) },
                onError = { Log.e(TAG, it.message.toString()) }
            )
    }

    private fun getAccessAction(member: Member): Action {
        return if (member.isThereAccess) {
            membersRepository::removeUser
        } else {
            membersRepository::addUser
        }
    }
}

private fun List<UserLocks>.toMembers(room: Room): List<Member> {
    return this.map { userLocks ->
        Member(
            idUser = userLocks.id,
            name = userLocks.name,
            surname = userLocks.surname,
            isThereAccess = userLocks.locks.filter { id -> id == room.id }.isNotEmpty()
        )
    }
}
