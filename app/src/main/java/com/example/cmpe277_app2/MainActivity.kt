package com.example.cmpe277_app2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.cmpe277_app2.ui.theme.CMPE277App2Theme


class MainActivity : ComponentActivity() {

    private var inputURL by mutableStateOf("")
    private var inputPhone by mutableStateOf("")

    @SuppressLint("QueryPermissionsNeeded")
    @Composable
    fun DialNumberButton(phoneNumber: String) {
        val context = LocalContext.current
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 101)
            } else {
                Log.d("MainActivity", "permission granted")
            }

            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)

        }) {
            Text(text = "Dial $phoneNumber")
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    @Composable
    fun OpenURLButton(url: String) {
        val context = LocalContext.current
        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            context.startActivity(intent)

        }) {
            Text(text = "Open $url")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CMPE277App2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Implicit Intents", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(value = inputURL, modifier = Modifier.fillMaxWidth(), onValueChange = { newText ->
                                    inputURL = newText
                                }, label = { Text(text = "Input URL") })
                                OpenURLButton(inputURL)
                            }
                        }
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedTextField(value = inputPhone, modifier = Modifier.fillMaxWidth(), onValueChange = { newText ->
                                    inputPhone = newText
                                }, label = { Text(text = "Input Phone Number") })
                                DialNumberButton(inputPhone)
                            }
                        }
                        Button(onClick = {
                            finish()
                        }) { Text(text = "Close App") }
                    }
                }
            }
        }
    }
}