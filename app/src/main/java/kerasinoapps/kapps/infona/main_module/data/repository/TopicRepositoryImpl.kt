package kerasinoapps.kapps.infona.main_module.data.repository

import android.content.Context
import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.common.checkForInternetConnection
import kerasinoapps.kapps.infona.common.networkBoundResource
import kerasinoapps.kapps.infona.main_module.data.local.SubjectDao
import kerasinoapps.kapps.infona.main_module.data.local.TopicDao
import kerasinoapps.kapps.infona.main_module.data.remote.SubjectService
import kerasinoapps.kapps.infona.main_module.data.remote.TopicService
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.model.Topic
import kerasinoapps.kapps.infona.main_module.domain.repository.TopicRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class TopicRepositoryImpl @Inject constructor(
    private val topicService: TopicService,
    private val topicDao: TopicDao,
    private val context: Context
) :TopicRepository {

    private var curTopicResponse: Response<List<Topic>>?=null

    private suspend fun insertTopics(subject:List<Topic>){
        subject.forEach {
            topicDao.insertTopic(it)
        }
    }

    override fun getTopics(subjectId:String): Flow<Resource<List<Topic>>> {
        return networkBoundResource(
            query = {
                topicDao.getTopics(subjectId = subjectId)
            },
            fetch = {
                fetchTopics(subjectId = subjectId)
                curTopicResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let{
                    insertTopics(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }

    suspend fun fetchTopics(subjectId: String){
        curTopicResponse = topicService.getTopics(subject_id = subjectId)
        curTopicResponse?.body()?.let { topicList ->
            topicList.forEach { topic ->
                topicDao.insertTopic(topic)
            }
        }
    }

    suspend fun fetchTopics(){
        curTopicResponse = topicService.getAllTopics()
        curTopicResponse?.body()?.let { topicList ->
            topicList.forEach { topic ->
                topicDao.insertTopic(topic)
            }
        }
    }

    override fun getAllTopics(): Flow<Resource<List<Topic>>> {
        return networkBoundResource(
            query = {
                topicDao.getAllTopics()
            },
            fetch = {
                fetchTopics()
                curTopicResponse
            },
            saveFetchResult = { response ->
                response?.body()?.let{
                    insertTopics(it)
                }
            },
            shouldFetch = {
                checkForInternetConnection(context)
            }
        )
    }

}