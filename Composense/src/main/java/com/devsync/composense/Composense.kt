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
import android.hardware.ConsumerIrManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.devsync.composense.annotation.ComposenseExperimentalAPI
import kotlin.math.sqrt

/**
 * A Composable that provides access to the device's accelerometer sensor.
 * This function registers a listener to receive updates on accelerometer
 * sensor data and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the accelerometer sensor values change.
 * The sensor event is passed as a parameter. Defaults to a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onSensorDataChanged A callback invoked when accelerometer data is updated.
 * The x, y, and z values of the acceleration are passed as parameters.
 * Defaults to a no-op function.
 */

@Composable
fun SenseAccelerometer(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = {_, _ ->},
    onSensorDataChanged: (x: Float, y: Float, z: Float) -> Unit = {_, _, _ -> }
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    onSensorChanged(event)
                    onSensorDataChanged(event.values[0], event.values[1], event.values[2])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(sensorListener, accelerometer, sensorDelay)
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's gyroscope sensor.
 * This function registers a listener to receive updates on gyroscope sensor
 * data and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the gyroscope sensor values change.
 * The sensor event is passed as a parameter. Defaults to a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onSensorDataChanged A callback invoked when gyroscope data is updated.
 * The x, y, and z values of the gyroscope's angular velocity are passed as parameters.
 * Defaults to a no-op function.
 */

@Composable
fun SenseGyroscope(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onSensorDataChanged: (x: Float, y: Float, z: Float) -> Unit = { _, _, _ -> }
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gyroscope = remember { sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                    onSensorChanged(event)
                    onSensorDataChanged(event.values[0], event.values[1], event.values[2])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        gyroscope?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's magnetometer sensor.
 * This function registers a listener to receive updates on magnetometer sensor
 * data and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the magnetometer sensor values change.
 * The sensor event is passed as a parameter. Defaults to a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onSensorDataChanged A callback invoked when magnetometer data is updated.
 * The x, y, and z values of the magnetic field strength are passed as parameters.
 * Defaults to a no-op function.
 */

@Composable
fun SenseMagnetometer(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onSensorDataChanged: (x: Float, y: Float, z: Float) -> Unit = { _, _, _ -> }
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val magnetometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                    onSensorChanged(event)
                    onSensorDataChanged(event.values[0], event.values[1], event.values[2])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        magnetometer?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's proximity sensor.
 * This function registers a listener to receive updates on proximity sensor
 * data and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the proximity sensor values change.
 * The sensor event is passed as a parameter. Defaults to a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onProximityChanged A callback invoked when proximity data is updated.
 * The distance to the nearest object (in centimeters) is passed as a parameter.
 * Defaults to a no-op function.
 */

@Composable
fun SenseProximity(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onProximityChanged: (distance: Float) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val proximitySensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_PROXIMITY) {
                    onSensorChanged(event)
                    onProximityChanged(event.values[0])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        proximitySensor?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's ambient light sensor.
 * This function registers a listener to receive updates on ambient light
 * levels and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the ambient light sensor
 * values change. The sensor event is passed as a parameter. Defaults to a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onLightLevelChanged A callback invoked when the ambient light level
 * is updated. The light level in lux is passed as a parameter.
 * Defaults to a no-op function.
 */

@Composable
fun SenseAmbientLight(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onLightLevelChanged: (lux: Float) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val lightSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_LIGHT) {
                    onSensorChanged(event)
                    onLightLevelChanged(event.values[0])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        lightSensor?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's barometer sensor.
 * This function registers a listener to receive updates on atmospheric
 * pressure changes and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the barometer sensor
 * values change. The sensor event is passed as a parameter. Defaults to
 * a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onPressureChanged A callback invoked when the atmospheric pressure
 * is updated. The pressure value in hPa is passed as a parameter.
 * Defaults to a no-op function.
 */

@Composable
fun SenseBarometer(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onPressureChanged: (pressure: Float) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val barometerSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_PRESSURE) {
                    onSensorChanged(event)
                    onPressureChanged(event.values[0])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        barometerSensor?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's heart rate sensor.
 * This function registers a listener to receive updates on heart rate
 * changes and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the heart rate sensor
 * values change. The sensor event is passed as a parameter. Defaults to
 * a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onHeartRateChanged A callback invoked when the heart rate is updated.
 * The heart rate value in beats per minute (BPM) is passed as a parameter.
 * Defaults to a no-op function.
 */

@Composable
fun SenseHeartRate(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onHeartRateChanged: (heartRate: Float) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val heartRateSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_HEART_RATE) {
                    onSensorChanged(event)
                    onHeartRateChanged(event.values[0])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        heartRateSensor?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's ambient temperature sensor.
 * This function registers a listener to receive updates on temperature changes
 * and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the ambient temperature sensor
 * values change. The sensor event is passed as a parameter. Defaults to
 * a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onTemperatureChanged A callback invoked when the ambient temperature
 * is updated. The temperature value in degrees Celsius is passed as a parameter.
 * Defaults to a no-op function.
 */

@Composable
fun SenseAmbientTemperature(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onTemperatureChanged: (temperature: Float) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val thermometerSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    onSensorChanged(event)
                    onTemperatureChanged(event.values[0])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        thermometerSensor?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's relative humidity sensor.
 * This function registers a listener to receive updates on humidity changes
 * and handles changes in sensor accuracy.
 *
 * @param sensorDelay The delay in microseconds between sensor updates.
 * Default is [SensorManager.SENSOR_DELAY_NORMAL].
 *
 * @param onSensorChanged A callback invoked when the relative humidity sensor
 * values change. The sensor event is passed as a parameter. Defaults to
 * a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onHumidityChanged A callback invoked when the relative humidity
 * is updated. The humidity value in percentage is passed as a parameter.
 * Defaults to a no-op function.
 */

@Composable
fun SenseHumidity(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onHumidityChanged: (humidity: Float) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val humiditySensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_RELATIVE_HUMIDITY) {
                    onSensorChanged(event)
                    onHumidityChanged(event.values[0])
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        humiditySensor?.let {
            sensorManager.registerListener(sensorListener, it, sensorDelay)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's step counter sensor.
 * This function registers a listener to receive updates on step count changes
 * and handles changes in sensor accuracy.
 *
 * @param onSensorChanged A callback invoked when the step counter sensor
 * values change. The sensor event is passed as a parameter. Defaults to
 * a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor
 * changes. The sensor and its accuracy are passed as parameters.
 * Defaults to a no-op function.
 *
 * @param onStepCountChanged A callback invoked when the step count is updated.
 * The step count value is passed as an integer parameter. Defaults to
 * a no-op function.
 */

@Composable
fun SenseStepCounter(
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onStepCountChanged: (stepCount: Int) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val stepCounterSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                    onSensorChanged(event)
                    onStepCountChanged(event.values[0].toInt())
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        stepCounterSensor?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that provides access to the device's step counter sensor, functioning
 * as a pedometer. It tracks and reports the number of steps taken by the user.
 *
 * @param onSensorChanged A callback invoked when the step counter sensor values change.
 * The sensor event is passed as a parameter. Defaults to a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor changes.
 * The sensor and its accuracy are passed as parameters. Defaults to a no-op function.
 *
 * @param onStepCountChanged A callback invoked when the step count is updated.
 * The updated step count value is passed as an integer parameter. Defaults to
 * a no-op function.
 */

@Composable
fun SensePedometer(
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onStepCountChanged: (stepCount: Int) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val stepCounterSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) }

    var totalSteps = remember { mutableIntStateOf(0) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                    totalSteps.intValue = event.values[0].toInt()
                    onSensorChanged(event)
                    onStepCountChanged(totalSteps.intValue)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }

    DisposableEffect(Unit) {
        stepCounterSensor?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

/**
 * A Composable that listens for shake gestures using the device's accelerometer sensor.
 * It detects significant movements (shakes) based on the acceleration values.
 *
 * @param onSensorChanged A callback invoked when the accelerometer sensor values change.
 * The sensor event is passed as a parameter. Defaults to a no-op function.
 *
 * @param onAccuracyChanged A callback invoked when the accuracy of the sensor changes.
 * The sensor and its accuracy are passed as parameters. Defaults to a no-op function.
 *
 * @param onGestureDetected A callback invoked when a shake gesture is detected.
 * The type of gesture detected (in this case, "Shake Detected") is passed as a string parameter.
 * Defaults to a no-op function.
 */

@Composable
@ComposenseExperimentalAPI
fun ShakeSensor(
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onGestureDetected: (gestureType: String) -> Unit = {}
) {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gestureSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val acceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                if (acceleration > 12) {
                    onGestureDetected("Shake Detected")
                }

                onSensorChanged(event)
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                onAccuracyChanged(sensor, accuracy)
            }
        }
    }
    DisposableEffect(Unit) {
        gestureSensor?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }
}
