package com.dr_chene.official_demo

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
@Entity
data class User(
        @PrimaryKey private val id: String,
        private val name: String,
        private val  lastName: String
)