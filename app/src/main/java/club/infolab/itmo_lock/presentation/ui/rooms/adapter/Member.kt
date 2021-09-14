package club.infolab.itmo_lock.presentation.ui.rooms.adapter

data class Member(
    val idUser: Long,
    val name: String,
    val surname: String,
    val isThereAccess: Boolean
)
