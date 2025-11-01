package com.alamin.smarthabitcompanion.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.smarthabitcompanion.core.network.ServerConstants
import com.alamin.smarthabitcompanion.domain.model.CurrentWeatherRequestParam
import com.alamin.smarthabitcompanion.domain.usecase.CurrentWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val currentWeatherUseCase: CurrentWeatherUseCase): ViewModel() {

    init {
       requestCurrentWeather()
    }

    fun requestCurrentWeather(){
        viewModelScope.launch (Dispatchers.IO){
            val weatherRequestParam = CurrentWeatherRequestParam("Dhaka", ServerConstants.API_KEY)
            currentWeatherUseCase.invoke(weatherRequestParam)
        }
    }


}