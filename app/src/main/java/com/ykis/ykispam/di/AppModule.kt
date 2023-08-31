package com.ykis.ykispam.di


import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.pam.data.AddressRepositoryImpl
import com.ykis.ykispam.pam.data.ApartmentRepositoryImpl
import com.ykis.ykispam.pam.data.FamilyRepositoryImpl
import com.ykis.ykispam.pam.data.HeatMeterRepositoryImpl
import com.ykis.ykispam.pam.data.HeatReadingRepositoryImpl
import com.ykis.ykispam.pam.data.PaymentRepositoryImpl
import com.ykis.ykispam.pam.data.ServiceRepositoryImpl
import com.ykis.ykispam.pam.data.WaterMeterRepositoryImpl
import com.ykis.ykispam.pam.data.WaterReadingRepositoryImpl
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCache
import com.ykis.ykispam.pam.data.cache.dao.ApartmentDao
import com.ykis.ykispam.pam.data.cache.dao.FamilyDao
import com.ykis.ykispam.pam.data.cache.dao.HeatMeterDao
import com.ykis.ykispam.pam.data.cache.dao.HeatReadingDao
import com.ykis.ykispam.pam.data.cache.dao.PaymentDao
import com.ykis.ykispam.pam.data.cache.dao.ServiceDao
import com.ykis.ykispam.pam.data.cache.dao.WaterMeterDao
import com.ykis.ykispam.pam.data.cache.dao.WaterReadingDao
import com.ykis.ykispam.pam.data.cache.database.AppDatabase
import com.ykis.ykispam.pam.data.cache.family.FamilyCache
import com.ykis.ykispam.pam.data.cache.heat.meter.HeatMeterCache
import com.ykis.ykispam.pam.data.cache.heat.reading.HeatReadingCache
import com.ykis.ykispam.pam.data.cache.payment.PaymentCache
import com.ykis.ykispam.pam.data.cache.service.ServiceCache
import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.data.cache.water.meter.WaterMeterCache
import com.ykis.ykispam.pam.data.cache.water.reading.WaterReadingCache
import com.ykis.ykispam.pam.data.remote.address.AddressRemote
import com.ykis.ykispam.pam.data.remote.api.ApiService
import com.ykis.ykispam.pam.data.remote.api.ApiService.Companion.BASE_URL
import com.ykis.ykispam.pam.data.remote.appartment.ApartmentRemote
import com.ykis.ykispam.pam.data.remote.family.FamilyRemote
import com.ykis.ykispam.pam.data.remote.heat.meter.HeatMeterRemote
import com.ykis.ykispam.pam.data.remote.heat.reading.HeatReadingRemote
import com.ykis.ykispam.pam.data.remote.payment.PaymentRemote
import com.ykis.ykispam.pam.data.remote.service.ServiceRemote
import com.ykis.ykispam.pam.data.remote.water.meter.WaterMeterRemote
import com.ykis.ykispam.pam.data.remote.water.reading.WaterReadingRemote
import com.ykis.ykispam.pam.domain.address.AddressRepository
import com.ykis.ykispam.pam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.pam.domain.family.FamilyRepository
import com.ykis.ykispam.pam.domain.heat.meter.HeatMeterRepository
import com.ykis.ykispam.pam.domain.heat.reading.HeatReadingRepository
import com.ykis.ykispam.pam.domain.payment.PaymentRepository
import com.ykis.ykispam.pam.domain.service.ServiceRepository
import com.ykis.ykispam.pam.domain.water.meter.WaterMeterRepository
import com.ykis.ykispam.pam.domain.water.reading.WaterReadingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi,okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }



    @Provides
    fun provideApartmentDao(db: AppDatabase): ApartmentDao {
        return db.apartmentDao()
    }

    @Provides
    fun provideFamilyDao(db: AppDatabase): FamilyDao {
        return db.familyDao()
    }

    @Provides
    fun provideServiceDao(db: AppDatabase): ServiceDao {
        return db.serviceDao()
    }
    @Provides
    fun providePaymentDao(db: AppDatabase): PaymentDao {
        return db.paymentDao()
    }
    @Provides
    fun provideWaterMeterDao(db: AppDatabase): WaterMeterDao {
        return  db.waterMeterDao()
    }
    @Provides
    fun provideWaterReadingDao(db: AppDatabase): WaterReadingDao {
        return  db.waterReadingDao()
    }
    @Provides
    fun provideHeatMeterDao(db: AppDatabase): HeatMeterDao {
        return  db.heatMeterDao()
    }

    @Provides
    fun provideHeatReadingDao(db: AppDatabase): HeatReadingDao {
        return  db.heatReadingDao()
    }
    @Singleton
    @Provides
    fun provideApartmentRepository(
        apartmentRemote: ApartmentRemote,
        userCache: UserCache,
        firebaseService: FirebaseService,
        familyCache: FamilyCache,
        serviceCache: ServiceCache,
        paymentCache: PaymentCache,
        waterMeterCache: WaterMeterCache,
        heatMeterCache : HeatMeterCache,
        waterReadingCache: WaterReadingCache,
        heatReadingCache: HeatReadingCache,
        apartmentCache: ApartmentCache
    ): ApartmentRepository {
        return ApartmentRepositoryImpl(apartmentRemote,apartmentCache, familyCache,serviceCache, paymentCache,waterMeterCache , heatMeterCache,waterReadingCache, heatReadingCache,userCache)
    }

    @Singleton
    @Provides
    fun provideAddressRepository(
        addressRemote: AddressRemote,
        userCache: UserCache,
    ): AddressRepository {
        return AddressRepositoryImpl(addressRemote,userCache)
    }
    @Singleton
    @Provides
    fun provideFamilyRepository(
        familyCache: FamilyCache,
        familyRemote: FamilyRemote,
        userCache: UserCache
    ): FamilyRepository {
        return FamilyRepositoryImpl(familyCache , familyRemote, userCache )
    }

    @Singleton
    @Provides
    fun provideServiceRepository(
        serviceCache: ServiceCache,
        serviceRemote: ServiceRemote,
        userCache: UserCache
    ): ServiceRepository {
        return ServiceRepositoryImpl(serviceCache , serviceRemote, userCache )
    }

    @Singleton
    @Provides
    fun providePaymentRepository(
        paymentCache: PaymentCache,
        paymentRemote: PaymentRemote,
        userCache: UserCache
    ): PaymentRepository {
        return PaymentRepositoryImpl(paymentCache , paymentRemote, userCache )
    }
    @Singleton
    @Provides
    fun provideWaterMeterRepository(
        waterMeterCache: WaterMeterCache,
        waterMeterRemote: WaterMeterRemote,
        userCache: UserCache
    ): WaterMeterRepository {
        return  WaterMeterRepositoryImpl(waterMeterCache ,waterMeterRemote , userCache)
    }
    @Singleton
    @Provides
    fun provideWaterReadingRepository(
        waterReadingCache : WaterReadingCache,
        waterReadingRemote: WaterReadingRemote,
        userCache: UserCache
    ): WaterReadingRepository {
        return  WaterReadingRepositoryImpl(waterReadingCache ,waterReadingRemote , userCache)
    }
    @Singleton
    @Provides
    fun provideHeatMeterRepository(
        heatMeterCache: HeatMeterCache,
        heatMeterRemote: HeatMeterRemote,
        userCache: UserCache
    ): HeatMeterRepository {
        return  HeatMeterRepositoryImpl(heatMeterCache ,heatMeterRemote , userCache)
    }
    @Singleton
    @Provides
    fun provideHeatReadingRepository(
        heatReadingCache : HeatReadingCache,
        heatReadingRemote: HeatReadingRemote,
        userCache: UserCache
    ): HeatReadingRepository {
        return  HeatReadingRepositoryImpl(heatReadingCache ,heatReadingRemote , userCache)
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }
}
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope