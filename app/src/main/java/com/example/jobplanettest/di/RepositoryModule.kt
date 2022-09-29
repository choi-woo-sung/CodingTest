package com.example.jobplanettest.di

import com.example.jobplanettest.repository.EnterpriseRepository
import com.example.jobplanettest.repository.RecruitRepository
import com.example.jobplanettest.repository.imp.EnterpriseRepositoryImp
import com.example.jobplanettest.repository.imp.RecruitRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindsRecruitRepository(repository: RecruitRepositoryImp): RecruitRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsEnterpriseRepository(repository: EnterpriseRepositoryImp): EnterpriseRepository
}
