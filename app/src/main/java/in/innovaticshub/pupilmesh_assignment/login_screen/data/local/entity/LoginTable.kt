package `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_entity")
data class LoginEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("email")
    val email: String,

    @ColumnInfo("password")
    val password: String,

    @ColumnInfo("is_logged_in", defaultValue = "0")
    val isLoggedIn: Boolean = false
)