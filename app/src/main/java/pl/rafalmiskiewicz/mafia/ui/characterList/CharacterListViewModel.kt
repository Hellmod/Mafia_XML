package pl.rafalmiskiewicz.mafia.ui.characterList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.ui.base.ClickType
import pl.rafalmiskiewicz.mafia.ui.base.ProductCommonClick
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
    val _playersAmount = MutableLiveData<String>("0")
    val playersAmount: LiveData<String> = _playersAmount

    lateinit var readAllData: LiveData<List<User>>
    private lateinit var repository: UserRepository

    init {
        characterPlayerList.value = listOf(
            CharacterPlayer(
                id = 0,
                name = "Marynarz",
                count = 0,
                amount = 4
            ),
            CharacterPlayer(
                id = 1,
                name = "Pirat",
                count = 0,
                amount = 4
            ),
        )
    }

    fun initDao(context: Context) {
        val userDao = UserDatabase.getDatabase(context).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAllData
    }

    fun itemClick(type: ClickType, position: Int) {
        when (type) {
            ProductCommonClick.PlusAmount -> increaseRemove(position, 1)
            ProductCommonClick.MinusAmount -> increaseRemove(position, -1)
        }
    }

    private fun increaseRemove(position: Int, diff: Int) {
        val list = ArrayList(characterPlayerList.value)
        val amountToDrop = list[position].count + diff

        if (diff > 0 && amountToDrop > list[position].amount) {
            return
        }

        list[position] = list[position].copy(
            count = amountToDrop
        )
        updateList(list)

       Timber.i("RMRM $position , $diff")
    }

    private fun updateList(list: List<CharacterPlayer>?) {
        characterPlayerList.value = ArrayList(list)
    }
}