package club.infolab.itmo_lock.presentation.ui.auth.registration

import android.util.Log
import club.infolab.itmo_lock.data.model.KeyObj
import club.infolab.itmo_lock.domain.login_reg.RegistrationData
import club.infolab.itmo_lock.presentation.ui.auth.LoginStatus
import club.infolab.itmo_lock.presentation.ui.auth.RemoteModel

class RegistrationViewModel : RemoteModel() {
    init {
        loginStatus.observeForever { status ->
            if (status != null && status is LoginStatus.Success) {
                KeyObj.token = status.data.toString()
                Log.d("REG", KeyObj.token.toString())
            }
        }
    }

    fun registerWithData(email: String, name: String, surname: String, password: List<Char>) {
        registerRequest(
            RegistrationData(email, name, surname, password.joinToString("", "", ""))
        )
    }
}