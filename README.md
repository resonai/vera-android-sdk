<p align="center">
    <img alt="Vera: A computer vision enterprise platform that transforms buildings into intelligent environments" src="./Vera.png">
</p>
<p align="center">
A computer vision enterprise platform that transforms buildings into intelligent environments.
</p>

<p align="center">
    <a href="https://kotlinlang.org/"><img alt="Kotlin 1.8.10" src="https://img.shields.io/badge/kotlin-1.8.10-8A2BE2.svg?style=flat"></a>
    <a href="https://github.com/resonai/vera-android-sdk/releases"><img alt="Vera Release" src="https://img.shields.io/github/v/release/resonai/vera-android-sdk"></a>
</p>

## Installation

- latest_version = 0.1.22
- minSdkVersion = 28
- arCore = true (check the [ARCore supported devices](https://developers.google.com/ar/devices))

## Integration
VeraSDK provides access to the Vera platform to any Native application. When the user finds himself in any of the Vera supported **sites** (buildings), he can open Vera and it will localize him inside the building with a very accurate precision. When he's localized, he can access any AR Experiences (**ARXs**) set up for that specific site.

Some examples of ARXs include Navigation, 3D objects & animations, Interactions with the environment, etc.

### Gradle configuration

To integrate VeraSDK into your android project using gradle:
on your root build.gradle:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add the dependency to your module

```groovy
implementation 'com.github.resonai:vera-android-sdk:$latest_version'
```

## Usage
You can check [example integration](https://github.com/resonai/vera-android-sdk/blob/97a715517ca69fb06adc7cfd90c90169d7bcaeca/app/src/main/java/com/app/vera/demo/LoginActivity.kt#L65).

1. Create a configuration object, either as a new Activity or in a Fragment

- Show it like a new Activity

```kotlin
val veraBuilder = VeraConfiguration.Builder(requireActivity())
```

- Show it in fragment

```kotlin
val veraBuilder = VeraConfiguration.Builder(
    fragmentManager = getSupportFragmentManager(),
    container = R.id.container
)
```

- Set your custom Vera platform ID.

```kotlin
veraBuilder.setClientAppID("vera_client_app")
```

- Set Vera site ID's.

```kotlin
veraBuilder.setSiteIDs("siteId1", "siteId2")
```

- (Optional) Set a boolean value of whether the SDK should show its own close button. Default to '
  true'

```kotlin
veraBuilder.setShowCloseButton(true)
```

- (Optional) Set a boolean value for the SDK to hide or show the default header. Defaults
  to `false`

```kotlin
veraBuilder.setHideHeader(false)
```

- (Optional) Set a string value defining your custom "deepLinkPrefix". Used when generating deep
  links that you can pass into Vera and should open your app.

```kotlin
veraBuilder.setDeeplinkPrefix(veraDomain)
```

- (Optional) Choose Vera language. Defaults to `en` (English)

```kotlin
veraBuilder.setLanguage(Languages.EN)
```

2. Start sdk, either login with user data or without login

- Start Vera with user data:

```kotlin
veraBuilder.startWithLogin(userName(), userId, token)
```

- Start Vera without user data, i.e. anonymously:

```kotlin
veraBuilder.startWithoutLogin()
```

3. Please refer to the [testing docs](./docs/testing.md) to learn how to test if the integration was successful.

## Bi-directional Communication

Check the [bi-directional communication docs](./docs/bidirectional-communication.md) to learn how to
send and receive events from the SDK.

## Manifest

#### Permissions:

```xml

<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.VIBRATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" tools:ignore="ScopedStorage" />
```

* "App needs access to the camera in order to render AR."
* "App needs location access to provide accurate AR experiences."
* "App needs storage access to save cache"
