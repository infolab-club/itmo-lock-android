package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.KeyObj
import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.data.network.ItmoLockService
import io.reactivex.rxjava3.core.Single
import org.koin.java.KoinJavaComponent.get

class RoomsRepositoryImpl : RoomsRepository {
    val itmoLockService: ItmoLockService = get(ItmoLockService::class.java)

    override fun getAccessibleRooms(): Single<List<Room>> {
        return itmoLockService.getAccessibleRooms(KeyObj.token)
            .map { roomsData -> roomsData.listRooms }
    }
}