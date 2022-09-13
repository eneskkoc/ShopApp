package com.eneskkoc.shopcase.ui.fragment

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskkoc.shopcase.data.AppDataManager
import com.eneskkoc.shopcase.data.model.product.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private var appDataManager: AppDataManager) : ViewModel() {
    val data = MutableLiveData<State>()
    private var productLiveData = MutableLiveData<List<Data>?>()
    var _productLiveData: LiveData<List<Data>?> = productLiveData
    val progressVisible = ObservableField(true)

    fun getProduct(categoryId: String, sort: String) {//ürünler servisine istek
        viewModelScope.launch(Dispatchers.IO) {
            appDataManager.productApi(categoryId, sort).let {
                if (it.isSuccessful) {

                    productLiveData.postValue(it.body()?.data)
                    data.postValue(State.OnCompleted)
                    progressVisible.set(false)//progressbarı kapatma
                } else {
                    data.postValue(State.OnError("${it.errorBody()}"))
                    progressVisible.set(false)//progressbarı kapatma
                }
            }
        }

    }

    fun getSort(categoryId: String, sort: String) {//sıralanmış olarak ürünler servisine istek
        viewModelScope.launch(Dispatchers.IO) {
            progressVisible.set(true)
            appDataManager.productApi(categoryId, sort).let {
                if (it.isSuccessful) {

                    productLiveData.postValue(it.body()?.data)
                    data.postValue(State.OnSort)
                    progressVisible.set(false)//progressbarı kapatma
                } else {
                    data.postValue(State.OnError("${it.errorBody()}"))
                    progressVisible.set(false)//progressbarı kapatma
                }
            }
        }

    }

    sealed class State {
        object OnCompleted : State()
        object OnSort : State()
        data class OnError(val error: String) : State() //parametre göndermek için data class

    }
}