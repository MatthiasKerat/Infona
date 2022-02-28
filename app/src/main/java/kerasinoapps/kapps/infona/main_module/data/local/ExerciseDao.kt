package kerasinoapps.kapps.infona.main_module.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises WHERE belongsToTopic = :topicId")
    fun getExercises(topicId:String): Flow<List<Exercise>>

    @Query("DELETE FROM exercises WHERE id = :exerciseId")
    suspend fun deleteExercise(exerciseId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    @Query("SELECT * FROM exercises")
    fun getAllExercises():Flow<List<Exercise>>

}