package com.mitteloupe.whoami.ui.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

abstract class BaseWhoAmIApplication : Application()

@HiltAndroidApp
class WhoAmIApplication : BaseWhoAmIApplication()
