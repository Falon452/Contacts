package com.activecampaign.contacts.data.di

import com.activecampaign.contacts.data.ContactsRepositoryImpl
import com.activecampaign.contacts.data.datasource.ContactsApi
import com.activecampaign.contacts.domain.repository.ContactsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class ContactDataModule {

    @Binds
    abstract fun bindContactsRepository(
        contactsRepositoryImpl: ContactsRepositoryImpl,
    ): ContactsRepository

    companion object {

        @Provides
        fun provideGitHubApiService(retrofit: Retrofit): ContactsApi =
            retrofit.create(ContactsApi::class.java)
    }
}
