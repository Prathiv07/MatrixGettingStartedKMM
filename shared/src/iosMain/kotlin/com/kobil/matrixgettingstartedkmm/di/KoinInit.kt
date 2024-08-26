package com.kobil.matrixgettingstartedkmm.di

import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

fun initKoin() {
    val modules = platformContextProviderModule

    startKoin {
        modules(modules)
    }
}
