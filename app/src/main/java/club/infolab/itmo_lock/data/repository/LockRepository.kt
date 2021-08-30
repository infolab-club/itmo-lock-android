package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.RoomAccessKey
import io.reactivex.rxjava3.core.Single

interface LockRepository {

    fun getRoomToken(idRoom: Long, userToken: String): Single<RoomAccessKey>
}
