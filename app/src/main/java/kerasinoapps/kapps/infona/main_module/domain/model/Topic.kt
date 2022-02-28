package kerasinoapps.kapps.infona.main_module.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "topics")
data class Topic(
    @PrimaryKey
    @SerializedName("_id")
    val id:String,
    val name:String,
    val description:String,
    @SerializedName("application_in_practise")
    val applicationInPractise:String,
    @SerializedName("belongs_to_subject")
    val belongsToSubject:String
)
