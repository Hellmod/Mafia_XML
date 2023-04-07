package pl.rafalmiskiewicz.mafia.util.db.character

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.util.db.UserDao

class Sailor(
    private val initDatabase: UserDao
) : CharacterInt() {
    override val name = "Marynarz"
    override fun makeSpecificAction(playerId: Int) {
        killPlayer(playerId)
    }

    private fun killPlayer(userId: Int) {
        GlobalScope.launch {
            initDatabase.updateIsPlayerDead(userId, true)
        }
    }

}