package com.v_kuzmich.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

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

        shareButton.setOnClickListener {
            val sendIntent = Intent(Intent.ACTION_SEND)
            sendIntent.type = "text/plain"
            sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.course_link))

            val shareIntent = Intent.createChooser(sendIntent, getString(R.string.select_share_app_message))
            startActivity(shareIntent)
        }

        supportButton.setOnClickListener {
            val message = getString(R.string.email_message)
            val subject = getString(R.string.email_subject)
            val email = getString(R.string.email_address)

            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)

            startActivity(shareIntent)
        }

        userAgreementButton.setOnClickListener {
            val url = Uri.parse(getString(R.string.offer_link))
            val intent = Intent(Intent.ACTION_VIEW, url)

            startActivity(intent)
        }

    }
}