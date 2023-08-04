package com.mitteloupe.whoami.test.rule

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

private const val TAG = "Test"

private val deviceLanguage by lazy { Locale.getDefault().language }

private val dateFormat by lazy { SimpleDateFormat("EEE-MMMM-dd-HH:mm:ss", Locale.US) }
private fun getDate() = dateFormat.format(Date())

private const val SCREENSHOT_FOLDER_LOCATION = ""

private val contentValues = ContentValues().apply {
    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
    }
}

class ScreenshotFailureRule : TestWatcher() {
    override fun failed(e: Throwable?, description: Description) {
        val screenShotName = "$deviceLanguage-${description.methodName}-${getDate()}"
        val bitmap = getInstrumentation().uiAutomation.takeScreenshot()
        storeFailureScreenshot(bitmap, screenShotName)
    }
}

private fun storeFailureScreenshot(bitmap: Bitmap, screenshotName: String) {
    val contentResolver = getInstrumentation().targetContext.applicationContext.contentResolver

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        useMediaStoreScreenshotStorage(
            contentResolver,
            screenshotName,
            bitmap
        )
    } else {
        usePublicExternalScreenshotStorage(
            contentResolver,
            screenshotName,
            bitmap
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
private fun useMediaStoreScreenshotStorage(
    contentResolver: ContentResolver,
    screenshotName: String,
    bitmap: Bitmap
) {
    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, screenshotName.jpg)
    contentValues.put(
        MediaStore.Images.Media.RELATIVE_PATH,
        Environment.DIRECTORY_PICTURES + SCREENSHOT_FOLDER_LOCATION
    )

    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        ?.let { uri ->
            Log.d(TAG, "Saving screenshot to $uri")
            contentResolver.openOutputStream(uri)?.let { saveScreenshotToStream(bitmap, it) }
            contentResolver.update(uri, contentValues, null, null)
        }
}

private fun usePublicExternalScreenshotStorage(
    contentResolver: ContentResolver,
    screenshotName: String,
    bitmap: Bitmap
) {
    val directory = File(
        Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES + SCREENSHOT_FOLDER_LOCATION
        ).toString()
    )

    if (!directory.exists()) {
        directory.mkdirs()
    }

    val file = File(directory, screenshotName.jpg)
    Log.d(TAG, "Saving screenshot to ${file.absolutePath}")
    saveScreenshotToStream(bitmap, FileOutputStream(file))

    contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}

private fun saveScreenshotToStream(bitmap: Bitmap, outputStream: OutputStream) {
    outputStream.use { openStream ->
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, openStream)
            Log.d(TAG, "Screenshot saved.")
        } catch (ioException: IOException) {
            Log.e(TAG, "Screenshot was not stored at this time: ${ioException.message}")
        }
    }
}

private val String.jpg
    get() = "$this.jpg"
