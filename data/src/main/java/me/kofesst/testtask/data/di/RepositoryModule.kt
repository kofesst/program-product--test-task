package me.kofesst.testtask.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.kofesst.testtask.data.impl.GeneratorsRepositoryImpl
import me.kofesst.testtask.domain.GeneratorsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideGeneratorsRepository(): GeneratorsRepository {
        return GeneratorsRepositoryImpl()
    }
}