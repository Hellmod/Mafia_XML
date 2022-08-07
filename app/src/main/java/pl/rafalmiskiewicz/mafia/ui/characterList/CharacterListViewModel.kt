package pl.rafalmiskiewicz.mafia.ui.characterList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.util.db.CharacterPlayer
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserDatabase
import pl.rafalmiskiewicz.mafia.util.db.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor() : BaseViewModel<CharacterListEvent>() {

    val characterPlayerList = MutableLiveData<List<CharacterPlayer>>()

    val playerList = MutableLiveData<List<User>>()
    val playersAmount = MutableLiveData<Int>()

    lateinit var readAllData: LiveData<List<User>>
    private lateinit var repository: UserRepository

    fun initDao(context: Context) {
        Timber.i("RMRM initializing DataBase")
        val userDao = UserDatabase.getDatabase(context).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
        playersAmount.value = playerList.value?.size ?: 0
    }
}