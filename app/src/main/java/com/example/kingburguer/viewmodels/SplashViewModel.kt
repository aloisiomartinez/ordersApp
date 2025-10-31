package com.example.kingburguer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kingburguer.api.KingBurguerService
import com.example.kingburguer.data.KingBurguerLocalStorage
import com.example.kingburguer.data.KingBurguerRepository
import kotlinx.coroutines.flow.flow

class SplashViewModel(
    private val repository: KingBurguerRepository
): ViewModel() {
    val hasSessionState = flow {
        val userCredentials = repository.fetchInitialCredentials()
        if (userCredentials.accessToken.isNotBlank() && System.currentTimeMillis() < userCredentials.expiresTimestamp) {
            emit(true)
        } else {
            emit(false)
        }
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]!!.applicationContext
                val service = KingBurguerService.create()
                val localStorage = KingBurguerLocalStorage(application)
                val repository = KingBurguerRepository(service, localStorage)
                SplashViewModel(repository)
            }
        }
    }
}