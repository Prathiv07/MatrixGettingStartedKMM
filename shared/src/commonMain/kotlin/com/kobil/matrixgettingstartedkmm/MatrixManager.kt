package com.kobil.matrixgettingstartedkmm

import com.russhwolf.settings.nullableString
import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.createTrixnityBotModules
import net.folivo.trixnity.client.fromStore
import net.folivo.trixnity.client.login
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.serverDiscovery
import net.folivo.trixnity.client.store.RoomUser
import net.folivo.trixnity.clientserverapi.model.authentication.IdentifierType
import net.folivo.trixnity.core.model.RoomId
import net.folivo.trixnity.core.model.UserId
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object MatrixManager : KoinComponent {

    private var matrixClient: MatrixClient? = null
    private var deviceId by get<PlatformContextProvider>().getPlatformSettings()
        .nullableString("DEVICE_ID")

    suspend fun userSessionFound(): Boolean {
        val session = MatrixClient.fromStore(
            mediaStore = get<PlatformContextProvider>().getPlatformMediaStore(),
            repositoriesModule = get<PlatformContextProvider>().getPlatformRepositoryModule(),
        ).getOrNull()

        if (session != null) {
            matrixClient = session
            return true
        }
        return false
    }

    suspend fun login(
        server: String,
        username: String,
        password: String,
    ) :String? {
        val url = server.serverDiscovery().getOrNull() ?: Url(server)
        matrixClient = MatrixClient.login(
            baseUrl = url,
            mediaStore = get<PlatformContextProvider>().getPlatformMediaStore(),
            repositoriesModule = get<PlatformContextProvider>().getPlatformRepositoryModule(),
            identifier = IdentifierType.User(username),
            password = password,
            deviceId = deviceId,
        ).getOrNull()
        deviceId = matrixClient?.deviceId
        return deviceId
    }

    suspend fun logout() {
        matrixClient?.apply {
            logout()
            clearCache()
            clearMediaCache()
            stop()
        }
        matrixClient = null
    }

    suspend fun stop() {
        matrixClient?.stop()
        matrixClient = null
    }

}
