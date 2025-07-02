package com.mitteloupe.whoami.suite

import androidx.compose.ui.test.ExperimentalTestApi
import com.mitteloupe.whoami.test.HistoryHighlightedIpAddressTest
import com.mitteloupe.whoami.test.HistoryTest
import com.mitteloupe.whoami.test.HomeTest
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@OptIn(ExperimentalTestApi::class)
@RunWith(Suite::class)
@SuiteClasses(
    HistoryTest::class,
    HistoryHighlightedIpAddressTest::class,
    HomeTest::class
)
class SmokeTests
