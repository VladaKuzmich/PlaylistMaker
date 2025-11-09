package com.v_kuzmich.playlistmaker

import com.v_kuzmich.playlistmaker.data.TracksRepositoryImpl
import com.v_kuzmich.playlistmaker.data.network.RetrofitNetworkClient
import com.v_kuzmich.playlistmaker.domain.api.TracksRepository
import com.v_kuzmich.playlistmaker.domain.impl.SettingsAgreementItemInteractorImpl
import com.v_kuzmich.playlistmaker.domain.impl.SettingsMailItemInteractorImpl
import com.v_kuzmich.playlistmaker.domain.impl.SettingsShareItemInteractorImpl
import com.v_kuzmich.playlistmaker.domain.impl.TracksInteractorImpl
import com.v_kuzmich.playlistmaker.domain.models.SupportEmailMessage

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TracksInteractorImpl {
        return TracksInteractorImpl(getTracksRepository())
    }

    fun provideSettingsShareItemInteractor(): SettingsShareItemInteractorImpl {
        return SettingsShareItemInteractorImpl()
    }

    fun provideSettingsMailItemInteractor(): SettingsMailItemInteractorImpl {
        return SettingsMailItemInteractorImpl()
    }

    fun provideSettingsAgreementItemInteractor(): SettingsAgreementItemInteractorImpl {
        return SettingsAgreementItemInteractorImpl()
    }
}