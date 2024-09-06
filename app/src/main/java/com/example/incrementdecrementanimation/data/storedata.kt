package com.example.incrementdecrementanimation.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class storedata:ViewModel() {
    val Counter = MutableStateFlow(0)

    val counter: StateFlow<Int> = Counter
    fun increment(){
        Counter.value++
    }
    fun decrement(){
        Counter.value--
    }
    fun reset(){
        Counter.value = 0
    }
}