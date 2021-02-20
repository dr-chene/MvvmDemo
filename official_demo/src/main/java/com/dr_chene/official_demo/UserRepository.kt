package com.dr_chene.official_demo

import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executor

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
class UserRepository constructor(
        private val webService: WebService,
        private val userDao: UserDao
) {

     fun getUser(userId: String) =
            object : NetWorkBoundResource<User>() {
                override suspend fun saveCallResult(item: User) {
                    userDao.save(item)
                }

                override fun shouldFetch(data: User?): Boolean {
                    return data == null
                }

                override suspend fun loadFromDb(): Flow<User> {
                    return userDao.load(userId)
                }

                override fun createCall(): Flow<ApiResponse<User>> {
                    return webService.getUser(userId)
                }

            }
}