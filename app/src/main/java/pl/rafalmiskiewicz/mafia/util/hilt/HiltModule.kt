package pl.rafalmiskiewicz.mafia.util.hilt

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.rafalmiskiewicz.mafia.util.db.UserDao
import pl.rafalmiskiewicz.mafia.util.db.UserDatabase

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    @ViewModelScoped
    fun provideDatabase(
        app: Application
    ): UserDatabase = Room.databaseBuilder(app, UserDatabase::class.java, "Mafia.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @ViewModelScoped
    fun provideUserDao(db: UserDatabase): UserDao = db.userDao()
}
