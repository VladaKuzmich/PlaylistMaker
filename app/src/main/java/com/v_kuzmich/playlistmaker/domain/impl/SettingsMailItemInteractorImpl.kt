package com.v_kuzmich.playlistmaker.domain.impl

import android.content.Intent
import android.net.Uri
import com.v_kuzmich.playlistmaker.domain.api.SettingsItemInteractor
import com.v_kuzmich.playlistmaker.domain.models.SupportEmailMessage

class SettingsMailItemInteractorImpl(
    private var emailMessage: SupportEmailMessage = SupportEmailMessage()
) : SettingsItemInteractor {

    override fun getSettingsItemIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SENDTO)
        shareIntent.data = Uri.parse("mailto:")
        shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailMessage.email))
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, emailMessage.subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, emailMessage.message)
        return shareIntent
    }

    fun setMailSettings(emailMessage: SupportEmailMessage) {
        this.emailMessage = emailMessage
    }
}