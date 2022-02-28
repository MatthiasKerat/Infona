package kerasinoapps.kapps.infona.main_module.domain.repository

import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.model.Topic
import kotlinx.coroutines.flow.Flow

interface TopicRepository {

    fun getTopics(subjectId:String): Flow<Resource<List<Topic>>>

    fun getAllTopics():Flow<Resource<List<Topic>>>
}