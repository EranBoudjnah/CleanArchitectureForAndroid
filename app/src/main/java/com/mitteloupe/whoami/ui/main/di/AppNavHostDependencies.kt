package com.mitteloupe.whoami.ui.main.di

import com.mitteloupe.whoami.home.ui.di.HomeDependencies
import com.mitteloupe.whoami.opensourcenotices.ui.di.OpenSourceNoticesDependencies

data class AppNavHostDependencies(
    val homeDependencies: HomeDependencies,
    val lazyOpenSourceNoticesDependencies: Lazy<OpenSourceNoticesDependencies>
)
