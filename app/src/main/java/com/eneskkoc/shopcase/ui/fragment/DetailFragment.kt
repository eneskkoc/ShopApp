package com.eneskkoc.shopcase.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.eneskkoc.shopcase.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel by viewModels<DetailViewModel>()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        arguments.let { bundle ->
            val productId: String? = bundle?.let { arg -> DetailFragmentArgs.fromBundle(arg).id }
            productId?.let { id -> viewModel.getDetail(id) }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        viewModel.data.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailViewModel.State.OnCompleted -> onCompleted()
                is DetailViewModel.State.OnError -> onMessage(state.error)
            }
        }
        binding.imgAdd.setOnClickListener {//sepete tıklanınca
            Toast.makeText(context,"Sonraki Güncelleme ile Kullanıma Açılacaktır.",Toast.LENGTH_LONG).show()
        }
    }

    private fun onCompleted() {//viewlere değer ataması
        viewModel._detailLiveData.observe(viewLifecycleOwner) { data ->
            binding.tvDetailName.text = data?.title
            binding.tvDesc.text = data?.description
            if (data?.campaignPrice == null) {
                binding.tvDetailCamp.visibility=View.GONE
            } else {
                binding.tvDetailCamp.text ="Campaign Price:"+ data.campaignPrice.toString()+"$"
            }
            binding.tvDetailPrice.text ="Price:"+ data?.price.toString()+"$"
            if (data?.stock?.equals(0) == true) {
                binding.tvStock.text = "No Stock"
            } else binding.tvStock.text = "İs Stock"

        }

    }

    private fun onMessage(error: String) {//hata mesajı logcata yazdırma
        Log.e("backend hatası", error)
    }

}