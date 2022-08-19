package pl.rafalmiskiewicz.mafia.util.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class CharacterPlayer(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    var count: Int,
    val amount: Int
)