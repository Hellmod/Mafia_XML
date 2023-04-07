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
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterInt
import pl.rafalmiskiewicz.mafia.util.db.character.Pirates
import pl.rafalmiskiewicz.mafia.util.db.character.Sailor
import javax.inject.Singleton

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

    @Provides
    @ViewModelScoped
    fun provideSailor(dao: UserDao): HashMap<Int, CharacterInt> {
        val mutableMap = mutableMapOf<Int, CharacterInt>()
        mutableMap.put(1, Pirates(dao))
        mutableMap.put(2, Sailor(dao))
        return HashMap<Int, CharacterInt>(mutableMap.size).apply {
            putAll(mutableMap)
        }
    }
}
