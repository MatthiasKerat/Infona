package kerasinoapps.kapps.infona.main_module.domain.repository

import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {

    fun getExercises(topicId:String): Flow<Resource<List<Exercise>>>

    fun getAllExercises():Flow<Resource<List<Exercise>>>
}