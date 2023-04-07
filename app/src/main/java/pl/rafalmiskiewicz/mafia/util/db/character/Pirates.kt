package pl.rafalmiskiewicz.mafia.util.db.character

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.rafalmiskiewicz.mafia.util.db.UserDao

class Pirates(
    private val initDatabase: UserDao
) : CharacterInt() {

    override val name = "Pirat"
    override fun makeSpecificAction(playerId: Int) {
        killPlayer(playerId)
    }

    private fun killPlayer(userId: Int) {
        GlobalScope.launch {
            initDatabase.updateIsPlayerDead(userId, true)
        }
    }

}