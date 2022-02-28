package kerasinoapps.kapps.infona.main_module.domain.use_case

import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kerasinoapps.kapps.infona.main_module.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {

    operator fun invoke(topicId:String): Flow<Resource<List<Exercise>>> {
        return exerciseRepository.getExercises(topicId = topicId).map { result ->
            when(result){
                is Resource.Success -> {
                    if(result.data != null && result.data.isNotEmpty()){
                        Resource.Success(result.data)
                    }else{
                        Resource.Error<List<Exercise>>("You should at least connect to the internet once to get the Exercises",listOf())
                    }
                }
                is Resource.Loading -> {
                    Resource.Loading(result.data)
                }
                is Resource.Error -> {
                    if(result.data != null && result.data.isNotEmpty()){
                        Resource.Success(result.data)
                    }else{
                        Resource.Error<List<Exercise>>("You should at least connect to the internet once to get the Exercises",listOf())
                    }
                }
            }
        }
    }

}