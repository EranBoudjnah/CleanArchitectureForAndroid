package com.mitteloupe.whoami.architecture.ui.navigation.model

fun interface UiDestination {
    fun navigate(backStack: MutableList<Any>)
}
