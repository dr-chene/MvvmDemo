package com.dr_chene.official_demo

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
@Database(entities = [User::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}