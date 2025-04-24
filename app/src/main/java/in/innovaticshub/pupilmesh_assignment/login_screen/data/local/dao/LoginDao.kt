package `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.entity.LoginEntity

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLoginCredential(loginTable: LoginEntity)

    @Query("SELECT * FROM login_entity WHERE is_logged_in = 1 LIMIT 1")
    suspend fun getCurrentUser(): LoginEntity?

    @Query("SELECT * FROM login_entity LIMIT 1")
    suspend fun getLoginEntity(): LoginEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM login_entity WHERE email = :email AND password = :password)")
    suspend fun isValidLogin(email: String, password: String): Boolean

    @Query("UPDATE login_entity SET is_logged_in = :isLoggedIn")
    suspend fun updateAllLoginStatus(isLoggedIn: Boolean)

    @Query("UPDATE login_entity SET is_logged_in = :isLoggedIn WHERE email = :email")
    suspend fun updateLoginStatus(email: String, isLoggedIn: Boolean)
}