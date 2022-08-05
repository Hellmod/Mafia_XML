package pl.rafalmiskiewicz.mafia.ui.playerList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.data.common.PlayerItem
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserDatabase
import pl.rafalmiskiewicz.mafia.util.db.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor() : BaseViewModel<PlayerListEvent>() {

    val playerList = MutableLiveData<List<PlayerItem>>()
    val playerName = MutableLiveData<String>()

    lateinit var readAllData: LiveData<List<User>>
    private lateinit var repository: UserRepository

    fun initDao(context: Context) {
        Timber.i("RMRM initializing DataBase")
        val userDao = UserDatabase.getDatabase(context).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun onProfileClicked(id: Int) {
        playerList.value = playerList.value?.filter { it.id != id }
    }

    fun onAddClicked() {
        addUser(
            User(
                uid = 0,
                name = playerName.value ?: "ERROR",
                character = null
            )
        )
    }

    fun onNextClicked() {
        sendEvent(PlayerListEvent.NavigateToCharacter)
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }
}