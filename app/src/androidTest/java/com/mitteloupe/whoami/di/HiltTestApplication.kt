package com.mitteloupe.whoami.di

import com.mitteloupe.whoami.ui.main.BaseWhoAmIApplication
import dagger.hilt.android.testing.CustomTestApplication

@CustomTestApplication(BaseWhoAmIApplication::class)
interface HiltTestApplication
