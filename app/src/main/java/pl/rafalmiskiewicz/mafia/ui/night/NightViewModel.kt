package pl.rafalmiskiewicz.mafia.ui.night

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.util.db.UserDao
import pl.rafalmiskiewicz.mafia.util.db.UserWitchCheckBox
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterInt
import javax.inject.Inject

@HiltViewModel
class NightViewModel @Inject constructor(
    val initDatabase: UserDao,
    val characterMap: HashMap<Int, CharacterInt>
) : BaseViewModel<NightEvent>() {

    var charactersListInPlay = listOf<Int>()
    val characterPointerTurn = MutableLiveData(-1)
    val dayPart = MutableLiveData(DayPart.NIGHT)
    val playerList = MutableLiveData<List<UserWitchCheckBox>>()

    fun onNextClicked() {
        playerList.value?.let {
            val idSelectedUsers = it.filter { it.isSelected == true }.map { it.user.id }
            sendEvent(NightEvent.OnNextClick(idSelectedUsers))
        }
    }

    fun onTestsClicked() {
        Log.i("RMRM", "RMRM " + "onTestsClicked() called charactersListInPlay: ${playerList.value}")
        sendEvent(NightEvent.OnTestsClick)
        resurrectAll()
    }

    private fun resurrectAll() {
        GlobalScope.launch {
            playerList.value?.forEach {
                initDatabase.updateIsPlayerDead(it.user.id, false)
            }
        }
    }

    fun calculateCharactersListInPlay() {
        playerList.value?.let {
            charactersListInPlay = it.filter { user ->
                val wakeInNight = characterMap.get(user.user.character)?.wakeInNight ?: false
                wakeInNight
            }
                .map { user ->
                    val characterId = user.user.character
                    val priority = characterMap.get(user.user.character)?.prority
                    Pair(characterId, priority)
                }.sortedBy { it.second }
                .map { it.first }
                .distinct()
        }
        if (charactersListInPlay.isNotEmpty() && characterPointerTurn.value == -1) {
            characterPointerTurn.value = 0
        }
    }

    fun isGameEnd(): Boolean {
        val numberAliveEvil =
            playerList.value?.filter { !it.user.isPlayerDead && it.user.character.toCharacter().isEvil }?.map { it.user.id }?.size ?: 0
        val numberAliveGood =
            playerList.value?.filter { !it.user.isPlayerDead && it.user.character.toCharacter().isEvil }?.map { it.user.id }?.size ?: 0
        return numberAliveEvil >= 1 && numberAliveGood >= 1
    }

    fun nextCharacter() {
        playerList.value?.let {
            playerList.value = it.map { user ->
                user.isSelected = false
                user
            }
        }
        characterPointerTurn.value = (characterPointerTurn.value?.plus(1)) ?: (-1)
        Log.i("RMRM", "RMRM "+"nextCharacter() called isGameEnd: ${isGameEnd()}")
    }

    fun endNight() {
        dayPart.value = DayPart.DAY
    }

    fun Int.toCharacter(): CharacterInt {
        return characterMap.get(this)!!
    }
}