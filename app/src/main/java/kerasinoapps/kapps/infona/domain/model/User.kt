package kerasinoapps.kapps.infona.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kerasinoapps.kapps.infona.domain.model.user_info.Birthday
import kerasinoapps.kapps.infona.domain.model.user_info.BoughtTip
import java.util.*

@Entity (tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("_id")
    val id:String = UUID.randomUUID().toString(),
    val email:String,
    val name:String = "",
    @SerializedName("profile_picture_url")
    val profilPictureUrl:String = "",
    val gender:String = "Female",
    val birthday:Birthday = Birthday(12,6,1992),
    val averageGrade:Float = 3.0f,
    val university:String = "",
    val privacy:List<Boolean> = listOf(true,true,true), //[0] = Email-Anzeigen, [1] = Notenschnitt anzeigen,[2] Alter anzeigen
    val coins:Int = 1500,
    val doneExercises:List<String> = listOf(),
    val boughtTips:List<BoughtTip> = listOf(),

    @Expose(deserialize = false, serialize = false)
    var isAppUser:Boolean = false,
    @Expose(deserialize = false, serialize = false)
    var userImage: ImageBitmap? = null,
    @Expose(deserialize = false, serialize = false)
    var isSynced:Boolean = false

)