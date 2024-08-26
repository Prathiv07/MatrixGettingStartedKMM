package com.kobil.matrixgettingstartedkmm.android

import android.app.Application
import com.kobil.matrixgettingstartedkmm.PlatformContextProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MatrixGettingStartedKMMApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val platformModule = module {
            single {
                PlatformContextProvider(this@MatrixGettingStartedKMMApp)
            }
        }

        startKoin {
            androidContext(this@MatrixGettingStartedKMMApp)
            modules(platformModule)
        }
    }
}

