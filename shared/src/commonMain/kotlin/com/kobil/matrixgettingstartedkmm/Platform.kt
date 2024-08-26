package com.kobil.matrixgettingstartedkmm

import com.russhwolf.settings.Settings
import net.folivo.trixnity.client.media.MediaStore
import org.koin.core.module.Module


expect class PlatformContextProvider {
     fun getPlatformSettings() : Settings
     suspend fun getPlatformRepositoryModule(): Module
     suspend fun getPlatformMediaStore(): MediaStore
}



