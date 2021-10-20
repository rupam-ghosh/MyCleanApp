package com.colearn.mycleanapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.colearn.mycleanapp.R
import com.colearn.mycleanapp.ui.landingpage.LandingPageFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LandingPageFragment.Companion.newInstance())
                    .addToBackStack(LandingPageFragment::class.java.name)
                    .commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}