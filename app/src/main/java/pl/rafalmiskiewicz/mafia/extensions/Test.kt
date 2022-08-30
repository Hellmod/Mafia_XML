package pl.rafalmiskiewicz.mafia.extensions

import pl.rafalmiskiewicz.mafia.util.db.CharacterPlayer

fun getList(): List<CharacterPlayer> {
    return listOf(
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