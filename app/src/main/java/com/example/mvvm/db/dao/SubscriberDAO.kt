package com.example.mvvm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvm.db.entity.Subscriber

/**
 * SubscriberDAO - DAO class to access subscriber entity.
 * @author:  Jignesh N Patel
 * @date: 10-May-2021 6:53 PM
 */

@Dao
interface SubscriberDAO {

    // Insert single subscriber
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubscriber(subscriber: Subscriber): Long // Will return inserted id of row

    // Insert multiple subscriber
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubscribers(subscribers: ArrayList<Subscriber>): List<Long> // Will return inserted id of rows


    // Insert multiple subscriber
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubscribers(vararg subscribers: Subscriber): List<Long> // Will return inserted id of rows


    // Update single subscriber
    @Update
    suspend fun updateSubscriber(subscriber: Subscriber): Int // Will return number of row updated

    // Update multiple subscriber
    @Update
    suspend fun updateSubscribers(subscribers: ArrayList<Subscriber>): Int  // Will return number of row updated


    // Delete single subscriber
    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber): Int // Will return number of row deleted


    // Delete single subscriber by id
    @Query("DELETE from subscriber_data_table where subscriber_id=:subscriber_id")
    suspend fun deleteSubscriber(subscriber_id: Int): Int // Will return number of row deleted

    // Delete all the records from the table
    @Query("DELETE from subscriber_data_table")
    suspend fun deleteAll(): Int // Will return number of row deleted


    // Get all subscribers
    @Query("SELECT * from subscriber_data_table ORDER BY subscriber_id DESC")
    fun getAllSubscribers(): LiveData<List<Subscriber>>

    // Get subscriber by id
    @Query("SELECT * from subscriber_data_table where subscriber_id=:subscriber_id")
    fun getSubscriber(subscriber_id: Int): LiveData<Subscriber>


    // To know if subscriber already exist by email address
    @Query("SELECT COUNT(*) FROM subscriber_data_table WHERE subscriber_email=:subscriber_email")
    suspend fun isSubscriberExist(subscriber_email: String): Int // return 0 if not exits otherwise return >0


}
