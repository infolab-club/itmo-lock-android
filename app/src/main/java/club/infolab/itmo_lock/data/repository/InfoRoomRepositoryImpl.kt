package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.network.ItmoLockService
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.get

class InfoRoomRepositoryImpl : InfoRoomRepository {

    private val itmoLockService: ItmoLockService = get(ItmoLockService::class.java)

    override fun getRoomUnlockToken(idRoom: Long, userToken: String): Single<String> {
        return itmoLockService.getRoomUnlockToken(idRoom, userToken)
    }
}