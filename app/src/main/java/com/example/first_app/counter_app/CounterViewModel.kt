package com.example.first_app.counter_app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CounterViewModel() : ViewModel() {
    private val _counterRepository: CounterRepository = CounterRepository()
    private val _count = mutableIntStateOf(_counterRepository.getCounter().count)

    val count: MutableState<Int> = _count

    fun increment() {
        _counterRepository.increment()
        _count.intValue = _counterRepository.getCounter().count
    }

    fun decrement() {
        _counterRepository.decrement()
        _count.intValue = _counterRepository.getCounter().count
    }
}