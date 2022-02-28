package kerasinoapps.kapps.infona.domain.use_case

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.compose.ui.platform.LocalContext
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.common.getPathFromURI
import kerasinoapps.kapps.infona.data.remote.dto.response.ImageUploadResponse
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(userId: String, uri: Uri, context: Context) = flow {

        try{
            emit(Resource.Loading<ImageUploadResponse>())
            val realPath = getPathFromURI(context,uri)
            val file = java.io.File(realPath ?: "")
            val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val multipartBody = MultipartBody.Part.createFormData("image",file.name,requestFile)
            val response = userRepository.uploadImage(userId = userId, multipartBody)
            emit(Resource.Success(response))
        }catch(e: HttpException){
            emit(Resource.Error<ImageUploadResponse>(e.localizedMessage ?: "An unexpected error occured"))
        }catch(e: IOException) {
            System.out.println("Message: ${e.printStackTrace()}")
            emit(Resource.Error<ImageUploadResponse>("Couldn't reach server. Check your internet connection."))
        }

    }

}