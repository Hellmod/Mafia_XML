package pl.rafalmiskiewicz.mafia.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val uid: Int,
    val name: String,
    val character: String?
)
