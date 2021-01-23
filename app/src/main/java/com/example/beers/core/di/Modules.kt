package com.example.beers.core.di

import android.content.Context
import android.content.SharedPreferences
import com.example.beers.BuildConfig
import com.example.beers.core.platform.ContextHandler
import com.example.beers.core.platform.NetworkHandler
import com.example.beers.features.beers.repository.BeersRepository
import com.example.beers.features.beers.service.BeersService
import com.example.beers.features.beers.usecase.GetBeers
import com.example.beers.features.beers.usecase.GetBeersFiltered
import com.example.beers.features.beers.usecase.GetBeersViewModel
import com.example.beers.features.beers.view.BeerAdapter
import com.example.beers.features.beers.view.BeersFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { ContextHandler(get()) }
    factory { NetworkHandler(get()) }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.punkapi.com/v2/")
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        Retrofit.Builder()
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
    }
}
val applicationModule = module(override = true) {
    scope(named<BeersFragment>()){
        factory { BeerAdapter() }
    }
    single<SharedPreferences> { androidContext().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE) }
}

val useCaseModule = module {
    factory { GetBeers(get()) }
    factory { GetBeersFiltered(get()) }
}

val repositoryModule = module {
    factory<BeersRepository> { BeersRepository.Network(get(),get()) }
}

val dataSourceModule = module {
    factory { BeersService(get()) }
}


val viewModelModule = module {
    scope(named<BeersFragment>()){
        viewModel {
            GetBeersViewModel(get(), get())
        }
    }
}

private fun createClient(): OkHttpClient {
    val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
    }
    return okHttpClientBuilder.build()
}
