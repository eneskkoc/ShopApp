package com.eneskkoc.shopcase.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.eneskkoc.shopcase.R
import com.eneskkoc.shopcase.base.BaseAdapter
import com.eneskkoc.shopcase.data.model.category.Data
import com.eneskkoc.shopcase.databinding.FragmentMainBinding
import com.eneskkoc.shopcase.databinding.ItemCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel by viewModels<MainFragmentViewModel>()
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: BaseAdapter<ItemCategoryBinding, Data>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = object : BaseAdapter<ItemCategoryBinding, Data>(R.layout.item_category) {
            override fun bindView(binding: ItemCategoryBinding, item: Data?, adapterPosition: Int) {
                binding.viewModel = item

            }

            override fun clickListener(item: Data?, position: Int, binding: ItemCategoryBinding) {
                viewModel._categoryLiveData.observe(viewLifecycleOwner) {
                    openProduct(it?.get(position)?.categoryId)
                }
            }
        }
        binding.viewModel = viewModel
        viewModel.getCategory()
        binding.recyclerviewCate.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MainFragmentViewModel.State.OnCompleted -> onCompleted()
                is MainFragmentViewModel.State.OnError -> onMessage(state.error)
            }
        }
    }

    private fun openProduct(categoryId: String?) {//ürünler ekranına gitme
        categoryId?.let {
            Navigation.findNavController(requireView()).navigate(MainFragmentDirections.actionMainFragmentToProductFragment(it))
        }
    }

    private fun onCompleted() {//adaptere item atama
        viewModel._categoryLiveData.observe(viewLifecycleOwner) { data ->
            adapter.items = data?.let { ArrayList(it) }
        }
    }

    private fun onMessage(error: String) {
        Log.e("backend hatası", error)
    }

}