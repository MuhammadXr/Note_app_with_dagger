package uz.gita.noteapp_by_xr.utils

import androidx.lifecycle.MutableLiveData

fun eventLiveData() = MutableLiveData<Unit>()

fun <T> eventValueLiveData() = MutableLiveData<T>()