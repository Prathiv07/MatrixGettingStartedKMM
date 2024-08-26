package com.kobil.matrixgettingstartedkmm

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import net.folivo.trixnity.client.media.MediaStore
import net.folivo.trixnity.client.media.okio.OkioMediaStore
import net.folivo.trixnity.client.store.repository.realm.createRealmRepositoriesModule
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module

actual class  PlatformContextProvider(private val context: Context)  {

    actual fun getPlatformSettings(): Settings = SharedPreferencesSettings(
        context.getSharedPreferences("MatrixGettingStartedKMM", Context.MODE_PRIVATE)
    )

    private fun getCacheDirectoryPath(context: Context): Path =
        context.cacheDir.absolutePath.toPath().resolve("cache")

    actual suspend fun getPlatformRepositoryModule(): Module = createRealmRepositoriesModule {
        directory(getCacheDirectoryPath(context).resolve("realm").toString())
    }

    actual suspend fun getPlatformMediaStore(): MediaStore = OkioMediaStore(
        getCacheDirectoryPath(context).resolve("media")
    )
}

