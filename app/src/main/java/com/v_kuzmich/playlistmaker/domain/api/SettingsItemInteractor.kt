package com.v_kuzmich.playlistmaker.domain.api

import android.content.Intent

interface SettingsItemInteractor {
    fun getSettingsItemIntent(): Intent
}