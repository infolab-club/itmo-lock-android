package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.entity.Room

interface Repository {
    fun getAccessibleRooms(): List<Room>
}