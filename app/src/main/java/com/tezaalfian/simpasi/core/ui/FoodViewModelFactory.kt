package com.tezaalfian.simpasi.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository
import com.tezaalfian.simpasi.core.data.source.repository.FoodRepository
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository
import com.tezaalfian.simpasi.core.di.Injection
import com.tezaalfian.simpasi.view.login.LoginViewModel
import com.tezaalfian.simpasi.view.main.children.ChildrenViewModel
import com.tezaalfian.simpasi.view.main.food.FoodViewModel
import com.tezaalfian.simpasi.view.main.home.HomeViewModel
import com.tezaalfian.simpasi.view.main.profile.ProfileViewModel
import com.tezaalfian.simpasi.view.register.RegisterViewModel
import com.tezaalfian.simpasi.view.splash.SplashViewModel

class FoodViewModelFactory private constructor(private val foodRepository: FoodRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: FoodViewModelFactory? = null

        fun getInstance(context: Context): FoodViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance ?: FoodViewModelFactory(Injection.provideFoodRepository(context))
            }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FoodViewModel::class.java) -> {
                FoodViewModel(foodRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(foodRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}