package kerasinoapps.kapps.infona.main_module.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.model.Topic
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    @Query("SELECT * FROM topics WHERE belongsToSubject = :subjectId")
    fun getTopics(subjectId:String): Flow<List<Topic>>

    @Query("DELETE FROM topics WHERE id = :topicId")
    suspend fun deleteTopic(topicId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopic(topic:Topic)

    @Query("SELECT * FROM topics")
    fun getAllTopics():Flow<List<Topic>>

}