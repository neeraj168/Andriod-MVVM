package com.example.mvvm.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.repository.SubscriberRepository
import com.example.mvvm.viewmodel.SubscriberVM

/**
 * SubscriberVMFactory.kt - .
 * @author:  Jignesh N Patel
 * @date: 16-May-2021 7:14 PM
 */
class SubscriberVMFactory(private val subRepo: SubscriberRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubscriberVM::class.java)) {
            return SubscriberVM(subRepo) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}