package pl.rafalmiskiewicz.mafia.util.db

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun deleteUser(user: Int){
        userDao.deleteUser(user)
    }

    suspend fun updateUsers(usersList: List<User>){
        userDao.updateSong(usersList)
    }
}