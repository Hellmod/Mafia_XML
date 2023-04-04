package pl.rafalmiskiewicz.mafia.util.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("DELETE FROM User WHERE id = :userId")
    suspend fun deleteUser(userId: Int)

    @Query("SELECT * FROM User")
    fun readAllData(): LiveData<List<User>>

    @Update
    fun updateSong(songs: List<User>): Int

    @Query("UPDATE User SET isPlayerDead = :isDead WHERE id = :userId")
    suspend fun updateIsPlayerDead(userId: Int, isDead: Boolean)

}