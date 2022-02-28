package kerasinoapps.kapps.infona.main_module.domain.repository

import kerasinoapps.kapps.infona.common.Resource
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface SubjectRepository {

    fun getSubjects(): Flow<Resource<List<Subject>>>

}