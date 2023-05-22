package pl.rafalmiskiewicz.mafia.util.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import pl.rafalmiskiewicz.mafia.util.db.UserDao
import pl.rafalmiskiewicz.mafia.util.db.UserWitchCheckBox
import pl.rafalmiskiewicz.mafia.util.db.character.CharacterInt
import javax.inject.Inject

class GameState @Inject constructor(
    val initDatabase: UserDao,
    val characterMap: HashMap<Int, CharacterInt>
) {

    val playerList = MutableLiveData<List<UserWitchCheckBox>>()

    fun isGameEnd(): Boolean {
        val numberAliveEvil =
            playerList.value?.filter { !it.user.isPlayerDead && it.user.character.toCharacter().isEvil }?.map { it.user.id }?.size ?: 0
        val numberAliveGood =
            playerList.value?.filter { !it.user.isPlayerDead && it.user.character.toCharacter().isEvil }?.map { it.user.id }?.size ?: 0

        Log.i("RMRM", "RMRM "+"isGameEnd() called playerList.value: ${playerList.value}, numberAliveEvil: $numberAliveEvil, numberAliveGood: $numberAliveGood")
        return numberAliveEvil >= 1 && numberAliveGood >= 1
    }

    fun Int.toCharacter(): CharacterInt {
        return characterMap.get(this)!!
    }
}