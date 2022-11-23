package pl.rafalmiskiewicz.mafia.extensions

import pl.rafalmiskiewicz.mafia.util.db.character.CharacterPlayer
import pl.rafalmiskiewicz.mafia.util.db.character.Pirates
import pl.rafalmiskiewicz.mafia.util.db.character.Sailor

fun getList(): List<CharacterPlayer> {
    return listOf(
        CharacterPlayer(
            id = 0,
            name = "Marynarz",
            count = 0,
            amount = 4,
            character = Sailor().javaClass
        ),
        CharacterPlayer(
            id = 1,
            name = "Pirat",
            count = 0,
            amount = 4,
            character = Pirates().javaClass
        ),
    )
}