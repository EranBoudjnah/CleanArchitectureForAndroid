package com.mitteloupe.whoami.test.idlingresource

import android.util.Log
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObjectNotFoundException
import androidx.test.uiautomator.UiSelector

private const val APP_NOT_RESPONDING_TEXT = " isn't responding"
private const val APP_NOT_RESPONDING_TAG = "AppNotResponding"
fun UiDevice.registerAppNotRespondingWatcher() {
    registerWatcher("AppNotResponding") {
        findAndCloseAppNotRespondingDialog()
    }
    findAndCloseAppNotRespondingDialog()
}

private fun UiDevice.appNotRespondingDialog() = findObject(
    UiSelector()
        .packageName("android")
        .textContains(APP_NOT_RESPONDING_TEXT)
)

fun UiDevice.findAndCloseAppNotRespondingDialog() =
    appNotRespondingDialog().let { appNotRespondingDialog ->
        appNotRespondingDialog.exists()
            .also { dialogExists ->
                if (dialogExists) {
                    closeAnrWithWait(appNotRespondingDialog)
                }
            }
    }

private fun UiDevice.closeAnrWithWait(appNotRespondingDialog: UiObject) {
    Log.i(APP_NOT_RESPONDING_TAG, "App not responding (ANR) dialog detected.")
    try {
        findObject(
            UiSelector()
                .text("Wait")
                .className("android.widget.Button")
                .packageName("android")
        ).click()
        val dialogText = appNotRespondingDialog.text
        val appName = dialogText.take(dialogText.length - APP_NOT_RESPONDING_TEXT.length)
        Log.i(APP_NOT_RESPONDING_TAG, "App \"$appName\" is not responding. Pressed on wait.")
    } catch (_: UiObjectNotFoundException) {
        Log.i(APP_NOT_RESPONDING_TAG, "Detected app not responding dialog, but window disappeared.")
    }
}
