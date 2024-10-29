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

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.devsync.composense.annotation.ComposenseExperimentalAPI

/**
 * A Composable that provides functionality to read RGB light sensor values
 * from the device's light sensor. This function utilizes the SensorManager
 * to register a listener and handle changes in the light sensor data.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the sensor values change.
 * The sensor event is passed as a parameter. Defaults to a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onRGBValuesChanged A callback invoked when RGB values are updated.
 * The red, green, and blue values (r, g, b) are passed as parameters,
 * normalized between 0.0 and 1.0. Defaults to a no-op function.
 *
 * @experimental This API is experimental and subject to change.
 */

@Composable
@ComposenseExperimentalAPI
fun SenseRGBLight(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onRGBValuesChanged: (r: Float, g: Float, b: Float) -> Unit = { _, _, _ -> }
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val rgbSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_LIGHT) {
                    val r = event.values[0] / 255.0f
                    val g = event.values[1] / 255.0f
                    val b = event.values[2] / 255.0f
                    onSensorChanged(event)
                    onRGBValuesChanged(r, g, b)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        rgbSensor?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}
