package kerasinoapps.kapps.infona.data.remote

import kerasinoapps.kapps.infona.data.remote.dto.response.DefaultResponse
import kerasinoapps.kapps.infona.data.remote.dto.response.ImageUploadResponse
import kerasinoapps.kapps.infona.domain.model.User
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserService {

    @POST("user/update")
    suspend fun addUser(
        @Body user: User
    ):DefaultResponse

    @GET("user")
    suspend fun getUsers(

    ):List<User>

    @GET("image/{image_path}")
    suspend fun getUserImage(
        @Path("image_path")image_path:String
    ):String

    @Multipart
    @POST("image/{user_id}")
    suspend fun uploadImage(
        @Path("user_id")user_id:String,
        @Part filePart:MultipartBody.Part
    ):ImageUploadResponse

}