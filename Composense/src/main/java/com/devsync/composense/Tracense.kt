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

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.ConsumerIrManager
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devsync.composense.annotation.ComposenseExperimentalAPI


/**
 * A Composable that provides functionality to send IR (infrared) signals
 * using the device's IR emitter. This function utilizes the ConsumerIrManager
 * to transmit a specified signal pattern at a given frequency.
 *
 * @param frequency The frequency (in Hertz) at which the IR signal is transmitted.
 *
 * @param pattern An array of integers representing the pattern of the IR signal
 * to be sent. This pattern defines the duration of the IR signal on and off states.
 *
 * @param onIrSignalSent A callback invoked when the IR signal has been successfully sent.
 * Defaults to a no-op function.
 *
 * @param onError A callback invoked when an error occurs, such as when the device
 * does not have an IR emitter. The error message is passed as a parameter.
 * Defaults to a no-op function.
 *
 * @experimental This API is experimental and subject to change.
 */

@Composable
@ComposenseExperimentalAPI
fun SenseIRBlaster(
    frequency: Int,
    pattern: IntArray,
    onIrSignalSent: () -> Unit = {},
    onError: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val consumerIrManager = remember { context.getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager }
    if (consumerIrManager.hasIrEmitter()) {
        consumerIrManager.transmit(frequency, pattern)
        onIrSignalSent()
    } else {
        onError("Device does not have IR emitter.")
    }
}

/**
 * A Composable that provides functionality to read NFC (Near Field Communication) tags.
 * This function utilizes the NfcAdapter to enable foreground dispatch and handle
 * NFC tag readings.
 *
 * @param onTagRead A callback invoked when an NFC tag is successfully read.
 * The tag's data is passed as a String parameter. Defaults to a no-op function.
 *
 * @param onError A callback invoked when an error occurs, such as when NFC is
 * not supported on the device or if the tag does not support NDEF. The error message
 * is passed as a parameter. Defaults to a no-op function.
 *
 * @experimental This API is experimental and subject to change.
 */

@Composable
@ComposenseExperimentalAPI
fun SenseNFC(
    onTagRead: (String) -> Unit = {},
    onError: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val nfcAdapter = remember { NfcAdapter.getDefaultAdapter(context) }

    if (nfcAdapter == null) {
        onError("NFC is not supported on this device.")
        return
    }

    val onNewIntent = rememberUpdatedState { intent: Intent ->
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        tag?.let {
            val ndef = Ndef.get(it)
            if (ndef != null) {
                val message = ndef.cachedNdefMessage
                onTagRead(message.toString())
            } else {
                onError("NDEF not supported on this tag.")
            }
        }
    }
    LaunchedEffect(Unit) {
        onNewIntent
    }
    DisposableEffect(Unit) {
        nfcAdapter.enableForegroundDispatch(
            (context as android.app.Activity),
            PendingIntent.getActivity(context, 0, Intent(context, context::class.java),
                PendingIntent.FLAG_IMMUTABLE),
            null,
            null
        )
        onDispose {
            nfcAdapter.disableForegroundDispatch(context)
        }
    }
}

/**
 * A Composable that detects touch gestures such as taps and swipes
 * (left, right, up, and down) on a specified area of the screen.
 * This function utilizes gesture detection to trigger callbacks
 * based on user interactions.
 *
 * @param onTap A callback invoked when a tap gesture is detected.
 * Defaults to a no-op function.
 *
 * @param onSwipeLeft A callback invoked when a swipe left gesture
 * is detected. Defaults to a no-op function.
 *
 * @param onSwipeRight A callback invoked when a swipe right gesture
 * is detected. Defaults to a no-op function.
 *
 * @param onSwipeUp A callback invoked when a swipe up gesture is
 * detected. Defaults to a no-op function.
 *
 * @param onSwipeDown A callback invoked when a swipe down gesture is
 * detected. Defaults to a no-op function.
 *
 * @experimental This API is experimental and subject to change.
 */

@Composable
@ComposenseExperimentalAPI
fun SenseTouchGestures(
    onTap: () -> Unit = {},
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    onSwipeUp: () -> Unit = {},
    onSwipeDown: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .size(200.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onTap()
                    }
                )

                detectHorizontalDragGestures { change, dragAmount ->
                    if (dragAmount > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLeft()
                    }
                    change.consume()
                }

                detectVerticalDragGestures { change, dragAmount ->
                    if (dragAmount > 0) {
                        onSwipeDown()
                    } else {
                        onSwipeUp()
                    }
                    change.consume()
                }
            }
    )
}