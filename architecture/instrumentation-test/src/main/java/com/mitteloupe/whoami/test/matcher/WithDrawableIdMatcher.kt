package com.mitteloupe.whoami.test.matcher

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.createBitmap
import androidx.test.platform.app.InstrumentationRegistry
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun withDrawableId(@DrawableRes id: Int): Matcher<View> = WithDrawableIdMatcher(id)

class WithDrawableIdMatcher(@param:DrawableRes private val expectedId: Int) :
    TypeSafeMatcher<View>(View::class.java) {

    override fun matchesSafely(target: View): Boolean {
        @Suppress("UNCHECKED_CAST")
        val drawable: Drawable = when {
            target::class.simpleName == "androidx.appcompat.view.menu.ActionMenuItemView" -> {
                (target::class as KClass<in View>).memberProperties
                    .first { it.name == "bar" }
                    .getter(target) as Drawable
            }

            target is ImageView -> {
                target.drawable?.extractStateIfStateful(target.drawableState)
            }

            else -> null
        } ?: return false

        val resources = target.resources
        val expectedDrawable =
            ResourcesCompat.getDrawable(resources, expectedId, target.context.theme)
        val constantStateIsSame =
            expectedDrawable?.constantState?.let { it == drawable.constantState } == true
        if (constantStateIsSame) return true

        val bitmapHolder = getBitmap(drawable)
        val expectedBitmapHolder = expectedDrawable?.let(::getBitmap)
        val result = expectedBitmapHolder?.bitmap?.sameAs(bitmapHolder?.bitmap) == true
        bitmapHolder?.recycleIfRecyclable()
        expectedBitmapHolder?.recycleIfRecyclable()
        return result
    }

    private fun getBitmap(drawable: Drawable): BitmapHolder? =
        (drawable as? BitmapDrawable)?.let { bitmapDrawable ->
            BitmapHolder(bitmap = bitmapDrawable.bitmap, recyclable = false)
        } ?: run {
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            if (width < 1 || height < 1) {
                return null
            }
            val result = createBitmap(width, height)
            val canvas = Canvas(result)

            with(drawable) {
                setBounds(0, 0, canvas.width, canvas.height)
                colorFilter = PorterDuffColorFilter(0, PorterDuff.Mode.DST)
                draw(canvas)
            }
            BitmapHolder(bitmap = result, recyclable = true)
        }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: $expectedId")
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        targetContext.resources.getResourceEntryName(expectedId)
            ?.let { description.appendText("[$it]") }
    }

    private fun Drawable.extractStateIfStateful(currentState: IntArray): Drawable? {
        val stateListDrawable = this as? StateListDrawable ?: return this

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            stateListDrawable.getStateDrawable(
                stateListDrawable.findStateDrawableIndex(currentState)
            )
        } else {
            Log.w("DrawableMatcher", "Android version ${Build.VERSION.SDK_INT} unsupported.")
            null
        }
    }

    private class BitmapHolder(val bitmap: Bitmap, private val recyclable: Boolean) {
        fun recycleIfRecyclable() {
            if (recyclable) {
                bitmap.recycle()
            }
        }
    }
}
