package com.kobil.matrixgettingstartedkmm.android

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.kobil.matrixgettingstartedkmm.MatrixManager
import com.kobil.matrixgettingstartedkmm.PlatformContextProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MatrixGettingStarted : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MatrixGettingStartedKMMApp(lifecycleScope, this)
            }
        }
    }
}


@Composable
fun MatrixGettingStartedKMMApp(
    lifecycleScope: CoroutineScope,
    context: Context
) {
    val platformModule = module {
        single {
            PlatformContextProvider(context)
        }
    }

    startKoin {
        androidContext(context)
        modules(platformModule)
    }

    var deviceId: String? by remember {
        mutableStateOf(null)
    }
    var inProgress by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(enabled = !inProgress, onClick = {
                inProgress = true
                lifecycleScope.launch {
                    deviceId = MatrixManager.login(
                        server = "matrix.org",
                        username = "prathiv1",
                        password = "Kobiltest1",
                    )
                    inProgress = false
                    Log.d("SDK", deviceId ?: "Unable to login")
                }
            }) {
                if (inProgress) {
                    CircularProgressIndicator()
                } else {
                    Text(text = "Login At matrix.org")
                }
            }
            Text(
                modifier = Modifier
                    .padding(horizontal = 20.dp),
                text = "Credentials are hardcoded and login session will be created with successful login"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Logged in devices Id : $deviceId")

        }

    }
}