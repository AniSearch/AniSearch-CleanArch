package dev.redfox.anisearchclean.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.redfox.anisearchclean.data.Repositories.AnimeRoomDBRepository
import dev.redfox.anisearchclean.data.room.AnimesDao
import dev.redfox.anisearchclean.data.room.AnimesRoomDatabase
import dev.redfox.anisearchclean.data.utils.Constants.Companion.BASE_URL
import dev.redfox.anisearchclean.domain.retrofit.AnimeApiInterface
import dev.redfox.anisearchclean.presentation.viewmodels.AnimesRoomDBViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    private var mClient: OkHttpClient? = null

    val client: OkHttpClient
        get() {
            if (mClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BASIC

                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)  /// show all JSON in logCat
                mClient = httpBuilder.build()

            }
            return mClient!!
        }

    @Provides
    fun getRetrofitService(retrofit: Retrofit): AnimeApiInterface {
        return retrofit.create(AnimeApiInterface::class.java)
    }
    @Provides
    fun getRetofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    fun provideAnimeRepository(dao: AnimesDao): AnimeRoomDBRepository {
        return AnimeRoomDBRepository(dao)
    }

    @Provides
    fun provideAnimesDao(db: AnimesRoomDatabase) = db.animesDao()

    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AnimesRoomDatabase::class.java, "Animes_Database").build()


}