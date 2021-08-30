package club.infolab.itmo_lock.data.repository

import io.reactivex.rxjava3.core.Single

interface InfoRoomRepository {

    fun getRoomUnlockToken(idRoom: Long, userToken: String): Single<String>
}