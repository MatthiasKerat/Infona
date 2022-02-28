package kerasinoapps.kapps.infona.domain.use_case

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserImageUseCase @Inject constructor(
    private val userRepository: UserRepository
){
    operator fun invoke (image_path:String) = flow {
        try{
            emit(Resource.Loading())
            val response = userRepository.getUserImage(image_path)

            val imageBytes = Base64.decode(response,0)
            val image = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.size)
            emit(Resource.Success(image.asImageBitmap()))
        }catch(e: HttpException){
            emit(Resource.Error<ImageBitmap>(e.localizedMessage ?: "An unexpected error occured"))
        }catch(e: IOException) {
            emit(Resource.Error<ImageBitmap>("Couldn't reach server. Check your internet connection."))
        }
    }


}