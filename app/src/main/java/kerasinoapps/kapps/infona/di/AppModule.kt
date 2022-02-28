package kerasinoapps.kapps.infona.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kerasinoapps.kapps.infona.common.Constants
import kerasinoapps.kapps.infona.common.Constants.DATABASE_NAME
import kerasinoapps.kapps.infona.common.Constants.ENCRYPTED_SHARED_PREF_NAME
import kerasinoapps.kapps.infona.data.local.UserDao
import kerasinoapps.kapps.infona.data.local.InfonaDatabase
import kerasinoapps.kapps.infona.data.local.preferences.UserPreferences
import kerasinoapps.kapps.infona.data.local.preferences.UserPreferencesImpl
import kerasinoapps.kapps.infona.data.remote.UserService
import kerasinoapps.kapps.infona.data.remote.interceptor.TokenInterceptor
import kerasinoapps.kapps.infona.data.remote.interceptor.TokenInterceptorImpl
import kerasinoapps.kapps.infona.data.repository.UserRepositoryImpl
import kerasinoapps.kapps.infona.domain.repository.UserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        return EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_SHARED_PREF_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Singleton
    @Provides
    fun provideUserPreferences(sharedPreferences: SharedPreferences): UserPreferences {
        return UserPreferencesImpl(sharedPreferences)
    }

    @Singleton
    @Provides
    fun provideTokenInterceptor(): TokenInterceptor {
        return TokenInterceptorImpl()
    }

    @Provides
    @Singleton
    fun provideUserService(jwtInterceptor: TokenInterceptor) : UserService {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val logClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(jwtInterceptor)
            .connectTimeout(2000,TimeUnit.MILLISECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(logClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(service: UserService, dao: UserDao): UserRepository {
        return UserRepositoryImpl(service,dao)
    }

    @Provides
    @Singleton
    fun provideInfonaDatabase(@ApplicationContext context:  Context) =
        Room.databaseBuilder(context, InfonaDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideUserDao(db: InfonaDatabase) = db.userDao()



}