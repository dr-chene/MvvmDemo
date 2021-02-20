package com.dr_chene.official_demo

import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 * created by dr_chene on 2021/2/20
 * desc
 */
class UserProfileViewModel(
        //savedStateHandle允许ViewModel访问相关Fragment或Activity的已保存状态和参数
        savedStateHandle: SavedStateHandle,
        userRepository: UserRepository
) : ViewModel() {
    val userId : String = savedStateHandle["uid"] ?: throw IllegalAccessException("miss user uid")
    val user : LiveData<User> = TODO()
    private val _user = MutableLiveData<User>()
}