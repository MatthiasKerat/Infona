package kerasinoapps.kapps.infona.main_module.domain.use_case

import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.model.Topic
import kerasinoapps.kapps.infona.main_module.domain.repository.TopicRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopicsUseCase @Inject constructor(
    private val topicRepository: TopicRepository
) {

    operator fun invoke(subjectId:String): Flow<Resource<List<Topic>>> {
        return topicRepository.getTopics(subjectId = subjectId).map { result ->
            when(result){
                is Resource.Success -> {
                    if(result.data != null && result.data.isNotEmpty()){
                        Resource.Success(result.data)
                    }else{
                        Resource.Error<List<Topic>>("You should at least connect to the internet once to get the topics",listOf())
                    }
                }
                is Resource.Loading -> {
                    Resource.Loading(result.data)
                }
                is Resource.Error -> {
                    if(result.data != null && result.data.isNotEmpty()){
                        Resource.Success(result.data)
                    }else{
                        Resource.Error<List<Topic>>("You should at least connect to the internet once to get the topics",listOf())
                    }
                }
            }
        }
    }

}