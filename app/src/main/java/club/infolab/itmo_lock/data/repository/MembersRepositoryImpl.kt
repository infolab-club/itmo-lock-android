package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.IdRoomBody
import club.infolab.itmo_lock.data.entity.Users
import club.infolab.itmo_lock.data.network.ItmoLockService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.get

class MembersRepositoryImpl : MembersRepository {

    private val itmoLockService: ItmoLockService = get(ItmoLockService::class.java)

    override fun getUsers(token: String): Single<Users> {
        return itmoLockService.getUsers(token)
    }

    override fun addUser(idUser: Long, idRoom: Long, token: String): Completable {
        return itmoLockService.addUser(idUser, IdRoomBody(idRoom = idRoom), token)
    }

    override fun removeUser(idUser: Long, idRoom: Long, token: String): Completable {
        return itmoLockService.addUser(idUser, IdRoomBody(idRoom = idRoom), token)
    }
}