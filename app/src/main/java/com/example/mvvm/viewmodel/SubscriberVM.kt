package com.example.mvvm.viewmodel

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm.utilities.Event
import com.example.mvvm.db.entity.Subscriber
import com.example.mvvm.repository.SubscriberRepository
import kotlinx.coroutines.launch

/**
 * SubscriberVM - View model class of Subscriber to access subscriber repository.
 * @author:  Jignesh N Patel
 * @date: 16-May-2021 12:55 PM
 */
class SubscriberVM(private val subRepo: SubscriberRepository) : ViewModel(), Observable {

    val subscribers = subRepo.subscribers

    private var isSubscriberUpdateOrDelete = false
    private lateinit var updateOrDeleteSubscriber: Subscriber

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    @Bindable
    val etName = MutableLiveData<String>()

    @Bindable
    val etEmail = MutableLiveData<String>()

    @Bindable
    val btnSaveOrUpdate = MutableLiveData<String>()

    @Bindable
    val btnDeleteOrDeleteAll = MutableLiveData<String>()

    init {
        initView()
    }

    private fun initView() {
        etName.value = null
        etEmail.value = null
        btnSaveOrUpdate.value = "Save"
        btnDeleteOrDeleteAll.value = "Delete All"
    }

    fun saveOrUpdate() {
        val name = etName.value!!
        val email = etEmail.value!!
        if (isSubscriberUpdateOrDelete) {
            updateOrDeleteSubscriber.name = name
            updateOrDeleteSubscriber.email = email
            update(updateOrDeleteSubscriber)
        } else {

            when {
                name.isEmpty() -> {
                    statusMessage.value = Event("Please enter subscriber name")
                }

                email.isEmpty() -> {
                    statusMessage.value = Event("Please enter subscriber email address")
                }

                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    statusMessage.value = Event("Please enter valid email address of subscriber")
                }

                else -> {
                    viewModelScope.launch {
                        val subscriber = Subscriber(name = name, email = email)
                        if (subRepo.isExistSubscriber(subscriber)) {
                            statusMessage.value = Event("Subscriber already exist")
                        } else {
                            insert(subscriber)
                            etName.value = null
                            etEmail.value = null
                        }
                    }
                }
            }


        }
    }

    fun deleteOrDeleteAll() {
        if (isSubscriberUpdateOrDelete) {
            delete(updateOrDeleteSubscriber)
        } else {
            deleteAll()
        }
    }

    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        val rawId = subRepo.insertSubscriber(subscriber)
        if (rawId > -1) {
            statusMessage.value = Event("Subscriber inserted successfully")
        } else {
            statusMessage.value = Event("Something went wrong")
        }

    }

    fun update(subscriber: Subscriber) = viewModelScope.launch {
        val noOfRows = subRepo.updateSubscriber(subscriber)
        if (noOfRows > 0) {
            initView()
            statusMessage.value = Event("Subscriber updated successfully")
        } else {
            statusMessage.value = Event("Something went wrong")
        }

    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        val noOfRows = subRepo.deleteSubscriber(subscriber)
        if (noOfRows > 0) {
            initView()
            statusMessage.value = Event("Subscriber deleted successfully")
        } else {
            statusMessage.value = Event("Something went wrong")
        }
    }


    private fun deleteAll() = viewModelScope.launch {
        val noOfRows = subRepo.deleteAllSubscriber()
        if (noOfRows > 0) {
            statusMessage.value = Event("All subscribers deleted successfully")
        } else {
            statusMessage.value = Event("Something went wrong")
        }
    }

    fun updateOrDeleteSubscriber(subscriber: Subscriber) {
        etName.value = subscriber.name
        etEmail.value = subscriber.email
        isSubscriberUpdateOrDelete = true
        updateOrDeleteSubscriber = subscriber
        btnSaveOrUpdate.value = "Update"
        btnDeleteOrDeleteAll.value = "Delete"
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}