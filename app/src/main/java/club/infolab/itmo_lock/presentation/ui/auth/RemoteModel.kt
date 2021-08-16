package club.infolab.itmo_lock.presentation.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.infolab.itmo_lock.domain.login_reg.LoginData
import club.infolab.itmo_lock.domain.login_reg.RegistrationData
import club.infolab.itmo_lock.domain.network.API
import club.infolab.itmo_lock.domain.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class RemoteModel : ViewModel() {
    var api: API = NetworkService.retrofitService()
    val loginStatus: MutableLiveData<LoginStatus?> = MutableLiveData()

    protected fun loginRequest(
        data: LoginData
    ) {
        val request = suspend { api.login(data) }
        loginStatus.postValue(LoginStatus.Loading)
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                loginStatus.postValue(LoginStatus.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                loginStatus.postValue(LoginStatus.Error(null))
            }
        }
    }

    protected fun registerRequest(
        data: RegistrationData
    ) {
        val request = suspend { api.register(data) }
        loginStatus.postValue(LoginStatus.Loading)
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                loginStatus.postValue(LoginStatus.Success(response))
            } catch (e: Exception) {
                e.printStackTrace()
                loginStatus.postValue(LoginStatus.Error(null))
            }
        }
    }
}