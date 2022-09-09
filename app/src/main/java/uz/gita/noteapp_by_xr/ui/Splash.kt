package uz.gita.noteapp_by_xr.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import uz.gita.noteapp_by_xr.R
import uz.gita.noteapp_by_xr.presenter.SplashViewModel
import uz.gita.noteapp_by_xr.presenter.impl.SplashViewModelImpl

class Splash : Fragment(R.layout.fragment_splash) {

    val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openMainScreenLiveData.observe(this){
            findNavController().navigate(R.id.action_splash_to_mainScreen)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}
