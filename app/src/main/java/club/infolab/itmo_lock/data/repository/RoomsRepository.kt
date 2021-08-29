package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.Room
import io.reactivex.rxjava3.core.Single

interface RoomsRepository {

    fun getAccessibleRooms(): Single<List<Room>>
}
