package club.infolab.itmo_lock.data.repository

import club.infolab.itmo_lock.config.AppConfig

class RepositoryImpl : Repository {
    override fun getAccessibleRooms() = AppConfig.rooms
}