package uz.gita.noteapp_by_xr.presenter.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.noteapp_by_xr.presenter.SplashViewModel

class SplashViewModelImpl : SplashViewModel, ViewModel() {

    override val openMainScreenLiveData = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            delay(1000)
            openMainScreenLiveData.value = Unit
        }
    }
}