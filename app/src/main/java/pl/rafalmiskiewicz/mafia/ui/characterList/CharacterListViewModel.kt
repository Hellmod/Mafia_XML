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
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor() : BaseViewModel<CharacterListEvent>() {

    val characterPlayerList = MutableLiveData<List<CharacterPlayer>>()

    val playerList = MutableLiveData<List<User>>()
    val playersAmount = MutableLiveData(0)
    private val _characterLeft = MutableLiveData<String>("0")
    val characterLeft: LiveData<String> = _characterLeft

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
        calculateCharacterToChoose()
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
        calculateCharacterToChoose()
    }

    private fun updateList(list: List<CharacterPlayer>?) {
        characterPlayerList.value = ArrayList(list)
    }

    fun setCharacterLeft(amaount: Int) {
        _characterLeft.value = amaount.toString()
    }

    private fun calculateCharacterToChoose() {
        val list = ArrayList(characterPlayerList.value)
        _characterLeft.value = (playersAmount.value?.minus(list.sumOf { it.count })).toString()
    }

    fun onNextClicked() {
        sendEvent(CharacterListEvent.OnNextClick)
    }
}