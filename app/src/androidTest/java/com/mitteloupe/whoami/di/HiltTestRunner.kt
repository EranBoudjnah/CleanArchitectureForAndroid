package com.mitteloupe.whoami.di

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, name: String?, context: Context?): Application =
        super.newApplication(cl, HiltTestApplication_Application::class.java.name, context)
}
