package com.aplikasi.makala.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

class MSPRadioButton(context: Context, attributSet: AttributeSet): AppCompatRadioButton(context,attributSet) {

    init {
        applyFont()
    }

    private fun applyFont(){
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Berlin X-Bold.ttf")
        setTypeface(typeface)
    }
}