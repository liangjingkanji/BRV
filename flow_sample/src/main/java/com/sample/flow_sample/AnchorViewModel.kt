package com.sample.flow_sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnchorViewModel : ViewModel(){

    private val _hotAnchorListStateFlow = MutableStateFlow<List<FollowAnchor>>(emptyList())
    val hotAnchorListStateFlow: StateFlow<List<FollowAnchor>> = _hotAnchorListStateFlow

    fun getHotAnchorList(){
        viewModelScope.launch(Dispatchers.Default) {
            val list = mutableListOf<FollowAnchor>()
            for (i in 0..6) {
                list.add(FollowAnchor())
            }
            _hotAnchorListStateFlow.emit(list)
        }
    }
}