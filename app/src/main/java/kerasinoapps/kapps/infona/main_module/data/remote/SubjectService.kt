package kerasinoapps.kapps.infona.main_module.data.remote

import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import retrofit2.Response
import retrofit2.http.GET

interface SubjectService {

    @GET("subject")
    suspend fun getSubjects(): Response<List<Subject>>

}