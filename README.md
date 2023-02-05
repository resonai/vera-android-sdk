# Vera Android SDK 

Official repo for Vera Android SDK.

## Installation
- latest_version = 0.0.37
- minSdkVersion = 28

### Gradle

To integrate VeraSDK into your android project using gradle: 
on your root build.gradle:

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add the dependency to your module
```groovy
    implementation 'com.github.resonai:vera-android-sdk:0.0.37'
```

Then, sync project:

## Usage 
  Create a configuration object and start sdk for both case

```groovy
VeraConfiguration.Builder veraBuilder = new VeraConfiguration.Builder
        // (this) Activity used
        (getSupportFragmentManager(), R.id.container) // pass container for fragment
        .setClientAppID("vera_client_app")) // Your custom Vera platform ID.
        .setSiteIDs("siteId1", "siteId2");
        .setShowCloseButton(shouldShow) // A boolean value of whether the SDK should show it's own close button - default to true
        .setHideHeader(false) // A boolean value for the SDK to hide or show the default header. Defaults to `false`
        .setDeeplinkPrefix(veraDomain) //A string value defining your custom "deepLinkPrefix". Used when generating deep links that you can pass into Vera and should open your app.
        .setLanguage(sdkLanguage) // Choose vera language
        .onRequestRefreshToken(this::fetchUserToken) // Listener triggered when user token need refresh
        .onMessageListener(this::onMessage) // Listener to messages from vera and implement logic for them
        .onCloseListener(this::closeVeraSdk) // Listener triggered when vera closed
```
You can Start vera with user data :

```groovy

     builder.startWithLogin(useName(), userId, token);
```

You can also start vera without user data :

```groovy

     builder.startWithoutLogin();
```

```groovy

    when you need to send vera data after vera already exist you can use setDeeplinkComponent:
    for example:
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