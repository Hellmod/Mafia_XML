package pl.rafalmiskiewicz.mafia.data.common

import pl.rafalmiskiewicz.mafia.util.db.User

data class PlayerItem(
    val id: Int,
    val name: String,
)

fun PlayerItem.toUser():User{
    return User(
        uid = id,
        name = name,
        character = null
    )
}