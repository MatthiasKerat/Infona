package kerasinoapps.kapps.infona.main_module.data.repository

import android.content.Context
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.common.checkForInternetConnection
import kerasinoapps.kapps.infona.common.networkBoundResource
import kerasinoapps.kapps.infona.main_module.data.local.ExerciseDao
import kerasinoapps.kapps.infona.main_module.data.remote.ExerciseService
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kerasinoapps.kapps.infona.main_module.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ExerciseRepositoryImpl@Inject constructor(
    private val exerciseService: ExerciseService,
    private val exerciseDao: ExerciseDao,
    private val context: Context
) : ExerciseRepository {

    private var curExerciseResponse: Response<List<Exercise>>?=null

    private suspend fun insertExercises(subject:List<Exercise>){
        subject.forEach {
            exerciseDao.insertExercise(it)
        }
    }

    override fun getExercises(topicId:String): Flow<Resource<List<Exercise>>> {

        return networkBoundResource(
            query = {
                exerciseDao.getExercises(topicId = topicId)
            },
            fetch = {
                fetchExercises(topicId = topicId)
                curExerciseResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let{
                    insertExercises(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }

    suspend fun fetchExercises(topicId: String){
        curExerciseResponse = exerciseService.getExercises(topic_id = topicId)
        curExerciseResponse?.body()?.let { exerciseList ->
            exerciseList.forEach { exercise ->
                exerciseDao.insertExercise(exercise)
            }
        }
    }

    suspend fun fetchExercises(){
        curExerciseResponse = exerciseService.getAllExercises()
        curExerciseResponse?.body()?.let { exerciseList ->
            exerciseList.forEach { exercise ->
                exerciseDao.insertExercise(exercise)
            }
        }
    }

    override fun getAllExercises(): Flow<Resource<List<Exercise>>> {
        return networkBoundResource(
            query = {
                exerciseDao.getAllExercises()
            },
            fetch = {
                fetchExercises()
                curExerciseResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let{
                    insertExercises(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }

}