# Vera Android SDK 

Official repo for Vera Android SDK.

## Installation
- latest_version = 0.0.44
- minSdkVersion = 28

### Gradle

To integrate VeraSDK into your android project using gradle: 
on your root build.gradle:

```java
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add the dependency to your module
```java
    implementation 'com.github.resonai:vera-android-sdk:0.0.44'
```

Then, sync project:

## Usage 
  Create a configuration object and start sdk for both case

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

You can start Vera with user data :

```java

     builder.startWithLogin(useName(), userId, token);
```

You can also start Vera without user data, i.e. anonymously:

```java

     builder.startWithoutLogin();
```
When you need to send Vera data after Vera already running you can use 'setDeeplinkComponent':
for example:

```java
    builder.setDeeplinkComponent("https://vera.resonai.com/#/play/siteid/com.resonai.navigation/poseId")
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