package com.mitteloupe.whoami.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mitteloupe.whoami.ui.main.di.AppNavHostDependencies
import com.mitteloupe.whoami.ui.theme.WhoAmITheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appNavHostDependencies: AppNavHostDependencies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhoAmITheme {
                appNavHostDependencies.AppNavHost(supportFragmentManager)
            }
        }
    }
}
