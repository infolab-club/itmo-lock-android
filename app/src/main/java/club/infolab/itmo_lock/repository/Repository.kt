package club.infolab.itmo_lock.repository

import club.infolab.itmo_lock.model.Room

interface Repository {
    fun getAccessibleRooms() : ArrayList<Room>
}