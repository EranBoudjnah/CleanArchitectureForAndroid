package com.mitteloupe.whoami.screen

import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

class OpenSourceNoticesScreen {
    fun seesScreen() {
        intended(hasComponent(OssLicensesMenuActivity::class.java.name))
    }
}
