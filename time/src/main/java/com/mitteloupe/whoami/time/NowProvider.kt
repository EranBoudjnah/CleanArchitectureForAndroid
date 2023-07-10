package com.mitteloupe.whoami.time

interface NowProvider {
    fun nowMilliseconds(): Long

    object DefaultNowProvider : NowProvider {
        override fun nowMilliseconds(): Long =
            System.currentTimeMillis()
    }
}
