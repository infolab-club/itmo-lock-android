package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.data.model.Room

interface Repository {
    fun getAccessibleRooms() : ArrayList<Room>
}