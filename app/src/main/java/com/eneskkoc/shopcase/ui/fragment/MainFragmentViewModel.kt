package com.eneskkoc.shopcase.ui.fragment

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskkoc.shopcase.data.AppDataManager
import com.eneskkoc.shopcase.data.model.category.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(private var appDataManager: AppDataManager) : ViewModel() {
    val data = MutableLiveData<State>()
    private var categoryLiveData = MutableLiveData<List<Data>?>()
    var _categoryLiveData: LiveData<List<Data>?> = categoryLiveData
    val progressVisible = ObservableField(true)

    fun getCategory() {//kategori servisine istek
        viewModelScope.launch(Dispatchers.IO) {
            appDataManager.api().let {
                if (it.isSuccessful) {

                    categoryLiveData.postValue(it.body()?.data)
                    data.postValue(State.OnCompleted)
                    progressVisible.set(false)//progressbarı kapatma
                } else {//hata mesajı
                    data.postValue(State.OnError("${it.errorBody()}"))
                    progressVisible.set(false)//progressbarı kapatma
                }
            }
        }

    }

    sealed class State {
        object OnCompleted : State()
        data class OnError(val error: String) : State() //parametre göndermek için data class

    }
}