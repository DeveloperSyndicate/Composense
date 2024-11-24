# Composense: Jetpack Compose Wrapper for Android Sensor Data

## Overview

**Composense** is a Jetpack Compose-based Android library that simplifies the process of accessing sensor data (e.g., accelerometer, gyroscope, etc.) in Android applications. It provides easy-to-use Composables to fetch and observe sensor values in a declarative manner, enabling seamless integration with Jetpack Compose-based UIs.

This library abstracts the complexity of handling sensor events and managing sensor lifecycles into simple, reusable Composable functions.

### Key Features

- **Declarative API**: Use Jetpack Compose-style `@Composable` functions to get sensor data.
- **Customizable Callbacks**: Easily pass callback functions for handling sensor data changes and sensor accuracy updates.
- **Automatic Lifecycle Management**: Automatically manages the sensor listener registration and unregistration with the `DisposableEffect`.

### Supported Sensors

- **Accelerometer** (measures acceleration along X, Y, and Z axes)
- **Gyroscope** (measures the rate of rotation around the X, Y, and Z axes)
- **Magnetometer** (measures magnetic field strength)
- **Proximity Sensor** (detects the presence of nearby objects)
- **Ambient Light Sensor** (measures the light intensity)
- **Barometer** (measures atmospheric pressure)
- **Heart Rate Sensor** (measures the heart rate)
- **Ambient Temperature Sensor** (measures the surrounding temperature)
- **Humidity Sensor** (measures the ambient humidity)
- **Step Counter** (measures the number of steps taken)
- **Pedometer** (measures steps and calculates distance)
- **Shake Sensor** (detects shaking motion)

### Experimental and Alpha APIs

Composense includes experimental and alpha APIs for features beyond sensor data access. These features include interacting with hardware like IR Blasters, NFC, and biometric authentication. These APIs are marked as experimental and alpha and may change in future releases.

### Experimental API List:

- **SenseIRBlaster**: Transmit infrared signals.
- **SenseNFC**: Read NFC tags.
- **SenseTouchGestures**: Detect common touch gestures like taps and swipes.
- **FingerprintAuthenticator**: Authenticate using a fingerprint sensor.
- **FaceAuthenticator**: Authenticate using a face recognition sensor.

## Installation

To add Composense to your Android project, follow these steps:

### Step 1: Add the dependency to your `build.gradle` file

In your `app/build.gradle` file, under the `dependencies` block, add the following dependency:

```groovy
    implementation 'com.example:composense:x.y.z' // Replace with latest version
```
```kotlin
    implementation("com.example:composense:x.y.z")  // Replace with latest version
```
For **Fingerprint** and **Face Authentication** features, you must also add the `androidx.biometric` library. Add this dependency:

```groovy
dependencies {
    implementation 'androidx.biometric:biometric:1.2.0-alpha03'  // Required for biometric authentication - Replace with latest version
}
```

## API Usage

Composense provides a set of composables that allow you to easily access sensor data and update your UI reactively. Below are examples for each supported sensor.

### Example Usage

```kotlin

@Composable
fun SenseAccelerometer(
    sensorDelay: Int = SensorManager.SENSOR_DELAY_NORMAL,
    onSensorChanged: (event: SensorEvent) -> Unit = {},
    onAccuracyChanged: (sensor: Sensor, accuracy: Int) -> Unit = { _, _ -> },
    onSensorDataChanged: (x: Float, y: Float, z: Float) -> Unit = { _, _, _ -> }
)


@Composable
fun AccelerometerScreen() {
    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }
    var z by remember { mutableStateOf(0f) }

    SenseAccelerometer(
        onSensorDataChanged = { newX, newY, newZ ->
            x = newX
            y = newY
            z = newZ
        }
    )

    Column {
        Text("Accelerometer Data")
        Text("X: ${x}")
        Text("Y: ${y}")
        Text("Z: ${z}")
    }
}
```

## Contributing
We welcome contributions to Composense. To contribute, please follow these steps:

1. Fork the repository.
2. Create a feature branch (git checkout -b feature/YourFeature).
3. Commit your changes (git commit -am 'Add new feature').
4. Push to the branch (git push origin feature/YourFeature).
5. Create a new Pull Request.


## License
Composense is licensed under the Apache 2.0 License. See the [License](LICENSE) file for more details.

## Reporting Issues
If you encounter any issues or bugs, please [create a new issue](https://github.com/DeveloperSyndicate/Composense/issues) on GitHub. Provide a detailed description of the issue, steps to reproduce, and any relevant screenshots or code snippets.

