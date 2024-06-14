package com.an.paginglib3_sample.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Module needed to provide [ApplicationContext] to [NetworkStatusModule]
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class ApplicationModule {
    // Expose Application as an injectable context
    @Binds
    internal abstract fun bindContext(@ApplicationContext context: Context): Context
}