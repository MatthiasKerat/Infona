package kerasinoapps.kapps.infona.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kerasinoapps.kapps.infona.auth_module.data.remote.api.AuthService
import kerasinoapps.kapps.infona.auth_module.data.repository.AuthRepositoryImpl
import kerasinoapps.kapps.infona.auth_module.domain.repository.AuthRepository
import kerasinoapps.kapps.infona.common.Constants
import kerasinoapps.kapps.infona.data.local.InfonaDatabase
import kerasinoapps.kapps.infona.data.remote.interceptor.TokenInterceptor
import kerasinoapps.kapps.infona.main_module.data.local.ExerciseDao
import kerasinoapps.kapps.infona.main_module.data.local.SubjectDao
import kerasinoapps.kapps.infona.main_module.data.local.TopicDao
import kerasinoapps.kapps.infona.main_module.data.remote.ExerciseService
import kerasinoapps.kapps.infona.main_module.data.remote.SubjectService
import kerasinoapps.kapps.infona.main_module.data.remote.TopicService
import kerasinoapps.kapps.infona.main_module.data.repository.ExerciseRepositoryImpl
import kerasinoapps.kapps.infona.main_module.data.repository.SubjectRepositoryImpl
import kerasinoapps.kapps.infona.main_module.data.repository.TopicRepositoryImpl
import kerasinoapps.kapps.infona.main_module.domain.repository.ExerciseRepository
import kerasinoapps.kapps.infona.main_module.domain.repository.SubjectRepository
import kerasinoapps.kapps.infona.main_module.domain.repository.TopicRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object MainModule {

    @Provides
    @ViewModelScoped
    fun provideAuthService(jwtInterceptor: TokenInterceptor) : AuthService{

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val logClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(jwtInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(logClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(service: AuthService): AuthRepository {
        return AuthRepositoryImpl(service)
    }

    @Provides
    @ViewModelScoped
    fun provideSubjectService(jwtInterceptor: TokenInterceptor) : SubjectService{

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val logClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(jwtInterceptor)
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(logClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SubjectService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideSubjectRepository(service:SubjectService,dao:SubjectDao,@ApplicationContext context: Context):SubjectRepository{
        return SubjectRepositoryImpl(service,dao,context)
    }

    @Provides
    @ViewModelScoped
    fun provideSubjectDao(db: InfonaDatabase) = db.subjectDao()

    @Provides
    @ViewModelScoped
    fun provideTopicService(jwtInterceptor: TokenInterceptor) : TopicService {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val logClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(jwtInterceptor)
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(logClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TopicService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideTopicRepository(service:TopicService, dao: TopicDao, @ApplicationContext context: Context): TopicRepository {
        return TopicRepositoryImpl(service,dao,context)
    }

    @Provides
    @ViewModelScoped
    fun provideTopicDao(db: InfonaDatabase) = db.topicDao()

    @Provides
    @ViewModelScoped
    fun provideExerciseService(jwtInterceptor: TokenInterceptor) : ExerciseService {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val logClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(jwtInterceptor)
            .connectTimeout(2000, TimeUnit.MILLISECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(logClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExerciseService::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideExerciseRepository(service:ExerciseService, dao: ExerciseDao, @ApplicationContext context: Context): ExerciseRepository {
        return ExerciseRepositoryImpl(service,dao,context)
    }

    @Provides
    @ViewModelScoped
    fun provideExerciseDao(db: InfonaDatabase) = db.exerciseDao()
}