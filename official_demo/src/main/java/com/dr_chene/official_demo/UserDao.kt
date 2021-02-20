package com.dr_chene.official_demo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    fun load(userId: String): Flow<User>
}