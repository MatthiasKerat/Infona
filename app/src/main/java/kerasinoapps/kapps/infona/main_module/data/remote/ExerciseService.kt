package kerasinoapps.kapps.infona.main_module.data.remote

import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ExerciseService {

    @GET("exercise/{topic_id}")
    suspend fun getExercises(
        @Path("topic_id") topic_id:String
    ): Response<List<Exercise>>

    @GET("exercise")
    suspend fun getAllExercises(

    ):Response<List<Exercise>>
}