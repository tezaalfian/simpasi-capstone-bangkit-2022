package com.tezaalfian.simpasi.core.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tezaalfian.simpasi.core.data.source.repository.ChildRepository
import com.tezaalfian.simpasi.core.data.source.repository.UserRepository
import com.tezaalfian.simpasi.core.di.Injection
import com.tezaalfian.simpasi.view.main.children.ChildrenViewModel

class ChildViewModelFactory private constructor(private val childRepository: ChildRepository, private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ChildViewModelFactory? = null

        fun getInstance(context: Context): ChildViewModelFactory =
            instance
                ?: synchronized(this) {
                    instance ?: ChildViewModelFactory(Injection.provideChildRepository(context), Injection.provideUserRepository(context))
            }.also { instance = it }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ChildrenViewModel::class.java) -> {
                ChildrenViewModel(childRepository, userRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}