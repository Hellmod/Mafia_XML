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

}