package kerasinoapps.kapps.infona.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kerasinoapps.kapps.infona.domain.model.User
import kerasinoapps.kapps.infona.main_module.data.local.ExerciseDao
import kerasinoapps.kapps.infona.main_module.data.local.SubjectDao
import kerasinoapps.kapps.infona.main_module.data.local.TopicDao
import kerasinoapps.kapps.infona.main_module.domain.model.exercise.Exercise
import kerasinoapps.kapps.infona.main_module.domain.model.Subject
import kerasinoapps.kapps.infona.main_module.domain.model.Topic

@Database(
    entities = [User::class, Subject::class, Topic::class, Exercise::class],
    version = 6
)
@TypeConverters(TypeConverterUser::class)
abstract class InfonaDatabase : RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun subjectDao():SubjectDao
    abstract fun topicDao():TopicDao
    abstract fun exerciseDao(): ExerciseDao
}