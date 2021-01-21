package com.aplikasi.makala.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView

class MSPButton(context: Context, attrs: AttributeSet) : AppCompatButton(context, attrs) {
    init {
        applyFont()
    }

    private fun applyFont(){
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Berlin X-Bold.ttf")
        setTypeface(typeface)
    }
}