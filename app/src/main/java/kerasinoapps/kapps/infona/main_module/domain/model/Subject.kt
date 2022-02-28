package kerasinoapps.kapps.infona.main_module.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "subjects")
data class Subject(
    @PrimaryKey
    @SerializedName("_id")
    val id:String,
    val name:String,
    val description:String,
    @SerializedName("suitable_for")
    val suitableFor:String,
    @SerializedName("related_subjects")
    val relatedSubjects:List<String>,
    @SerializedName("required_basics")
    val requiredBasics:String
)
