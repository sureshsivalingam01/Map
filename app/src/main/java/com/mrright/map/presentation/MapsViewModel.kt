package com.mrright.map.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrright.map.data.Repository
import com.mrright.map.models.OfficeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _officeDetails: MutableLiveData<List<OfficeDetail>> = MutableLiveData(null)
    val officeDetails: LiveData<List<OfficeDetail>> get() = _officeDetails


    init {
        getOfficeDetails()
    }

    private fun getOfficeDetails() {
        viewModelScope.launch {
            delay(300L)
            _officeDetails.value = repository.getOfficeList()
        }
    }


}