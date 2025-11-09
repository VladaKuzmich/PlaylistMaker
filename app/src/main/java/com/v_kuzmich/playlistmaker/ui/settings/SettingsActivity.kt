package com.v_kuzmich.playlistmaker.ui.settings

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import com.v_kuzmich.playlistmaker.App
import com.v_kuzmich.playlistmaker.Creator.provideSettingsAgreementItemInteractor
import com.v_kuzmich.playlistmaker.Creator.provideSettingsMailItemInteractor
import com.v_kuzmich.playlistmaker.Creator.provideSettingsShareItemInteractor
import com.v_kuzmich.playlistmaker.R
import com.v_kuzmich.playlistmaker.domain.models.ShareSettings
import com.v_kuzmich.playlistmaker.domain.models.SupportEmailMessage

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<ImageButton>(R.id.back_button)
        val shareButton = findViewById<TextView>(R.id.share)
        val supportButton = findViewById<TextView>(R.id.support)
        val userAgreementButton = findViewById<TextView>(R.id.user_agreement)
        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        switchTheme.setOnCheckedChangeListener{ _, checked ->
            (applicationContext as App).switchTheme(checked)
        }

        switchTheme.isChecked = (applicationContext as App).isDarkTheme

        backButton.setOnClickListener {
            finish()
        }

        val settingsShareItemInteractor = provideSettingsShareItemInteractor()
        val settingsMailItemInteractor = provideSettingsMailItemInteractor()
        val settingsAgreementItemInteractor = provideSettingsAgreementItemInteractor()

        shareButton.setOnClickListener {
            settingsShareItemInteractor.setShareSettings(
                ShareSettings(
                    getString(R.string.select_share_app_message),
                    getString(R.string.course_link)
                )
            )
            startActivity(settingsShareItemInteractor.getSettingsItemIntent())
        }

        supportButton.setOnClickListener {
            settingsMailItemInteractor.setMailSettings(SupportEmailMessage(
                getString(R.string.email_message),
                getString(R.string.email_subject),
                getString(R.string.email_address)
            ))
            startActivity(settingsMailItemInteractor.getSettingsItemIntent())
        }

        userAgreementButton.setOnClickListener {
            settingsAgreementItemInteractor.setAgreementLink(getString(R.string.offer_link))
            startActivity(settingsAgreementItemInteractor.getSettingsItemIntent())
        }

    }
}