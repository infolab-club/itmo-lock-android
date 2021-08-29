package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.Room
import club.infolab.itmo_lock.data.network.ItmoLockService
import org.koin.java.KoinJavaComponent.get

class RepositoryImpl : Repository {
    val itmoLockService: ItmoLockService = get(ItmoLockService::class.java)

    override fun getAccessibleRooms(): List<Room> {
        return listOf()
    }
}