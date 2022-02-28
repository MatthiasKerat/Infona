package kerasinoapps.kapps.infona.domain.repository

import kerasinoapps.kapps.infona.data.remote.dto.response.ImageUploadResponse
import kerasinoapps.kapps.infona.domain.model.User
import okhttp3.MultipartBody

interface UserRepository {

    suspend fun insertUser(user: User)

    suspend fun getApplicationUser():User

    suspend fun getUsers():List<User>

    suspend fun getUserById(id:String):User

    suspend fun getUserImage(image_path:String):String

    suspend fun uploadImage(userId:String, filePart:MultipartBody.Part):ImageUploadResponse

}