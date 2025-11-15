package com.v_kuzmich.playlistmaker

import android.content.Context
import com.v_kuzmich.playlistmaker.data.TracksHistoryRepositoryImpl
import com.v_kuzmich.playlistmaker.data.TracksRepositoryImpl
import com.v_kuzmich.playlistmaker.data.network.RetrofitNetworkClient
import com.v_kuzmich.playlistmaker.domain.api.TracksHistoryRepository
import com.v_kuzmich.playlistmaker.domain.api.TracksRepository
import com.v_kuzmich.playlistmaker.domain.impl.SettingsAgreementItemInteractorImpl
import com.v_kuzmich.playlistmaker.domain.impl.SettingsMailItemInteractorImpl
import com.v_kuzmich.playlistmaker.domain.impl.SettingsShareItemInteractorImpl
import com.v_kuzmich.playlistmaker.domain.impl.TracksHistoryInteractorImpl
import com.v_kuzmich.playlistmaker.domain.impl.TracksInteractorImpl

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TracksInteractorImpl {
        return TracksInteractorImpl(getTracksRepository())
    }

    private fun getTracksHistoryRepository(context: Context): TracksHistoryRepository {
        return TracksHistoryRepositoryImpl(context)
    }

    fun provideTracksHistoryInteractor(context: Context): TracksHistoryInteractorImpl {
        return TracksHistoryInteractorImpl(getTracksHistoryRepository(context))
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