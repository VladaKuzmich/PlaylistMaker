package com.v_kuzmich.playlistmaker.presentation.helper

import android.content.Context
import android.util.TypedValue

class UiHelper {

    fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics).toInt()
    }
}