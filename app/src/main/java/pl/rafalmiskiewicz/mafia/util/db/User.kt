package pl.rafalmiskiewicz.mafia.util.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import pl.rafalmiskiewicz.mafia.data.common.PlayerItem

@Entity()
data class User(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val name: String,
    val character: String?
)

fun User.toPlayerItem():PlayerItem{
    return PlayerItem(
        id = uid,
        name = name
    )
}
