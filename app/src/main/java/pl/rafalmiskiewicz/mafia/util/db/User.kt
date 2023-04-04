package pl.rafalmiskiewicz.mafia.util.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var character: Int = -1,
    var isPlayerDead: Boolean = false,
)