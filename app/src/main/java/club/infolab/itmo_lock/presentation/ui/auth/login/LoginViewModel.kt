package club.infolab.itmo_lock.presentation.ui.auth.login

import club.infolab.itmo_lock.data.model.KeyObj
import club.infolab.itmo_lock.domain.login_reg.LoginData
import club.infolab.itmo_lock.presentation.ui.auth.LoginStatus
import club.infolab.itmo_lock.presentation.ui.auth.RemoteModel

class LoginViewModel : RemoteModel() {
    init {
        loginStatus.observeForever { status ->
            if (status != null && status is LoginStatus.Success) {
                KeyObj.token = status.data.toString()
            }
        }
    }

    fun loginWithData(email: String, password: List<Char>) {
        loginRequest(LoginData(email, password.joinToString("", "", "")))
    }
}