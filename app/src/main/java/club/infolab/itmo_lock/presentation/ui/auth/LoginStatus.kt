package club.infolab.itmo_lock.presentation.ui.auth

sealed class LoginStatus {
    object Loading : LoginStatus()
    data class Success(val data: Any) : LoginStatus()
    data class Error(val error: Exception?) : LoginStatus()
}