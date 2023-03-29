package pl.rafalmiskiewicz.mafia.util.db

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class InitDatabase @Inject constructor(
    @ApplicationContext private val context: Context
) {

    lateinit var repository: UserRepository

    fun initDao(context: Context) {
        Log.i("RMRM", "RMRM "+"initDao() called with: context = $context")
        val userDao = UserDatabase.getDatabase(context).userDao()
        repository = UserRepository(userDao)
    }

    init {
        Log.i("RMRM", "RMRM "+"InitDatabase() called with: context = $context")
    }
}