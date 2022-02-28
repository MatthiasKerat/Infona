package kerasinoapps.kapps.infona.data.remote.dto.response

import com.google.gson.annotations.SerializedName

data class ImageUploadResponse(
    @SerializedName("profile_picture_url")
    val profilePictureUrl:String
)