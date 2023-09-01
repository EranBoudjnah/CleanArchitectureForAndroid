package com.mitteloupe.whoami.home.ui.view.widget

import android.content.Context
import android.widget.Toast

fun notificationToast(context: Context, text: CharSequence) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}
