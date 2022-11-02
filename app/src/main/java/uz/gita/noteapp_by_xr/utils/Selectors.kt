package uz.gita.noteapp_by_xr.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

import android.graphics.drawable.StateListDrawable
import uz.gita.noteapp_by_xr.R


@SuppressLint("UseCompatLoadingForDrawables")
fun selectedBg(context: Context): Drawable? {
    return context.getDrawable(R.drawable.tag_text_background_selected)
}
@SuppressLint("UseCompatLoadingForDrawables")
fun simpleBg(context: Context): Drawable? {
    return context.getDrawable(R.drawable.tag_text_background)
}