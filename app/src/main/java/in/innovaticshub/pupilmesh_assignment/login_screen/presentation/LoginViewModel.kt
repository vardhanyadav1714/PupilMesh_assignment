package `in`.innovaticshub.pupilmesh_assignment.login_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.entity.LoginEntity
import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Loading)
    val loginState: StateFlow<LoginState> = _loginState

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {
            try {
                val user = repository.getCurrentUser()
                _loginState.value = if (user != null) {
                    LoginState.Authenticated(user)
                } else {
                    LoginState.Unauthenticated
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                val exists = repository.isValidLogin(email, password)
                val user = if (exists) {
                    LoginEntity(email = email, password = password, isLoggedIn = true)
                } else {
                    LoginEntity(email = email, password = password, isLoggedIn = true)
                }

                 repository.logoutAllUsers()
                 repository.insertLoginData(user)
                repository.setLoggedIn(email, true)
                _loginState.value = LoginState.Authenticated(user)
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logoutAllUsers()
            _loginState.value = LoginState.Unauthenticated
        }
    }
}

sealed class LoginState {
    object Loading : LoginState()
    object Unauthenticated : LoginState()
    data class Authenticated(val user: LoginEntity) : LoginState()
    data class Error(val message: String) : LoginState()
}