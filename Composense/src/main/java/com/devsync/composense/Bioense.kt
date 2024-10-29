/*
 * Composense - An Open Source Project
 * 
 * Copyright (c) 2024 DeveloperSyndicate
 * Author: Sanjay
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Created on: 29-10-2024
 */

package com.devsync.composense

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.devsync.composense.annotation.ComposenseExperimentalAPI

/**
 * A Composable that provides fingerprint authentication functionality using
 * Android's Biometric API. This Composable displays a prompt to the user
 * for fingerprint authentication and handles success, error, and failure callbacks.
 *
 * @param promptTitle The title displayed on the authentication prompt.
 * Defaults to "Fingerprint Authentication".
 *
 * @param promptSubtitle The subtitle displayed on the authentication prompt.
 * Defaults to "Place your finger on the sensor".
 *
 * @param negativeButtonText The text for the negative button (e.g., "Cancel").
 * Defaults to "Cancel".
 *
 * @param onAuthenticationSuccess A callback invoked when the authentication
 * is successful. The result of the authentication is passed as a parameter.
 * Defaults to a no-op function.
 *
 * @param onAuthenticationError A callback invoked when there is an error
 * during the authentication process. The error message and code are passed
 * as parameters. Defaults to a no-op function that ignores errors.
 *
 * @param onAuthenticationFailed A callback invoked when the authentication
 * fails (e.g., when the fingerprint does not match). Defaults to a no-op function.
 *
 * @requires API level 28 (Build.VERSION_CODES.P) or higher.
 * @experimental This API is experimental and subject to change.
 */
@Composable
@RequiresApi(Build.VERSION_CODES.P)
@ComposenseExperimentalAPI
fun FingerprintAuthenticator(
    promptTitle: String = "Fingerprint Authentication",
    promptSubtitle: String = "Place your finger on the sensor",
    negativeButtonText: String = "Cancel",
    onAuthenticationSuccess: (BiometricPrompt.AuthenticationResult) -> Unit = {},
    onAuthenticationError: (String, Int) -> Unit = {_, _ ->},
    onAuthenticationFailed: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val biometricPrompt: BiometricPrompt = remember {
        BiometricPrompt(
            activity as FragmentActivity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onAuthenticationSuccess(result)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    onAuthenticationError(errString.toString(), errorCode)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onAuthenticationFailed()
                }
            }
        )
    }
    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(promptTitle)
            .setSubtitle(promptSubtitle)
            .setNegativeButtonText(negativeButtonText)
            .build()
    }
    biometricPrompt.authenticate(promptInfo)
}

/**
 * A Composable that provides face authentication functionality using
 * Android's Biometric API. This Composable displays a prompt to the user
 * for face authentication and handles success, error, and failure callbacks.
 *
 * @param promptTitle The title displayed on the authentication prompt.
 * Defaults to "Face Authentication".
 *
 * @param promptSubtitle The subtitle displayed on the authentication prompt.
 * Defaults to "Look at the camera".
 *
 * @param negativeButtonText The text for the negative button (e.g., "Cancel").
 * Defaults to "Cancel".
 *
 * @param onAuthenticationSuccess A callback invoked when the authentication
 * is successful. The result of the authentication is passed as a parameter.
 * Defaults to a no-op function.
 *
 * @param onAuthenticationError A callback invoked when there is an error
 * during the authentication process. The error message and code are passed
 * as parameters. Defaults to a no-op function that ignores errors.
 *
 * @param onAuthenticationFailed A callback invoked when the authentication
 * fails (e.g., when the face does not match). Defaults to a no-op function.
 *
 * @requires API level 28 (Build.VERSION_CODES.P) or higher.
 * @experimental This API is experimental and subject to change.
 */

@Composable
@RequiresApi(Build.VERSION_CODES.P)
@ComposenseExperimentalAPI
fun FaceAuthenticator(
    promptTitle: String = "Face Authentication",
    promptSubtitle: String = "Look at the camera",
    negativeButtonText: String = "Cancel",
    onAuthenticationSuccess: (BiometricPrompt.AuthenticationResult) -> Unit = {},
    onAuthenticationError: (String, Int) -> Unit = { _, _ -> },
    onAuthenticationFailed: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val biometricPrompt: BiometricPrompt = remember {
        BiometricPrompt(
            activity as FragmentActivity,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onAuthenticationSuccess(result)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    onAuthenticationError(errString.toString(), errorCode)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onAuthenticationFailed()
                }
            }
        )
    }
    val promptInfo = remember {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(promptTitle)
            .setSubtitle(promptSubtitle)
            .setNegativeButtonText(negativeButtonText)
            .build()
    }
    biometricPrompt.authenticate(promptInfo)
}