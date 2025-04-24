package `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.repository

import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.entity.LoginEntity
import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.dao.LoginDao
import javax.inject.Inject

class LoginRepository @Inject constructor(private val dao: LoginDao) {
    suspend fun insertLoginData(loginEntity: LoginEntity) {
        dao.saveLoginCredential(loginEntity)
    }

    suspend fun getLoginData(): LoginEntity? {
        return dao.getLoginEntity()
    }

    suspend fun getCurrentUser(): LoginEntity? {
        return dao.getCurrentUser()
    }

    suspend fun isValidLogin(email: String, password: String): Boolean {
        return dao.isValidLogin(email, password)
    }

    suspend fun logoutAllUsers() {
        dao.updateAllLoginStatus(false)
    }

    suspend fun setLoggedIn(email: String, isLoggedIn: Boolean) {
        dao.updateLoginStatus(email, isLoggedIn)
    }
}