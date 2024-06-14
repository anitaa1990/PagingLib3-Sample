package com.an.paginglib3_sample.module

import android.content.Context
import com.an.paginglib3_sample.utils.NetworkStatus
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Module class needed to provide an instance of [NetworkStatus] to ViewModel class
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkStatusModule {
    @Provides
    @Singleton
    fun provideNetworkStatus(context: Context) = NetworkStatus(context)
}