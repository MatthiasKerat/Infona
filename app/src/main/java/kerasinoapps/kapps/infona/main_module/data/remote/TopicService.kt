package kerasinoapps.kapps.infona.main_module.data.remote

import kerasinoapps.kapps.infona.auth_module.data.remote.api.dto.response.ForgotPasswordResponse
import kerasinoapps.kapps.infona.main_module.domain.model.Topic
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TopicService {

    @GET("topic/{subject_id}")
    suspend fun getTopics(
        @Path("subject_id") subject_id:String
    ): Response<List<Topic>>

    @GET("topic")
    suspend fun getAllTopics(

    ):Response<List<Topic>>

}