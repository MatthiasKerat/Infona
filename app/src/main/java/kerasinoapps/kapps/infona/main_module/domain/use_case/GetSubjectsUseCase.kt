package kerasinoapps.kapps.infona.main_module.domain.use_case

import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.repository.SubjectRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetSubjectsUseCase @Inject constructor(
    private val subjectRepository: SubjectRepository
) {

    operator fun invoke(): Flow<Resource<List<Subject>>> {
        return subjectRepository.getSubjects().map { result ->
            when(result){
                is Resource.Success -> {
                    if(result.data != null && result.data.isNotEmpty()){
                        Resource.Success(result.data)
                    }else{
                        Resource.Error<List<Subject>>("You should at least connect to the internet once to get the subjects",listOf())
                    }
                }
                is Resource.Loading -> {
                    Resource.Loading(result.data)
                }
                is Resource.Error -> {
                    if(result.data != null && result.data.isNotEmpty()){
                        Resource.Success(result.data)
                    }else{
                        Resource.Error<List<Subject>>("You should at least connect to the internet once to get the subjects",listOf())
                    }
                }
            }
        }
    }
}