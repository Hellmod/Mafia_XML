package pl.rafalmiskiewicz.mafia.ui.night

import android.util.Log
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.rafalmiskiewicz.mafia.ui.base.BaseViewModel
import pl.rafalmiskiewicz.mafia.ui.base.ClickType
import pl.rafalmiskiewicz.mafia.util.db.User
import pl.rafalmiskiewicz.mafia.util.db.UserDao
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterInt
import javax.inject.Inject

@HiltViewModel
class NightViewModel @Inject constructor(
    val initDatabase: UserDao,
    val characterMap: HashMap<Int, CharacterInt>
) : BaseViewModel<NightEvent>() {

    var charactersListInPlay = listOf<Int>()
    val characterPointerTurn = 0
    val isNight = true
    val playerList = MutableLiveData<List<User>>()

    init {
        initCharactersListInPlay()
    }

    fun initCharactersListInPlay() {
        playerList.value?.let {
            it.map {
                val characterId = it.character
                val prority = characterMap.get(it.character)?.prority
                Pair(characterId, prority)
            }.sortedBy { it.second }
                .map { it.first }
                .distinct()
        }

    }

    fun onProfileClicked(type: ClickType, id: Int) {
        sendEvent(NightEvent.KillPlayer(id))
    }

    fun onNextClicked() {
        Log.i("RMRM", "RMRM " + "onNextClicked() called")
        sendEvent(NightEvent.OnNextClick)
    }

    fun onTestsClicked() {
        sendEvent(NightEvent.OnTestsClick)
        Log.i("RMRM", "RMRM " + "onTetsClicked() called playerList = ${playerList.value}")
    }
}