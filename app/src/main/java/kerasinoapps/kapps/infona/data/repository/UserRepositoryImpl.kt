package kerasinoapps.kapps.infona.data.repository

import kerasinoapps.kapps.infona.data.local.UserDao
import kerasinoapps.kapps.infona.data.remote.UserService
import kerasinoapps.kapps.infona.data.remote.dto.response.DefaultResponse
import kerasinoapps.kapps.infona.data.remote.dto.response.ImageUploadResponse
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.model.user_info.Birthday
import kerasinoapps.kapps.infona.domain.repository.UserRepository
import okhttp3.MultipartBody
import java.lang.Exception
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao
) : UserRepository{

    override suspend fun insertUser(user: User) {

        val response = try{
            userService.addUser(user)
        }catch(e : Exception){
            null
        }
        if(response != null && response.successful){
            userDao.insertUser(user.apply { isSynced = true })
        }else{
            userDao.insertUser(user)
        }

        userDao.insertUser(user)
    }

    override suspend fun getApplicationUser(): User {
        return userDao.getAppUser()
    }

    override suspend fun getUsers(): List<User> {
        val list = userService.getUsers()
        val appUser = userDao.getAppUser()

        for(user in list){
            //Nur andere Benutzer einfügen nicht auch nochmal den aktuellen AppUser
            if(user.id != appUser.id){
                userDao.insertUser(user)
            }
        }

        return list
    }

    override suspend fun getUserById(id: String): User {
        return userDao.getUserById(id)
    }

    override suspend fun getUserImage(image_path: String): String {
        return userService.getUserImage(image_path)
    }

    override suspend fun uploadImage(userId: String, filePart: MultipartBody.Part): ImageUploadResponse {
        return userService.uploadImage(userId,filePart)
    }
}