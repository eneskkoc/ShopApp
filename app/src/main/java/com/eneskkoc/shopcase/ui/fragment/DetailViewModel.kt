package com.eneskkoc.shopcase.ui.fragment

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eneskkoc.shopcase.data.AppDataManager
import com.eneskkoc.shopcase.data.model.detail.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private var appDataManager: AppDataManager) : ViewModel() {

    val data = MutableLiveData<State>()
    private var detailLiveData = MutableLiveData<Data?>()
    var _detailLiveData: LiveData<Data?> = detailLiveData
    val progressVisible = ObservableField(true)
    val image = ObservableField<String>()

    fun getDetail(id: String) {//detail servisine istek
        viewModelScope.launch(Dispatchers.IO) {
            appDataManager.detailApi(id).let {
                if (it.isSuccessful) {

                    detailLiveData.postValue(it.body()?.data)
                    image.set(it.body()?.data?.images?.get(0)?.t)
                    data.postValue(State.OnCompleted)
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
        data class OnError(val error: String) : State() //parametre göndermek için data class

    }
}