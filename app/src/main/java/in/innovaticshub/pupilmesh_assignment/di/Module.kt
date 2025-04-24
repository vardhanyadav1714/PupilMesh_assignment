package `in`.innovaticshub.pupilmesh_assignment.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.dao.LoginDao
import `in`.innovaticshub.pupilmesh_assignment.login_screen.data.local.database.LoginDatabase
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.remote.api.MangaApi
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.dao.MangaDataDao
import `in`.innovaticshub.pupilmesh_assignment.manga_screen.data.local.database.MangaDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    const val BASE_URL="https://mangaverse-api.p.rapidapi.com/"

    @Provides
    @Singleton
    fun provideLoginRoomDatabase(
        @ApplicationContext context: Context
    ): LoginDatabase {
        return Room.databaseBuilder(
            context,
            LoginDatabase::class.java,
            "login_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLoginDao(db: LoginDatabase): LoginDao {
        return db.loginDao()
    }

    @Provides
    @Singleton
    fun provideMangaApi(): MangaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MangaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMangaRoomDatabase(
        @ApplicationContext context: Context
    ): MangaDatabase {
        return Room.databaseBuilder(
            context,
            MangaDatabase::class.java,
            "manga_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMangaDao(db: MangaDatabase): MangaDataDao {
        return db.mangaDao()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}