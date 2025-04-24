package `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.entity.LoginEntity
import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.dao.LoginDao

@Database(entities = [LoginEntity::class], version = 2)
abstract class LoginDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginDao
}