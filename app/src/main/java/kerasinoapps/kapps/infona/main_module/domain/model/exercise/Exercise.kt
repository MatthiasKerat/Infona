package kerasinoapps.kapps.infona.main_module.domain.model.exercise

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey
    @SerializedName("_id")
    val id:String,
    val name:String,
    val description:String,
    @SerializedName("picture_url")
    val pictureUrl:String,
    val musterLoesung: MusterLoesung,
    val exerciseInfo: ExerciseInfo,
    val tipps:List<Tipp>,
    @SerializedName("coin_reward")
    val coinReward:Int,
    @SerializedName("belongs_to_topic")
    val belongsToTopic:String
)