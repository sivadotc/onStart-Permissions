package com.example.locationpermission

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.locationpermission.ui.theme.LocationPermissionTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationPermissionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val permissionsState = rememberMultiplePermissionsState(
                        permissions = listOf
                            (
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                            )
                    )

                    val lifecycleOwner = LocalLifecycleOwner.current
                    DisposableEffect(
                        key1 = lifecycleOwner,
                        effect = {
                        val observer = LifecycleEventObserver{ _, event ->
                            if (event == Lifecycle.Event.ON_START){
                                permissionsState.launchMultiplePermissionRequest()
                            }
                        }
                            lifecycleOwner.lifecycle.addObserver(observer)

                            onDispose {
                                lifecycleOwner.lifecycle.removeObserver(observer)
                            }
                    }
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        permissionsState.permissions.forEach { perm ->
                            when(perm.permission){
                                Manifest.permission.CAMERA ->{
                                    when {
                                        perm.hasPermission ->{
                                            Text(text = "Camera Permission is granted")
                                        }
                                        perm.shouldShowRationale ->{
                                            Text(text = "Camera Permission is required to take photos")
                                        }
                                        perm.isPermanentlyDenied() ->{
                                            Text(text = "Camera Permission is Disabled, you can got to app settings to enable it back.")
                                        }
                                    }

                                }
                                Manifest.permission.RECORD_AUDIO ->{
                                    when {
                                        perm.hasPermission ->{
                                            Text(text = "Audio Recording Permission is granted")
                                        }
                                        perm.shouldShowRationale ->{
                                            Text(text = "Audio Recording Permission is required to take photos")
                                        }
                                        perm.isPermanentlyDenied() ->{
                                            Text(text = "Audio Recording Permission is Disabled, you can got to app settings to enable it back.")
                                        }
                                    }
                                }
                                Manifest.permission.ACCESS_COARSE_LOCATION ->{
                                    when {
                                        perm.hasPermission ->{
                                            Text(text = "LOCATION Permission is granted")
                                        }
                                        perm.shouldShowRationale ->{
                                            Text(text = "LOCATION Permission is required to take photos")
                                        }
                                        perm.isPermanentlyDenied() ->{
                                            Text(text = "LOCATION Permission is Disabled, you can got to app settings to enable it back.")
                                        }
                                    }

                                }

                            }
                        }
                    }

                }
            }
        }
    }
}

