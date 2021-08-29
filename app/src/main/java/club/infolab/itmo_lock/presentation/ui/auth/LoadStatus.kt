package club.infolab.itmo_lock.presentation.ui.auth

sealed class LoadStatus {
    object Loading : LoadStatus()
    data class Success(val data: Any = Any()) : LoadStatus()
    data class Error(val error: Throwable) : LoadStatus()
    object InputWaiting : LoadStatus()
}
