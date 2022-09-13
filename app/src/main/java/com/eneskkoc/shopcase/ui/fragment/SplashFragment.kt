
package com.eneskkoc.shopcase.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.eneskkoc.shopcase.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val viewModel by viewModels<SplashViewModel>()
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() { // geri gelince countdowntimer çalışması için
        object :
            CountDownTimer(5000, 1000) { //animasyon devam ederken süre sayması
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                Navigation.findNavController(requireView()).navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            }
        }.start()
        super.onResume()
    }

}
