<p align="center">
    <img alt="Vera: A computer vision enterprise platform that transforms buildings into intelligent environments" src="https://github.com/resonai/vera-ios-sdk/raw/readme/Vera.png">
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

### Gradle configuration
To integrate VeraSDK into your android project using gradle:
on your root build.gradle:

```java
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add the dependency to your module
```java
    implementation 'com.github.resonai:vera-android-sdk:$latest_version'
```

## Usage
1. Create a configuration object

```java
VeraConfiguration.Builder veraBuilder = new VeraConfiguration.Builder
        // (this) Activity used
        (getSupportFragmentManager(), R.id.container) // pass container for fragment
        .setClientAppID("vera_client_app")) // Your custom Vera platform ID.
        .setSiteIDs("siteId1", "siteId2") // Vera site ID's.
        .setShowCloseButton(shouldShow) // A boolean value of whether the SDK should show its own close button. Default to 'true'
        .setHideHeader(false) // A boolean value for the SDK to hide or show the default header. Defaults to `false`
        .setDeeplinkPrefix(veraDomain) //A string value defining your custom "deepLinkPrefix". Used when generating deep links that you can pass into Vera and should open your app.
        .setLanguage(sdkLanguage) // Choose Vera language. Defaults to `en` (English)
        .onRequestRefreshToken(this::fetchUserToken) // Listener triggered when user token need refresh
        .onMessageListener(this::onMessage) // Listener to messages from Vera and implement logic for them
        .onCloseListener(this::closeVeraSdk) // Listener triggered when Vera closed
```

2. Start sdk
   2.1. Start Vera with user data :
```java
     veraBuilder.startWithLogin(userName(), userId, token);
```

2.2. Start Vera without user data, i.e. anonymously:
```java
     veraBuilder.startWithoutLogin();
```
## Bi-directional Communication
The SDK implements bi-directional communication between the Vera platform and the client application.

#### Deeplinks
The SDK supports deep link-ing to some AR Experiences.
When you need to send Vera data after Vera already running you can use 'setDeeplinkComponent':
for example:

```java
    veraBuilder.setDeeplinkComponent("https://vera.resonai.com/#/play/siteid/com.resonai.navigation/poseId")
```

## Manifest:

#Permissions:

```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"
        tools:ignore="ScopedStorage" />
```

* "App needs access to the camera in order to render AR."
* "App needs location access to provide accurate AR experiences."
* "App needs storage access to save cache"