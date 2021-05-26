package com.example.mvvm.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Subscriber - Subscriber entity class of room db.
 * @author:  Jignesh N Patel
 * @date: 10-May-2021 6:40 PM
 */

@Entity(tableName = "subscriber_data_table")
data class Subscriber(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "subscriber_id") val id: Int = 0,
    @ColumnInfo(name = "subscriber_name") var name: String,
    @ColumnInfo(name = "subscriber_email") var email: String
) : Serializable