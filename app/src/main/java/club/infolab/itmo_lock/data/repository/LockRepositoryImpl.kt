package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.RoomAccessKey
import club.infolab.itmo_lock.data.network.ItmoLockService
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.get

class LockRepositoryImpl : LockRepository {

    private val itmoLockService = get(ItmoLockService::class.java)

    override fun getRoomToken(idRoom: Long, userToken: String): Single<RoomAccessKey> {
        return itmoLockService.getRoomAccess(idRoom, userToken)
    }
}
