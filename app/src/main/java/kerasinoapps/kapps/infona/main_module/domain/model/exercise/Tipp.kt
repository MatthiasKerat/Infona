package kerasinoapps.kapps.infona.main_module.domain.model.exercise

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tipp(
    @SerializedName("_id")
    val id: String,
    @SerializedName("coin_cost")
    val coinCost: Int,
    val description: String,
    @Expose(deserialize = false, serialize = false)
    var alreadyBought:Boolean = false
)