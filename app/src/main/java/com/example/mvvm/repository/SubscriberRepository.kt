package com.example.mvvm.repository

import com.example.mvvm.db.dao.SubscriberDAO
import com.example.mvvm.db.entity.Subscriber

/**
 * SubscriberRepository.kt - Intermediate class to access data.
 * @author:  Jignesh N Patel
 * @date: 15-May-2021 3:32 PM
 */
class SubscriberRepository(private val subDAO: SubscriberDAO) {

    val subscribers = subDAO.getAllSubscribers()


    suspend fun isExistSubscriber(subscriber: Subscriber): Boolean {
        return subDAO.isSubscriberExist(subscriber.email) > 0
    }

    suspend fun insertSubscriber(subscriber: Subscriber): Long {
        return subDAO.addSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber): Int {
        return subDAO.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber): Int {
        return subDAO.deleteSubscriber(subscriber)
    }

    suspend fun deleteAllSubscriber(): Int {
        return subDAO.deleteAll()
    }
}