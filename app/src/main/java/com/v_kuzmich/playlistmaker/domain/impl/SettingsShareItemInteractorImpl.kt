package com.v_kuzmich.playlistmaker.domain.impl

import android.content.Intent
import com.v_kuzmich.playlistmaker.domain.api.SettingsItemInteractor
import com.v_kuzmich.playlistmaker.domain.models.ShareSettings

class SettingsShareItemInteractorImpl(
    private var shareSettings: ShareSettings = ShareSettings()
) : SettingsItemInteractor {

    override fun getSettingsItemIntent(): Intent {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareSettings.link)

        return Intent.createChooser(sendIntent, shareSettings.message)
    }

    fun setShareSettings(shareSettings: ShareSettings) {
        this.shareSettings = shareSettings
    }
}