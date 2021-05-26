package com.example.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.databinding.ListItemSubscribersBinding
import com.example.mvvm.db.entity.Subscriber

/**
 * SubscriberAdapter - .
 * @author:  Jignesh N Patel
 * @date: 25-May-2021 6:31 PM
 */
class SubscribersAdapter(private val clickListener: (Subscriber) -> Unit) : RecyclerView.Adapter<SubscribersAdapter.SubscriberViewHolder>() {


    private lateinit var subscribers: ArrayList<Subscriber>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ListItemSubscribersBinding = DataBindingUtil.inflate(inflater, R.layout.list_item_subscribers, parent, false)
        return SubscriberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribers[position], clickListener)

    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    fun setSubscribers(subscribers: List<Subscriber>) {
        if (this.subscribers.isNotEmpty()) this.subscribers.clear()
        this.subscribers = subscribers as ArrayList<Subscriber>
        notifyDataSetChanged()
    }

    class SubscriberViewHolder(private val binding: ListItemSubscribersBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
            binding.tvSubscriber.text = subscriber.name
            binding.tvMail.text = subscriber.email

            binding.root.setOnClickListener {
                clickListener(subscriber)
            }
        }
    }
}

