package kerasinoapps.kapps.infona.main_module.data.repository

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.common.checkForInternetConnection
import kerasinoapps.kapps.infona.common.networkBoundResource
import kerasinoapps.kapps.infona.main_module.data.local.SubjectDao
import kerasinoapps.kapps.infona.main_module.data.remote.SubjectService
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.repository.SubjectRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import retrofit2.Response
import javax.inject.Inject

class SubjectRepositoryImpl @Inject constructor(
    private val subjectService: SubjectService,
    private val subjectDao: SubjectDao,
    private val context: Context
):SubjectRepository {

    private var curSubjectResponse: Response<List<Subject>>?=null

    private suspend fun insertSubjects(subject:List<Subject>){
        subject.forEach {
            subjectDao.insertSubject(it)
        }
    }

    override fun getSubjects(): Flow<Resource<List<Subject>>> {
        return networkBoundResource(
            query = {
                subjectDao.getSubjects()
            },
            fetch = {
                fetchSubjects()
                curSubjectResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let{
                    insertSubjects(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }

    suspend fun fetchSubjects(){
        curSubjectResponse = subjectService.getSubjects()
        curSubjectResponse?.body()?.let { subjectList ->
            subjectList.forEach { subject ->
                subjectDao.insertSubject(subject)
            }
        }
    }
}