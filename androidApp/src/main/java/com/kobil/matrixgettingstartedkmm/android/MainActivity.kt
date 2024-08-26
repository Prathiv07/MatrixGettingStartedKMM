package com.kobil.matrixgettingstartedkmm.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.kobil.matrixgettingstartedkmm.MatrixManager
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var deviceId: String? by remember {
                mutableStateOf(null)
            }
            var inProgress by remember {
                mutableStateOf(false)
            }

            MyApplicationTheme {
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
                                    username = "{UserName}",
                                    password = "{Password}",
                                )
                                inProgress = false
                                Log.d("SDK", deviceId ?: "Unable to login")
                            }
                        }) {
                            if (inProgress) {
                                CircularProgressIndicator()
                            } else {
                                Text(text = "Check Matrix")
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = deviceId.toString())

                    }

                }
            }
        }
    }
}


@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
    }
}
