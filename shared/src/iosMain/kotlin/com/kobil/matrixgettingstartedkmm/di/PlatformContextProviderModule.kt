package com.kobil.matrixgettingstartedkmm.di

import com.kobil.matrixgettingstartedkmm.PlatformContextProvider
import org.koin.dsl.module

val platformContextProviderModule = module {
    single {
        PlatformContextProvider()
    }
}
