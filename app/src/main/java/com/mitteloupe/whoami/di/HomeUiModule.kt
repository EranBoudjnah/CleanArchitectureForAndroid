package com.mitteloupe.whoami.di

import android.content.Context
import com.mitteloupe.whoami.home.ui.mapper.ConnectionDetailsToUiMapper
import com.mitteloupe.whoami.ui.navigation.mapper.HomeDestinationToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object HomeUiModule {
    @Provides
    fun providesHomeDestinationToUiMapper(
        @ActivityContext context: Context
    ) = HomeDestinationToUiMapper(context)

    @Provides
    fun providesConnectionDetailsToUiMapper() = ConnectionDetailsToUiMapper()
}
