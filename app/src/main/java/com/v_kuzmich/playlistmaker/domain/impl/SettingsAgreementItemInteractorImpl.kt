package com.v_kuzmich.playlistmaker.domain.impl

import android.content.Intent
import android.net.Uri
import com.v_kuzmich.playlistmaker.domain.api.SettingsItemInteractor

class SettingsAgreementItemInteractorImpl(
    private var link: String = ""
) : SettingsItemInteractor{

    override fun getSettingsItemIntent(): Intent {
        val url = Uri.parse(link)
        return Intent(Intent.ACTION_VIEW, url)
    }

    fun setAgreementLink(link: String){
        this.link = link
    }
}