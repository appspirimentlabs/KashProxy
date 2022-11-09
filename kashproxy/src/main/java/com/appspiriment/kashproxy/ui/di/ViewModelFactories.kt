package com.appspiriment.kashproxy.ui.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal fun createWithFactory(
    create: () -> ViewModel
): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")// Casting T as ViewModel
            return create.invoke() as T
        }
    }
}