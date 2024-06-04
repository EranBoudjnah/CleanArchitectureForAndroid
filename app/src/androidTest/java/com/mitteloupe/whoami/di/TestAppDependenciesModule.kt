package com.mitteloupe.whoami.di

import android.app.Activity
import com.mitteloupe.whoami.ui.main.di.AppNavHostDependencies
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface TestAppDependenciesEntryPoint {
    val appNavHostDependencies: AppNavHostDependencies
}

fun testAppDependenciesEntryPoint(activity: Activity) = EntryPoints.get(
    activity,
    TestAppDependenciesEntryPoint::class.java
)
