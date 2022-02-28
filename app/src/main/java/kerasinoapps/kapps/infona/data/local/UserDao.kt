package kerasinoapps.kapps.infona.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kerasinoapps.kapps.infona.domain.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE isAppUser = 1")
    suspend fun getAppUser():User

    @Query("SELECT * FROM users where id = :userId")
    suspend fun getUserById(userId:String):User

}