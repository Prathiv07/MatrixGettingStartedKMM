package com.kobil.matrixgettingstartedkmm

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import net.folivo.trixnity.client.media.MediaStore
import net.folivo.trixnity.client.media.okio.OkioMediaStore
import net.folivo.trixnity.client.store.repository.realm.createRealmRepositoriesModule
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSUserDomainMask


actual class PlatformContextProvider {
    actual fun getPlatformSettings(): Settings {
        return NSUserDefaultsSettings(
            NSUserDefaults("MatrixGettingStartedKMM")
        )
    }

    private fun getCacheDirectoryPath(): Path {
        val cacheDir = NSSearchPathForDirectoriesInDomains(
            NSCachesDirectory,
            NSUserDomainMask,
            true
        ).first() as String
        return "$cacheDir/cache".toPath()
    }

    actual suspend fun getPlatformRepositoryModule(): Module {
        return createRealmRepositoriesModule {
            directory(getCacheDirectoryPath().resolve("realm").toString())
        }
    }

    actual suspend fun getPlatformMediaStore(): MediaStore = OkioMediaStore(
        getCacheDirectoryPath().resolve("media")
    )

}
