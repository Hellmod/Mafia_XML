package pl.rafalmiskiewicz.mafia.util.db.character

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.util.db.UserDao
import javax.inject.Inject

class Sailor @Inject constructor(
    private val initDatabase: UserDao
) : CharacterInt() {

    override fun makeSpecificAction(playerId: Int) {
        killPlayer(playerId)
    }

    private fun killPlayer(userId: Int) {
        GlobalScope.launch {
            initDatabase.updateIsPlayerDead(userId, true)
        }
    }

}
