package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.Users
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface MembersRepository {

    fun getUsers(token: String): Single<Users>

    // idUser - в теле запроса
    fun addUser(idUser: Long, idRoom: Long, token: String): Completable

    fun removeUser(idUser: Long, idRoom: Long, token: String): Completable
}
