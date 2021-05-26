package com.example.mvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm.db.dao.SubscriberDAO
import com.example.mvvm.db.entity.Subscriber

/**
 * SubscriberDatabase.kt - To access room database dao and entity.
 * @author:  Jignesh N Patel
 * @date: 15-May-2021 3:00 PM
 */

private const val ROOM_DB_NAME = "subscribers_data_database"

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase : RoomDatabase() {

    abstract val subscriberDAO: SubscriberDAO

    companion object {
        @Volatile
        private var INSTANCE: SubscriberDatabase? = null

        fun getInstance(mContext: Context): SubscriberDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(mContext.applicationContext, SubscriberDatabase::class.java, ROOM_DB_NAME).build()
                }
                return instance
            }
        }
    }
}