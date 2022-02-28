package kerasinoapps.kapps.infona.data.local

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kerasinoapps.kapps.infona.domain.model.user_info.Birthday
import kerasinoapps.kapps.infona.domain.model.user_info.BoughtTip
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.ExerciseInfo
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.MusterLoesung
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Tipp

class TypeConverterUser {

    /**
     * General
     */

    @TypeConverter
    fun fromImageBitmap(imageBitmap: ImageBitmap?):String{
        return ""
    }

    @TypeConverter
    fun toImageBitmap(string:String):ImageBitmap?{
        return null
    }

    @TypeConverter
    fun fromBooleanArray(array:List<Boolean>):String{
        return Gson().toJson(array)
    }

    @TypeConverter
    fun toBooleanArray(string:String):List<Boolean>{
        return Gson().fromJson(string,object: TypeToken<List<Boolean>>(){}.type)
    }

    @TypeConverter
    fun fromStringArray(array:List<String>):String{
        return Gson().toJson(array)
    }

    @TypeConverter
    fun toStringArray(string:String):List<String>{
        return Gson().fromJson(string,object: TypeToken<List<String>>(){}.type)
    }

    /**
     * User
     */
    @TypeConverter
    fun fromBirthday(birthday: Birthday):String{
        return "${birthday.day.toString()}.${birthday.month.toString()}.${birthday.year.toString()}"
    }

    @TypeConverter
    fun toBirthday(string:String):Birthday{
        val parts = string.split(""".""")
        return Birthday(parts[0].toInt(),parts[1].toInt(),parts[2].toInt())
    }

    @TypeConverter
    fun fromBoughtTipArray(array:List<BoughtTip>):String{
        return Gson().toJson(array)
    }

    @TypeConverter
    fun toBoughtTipArray(string:String):List<BoughtTip>{
        return Gson().fromJson(string,object: TypeToken<List<BoughtTip>>(){}.type)
    }

    /**
     * EXercise
     */

    @TypeConverter
    fun fromMusterLoesung(musterLoesung: MusterLoesung):String{
        return "${fromStringArray(musterLoesung.loesungArray)}#${fromBooleanArray(musterLoesung.loesungMultipleChoice)}#${musterLoesung.loesungSingleChoice}" +
                "#${musterLoesung.loesungString}#${musterLoesung.loesungsTyp}"
    }
    @TypeConverter
    fun toMusterLoesung(string:String): MusterLoesung {
        val parts = string.split("#")
        return MusterLoesung(toStringArray(parts[0]),toBooleanArray(parts[1]),parts[2].toInt(),parts[3],parts[4])
    }

    @TypeConverter
    fun fromExerciseInfo(exerciseInfo: ExerciseInfo):String{
        return "${exerciseInfo.exerciseTyp}#${fromStringArray(exerciseInfo.selectionPossibilities)}"
    }

    @TypeConverter
    fun toExerciseInfo(string:String): ExerciseInfo {
        val parts = string.split("#")
        return ExerciseInfo(parts[0],toStringArray(parts[1]))
    }

    @TypeConverter
    fun fromTippArray(tippArray:List<Tipp>):String{
        return Gson().toJson(tippArray)
    }

    @TypeConverter
    fun toTippArray(string:String):List<Tipp>{
        return Gson().fromJson(string,object: TypeToken<List<Tipp>>(){}.type)
    }
}