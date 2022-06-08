package com.tezaalfian.simpasi.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository
import com.tezaalfian.simpasi.core.di.Injection
import com.tezaalfian.simpasi.view.login.LoginViewModel
import com.tezaalfian.simpasi.view.main.children.ChildrenViewModel
import com.tezaalfian.simpasi.view.register.RegisterViewModel
import com.tezaalfian.simpasi.view.splash.SplashViewModel

class UserViewModelFactory private constructor(private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: UserViewModelFactory? = null

        fun getInstance(context: Context): UserViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance ?: UserViewModelFactory(Injection.provideUserRepository(context))
            }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}