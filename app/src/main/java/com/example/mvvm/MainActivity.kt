package com.example.mvvm

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.adapter.SubscribersAdapter
import com.example.mvvm.databinding.ActivityMainBinding
import com.example.mvvm.db.SubscriberDatabase
import com.example.mvvm.repository.SubscriberRepository
import com.example.mvvm.viewmodel.SubscriberVM
import com.example.mvvm.viewmodelfactory.SubscriberVMFactory

class MainActivity : AppCompatActivity() {


    private val TAG = javaClass.simpleName
    private lateinit var mActivity: Activity


    lateinit var binding: ActivityMainBinding
    lateinit var subscriberVM: SubscriberVM
    lateinit var adapter: SubscribersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this@MainActivity
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val subDao = SubscriberDatabase.getInstance(this).subscriberDAO
        val subRepo = SubscriberRepository(subDao)
        val subFactory = SubscriberVMFactory(subRepo)
        subscriberVM = ViewModelProvider(this, subFactory).get(SubscriberVM::class.java)
        binding.mySubVM = subscriberVM
        binding.lifecycleOwner = this
        initSubscribers()
    }

    private fun initSubscribers() {

        subscriberVM.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(mActivity, it, Toast.LENGTH_SHORT).show()
            }
        })

        adapter = SubscribersAdapter() { subscriber ->
            subscriberVM.updateOrDeleteSubscriber(subscriber)
        }
        binding.rvSubscribers.adapter = adapter


        subscriberVM.subscribers.observe(this, Observer {
            Log.i(TAG, it.toString())
            adapter.setSubscribers(it)
        })
    }


}