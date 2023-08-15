# Bi-directional Communication

The SDK implements bi-directional communication between the Vera platform and the client
application.

### Deeplinks

The SDK supports deep link-ing to some AR Experiences.
When you need to send Vera data after Vera already running you can use 'setDeeplinkComponent':
for example:

```kotlin
veraBuilder.setDeeplinkComponent("https://vera.resonai.com/#/play/siteid/com.resonai.navigation/poseId")
```

For an example
check [this implementation](https://github.com/resonai/vera-android-sdk/blob/42c4d47b99ebb3eeb7cf1c9d8be7cffb31b91e45/app/src/main/java/com/app/vera/demo/LoginActivity.kt#L90)
.

### Receiving Events

> [!WARNING]
> Only handle incoming events if you have a specific use-case or have been instructed to do so.

In the same manner, the SDK will send you events to ask you for additional information or deliver a
message from an ARX. You should be prepared to handle them.

#### Request refreshed token

Get event when Vera request to new refreshed token.

```kotlin
veraBuilder.onRequestRefreshToken(object : VeraEvents.VeraRequestTokenListener {
    override fun requestRefreshToken(callbackRequestToken: CallbackRequestToken?) {
        showToast("Vera request refreshed token")
        callbackRequestToken.userData(UserData("userId", "token"))
    }
})
```

#### OnClose Listener

Get event when Vera was closed.

```kotlin
veraBuilder.onCloseListener(object : VeraEvents.VeraCloseListener {
    override fun closeVera() {
        showToast("Vera was closed")
    }
})
```

## Communicating to ARXs

Since Vera is the platform where multiple Native apps can use different ARXs on the same site, we
implemented a generic way to communicate between these apps through the SDK.

### Sending Messages

In order to send custom events to ARXs use the `setMessage` event. You can then handle this event
in your own ARX app:

```kotlin
veraBuilder.setMessage(msgReceiver = "custom_arx_name", msgData = "custom_data")
```

> [!NOTE]
> The `setDeeplinkComponent` method is mostly a shortcut for communicating with the Navigation ARX.

### Receiving Messages

The same way you send events to ARXs, you can receive events from ARXs.

```kotlin
 veraBuilder.onMessageListener(object : VeraEvents.VeraOnMessageListener {
    override fun onMessage(sender: String?, data: String?) {
        showToast("Vera is sending event message back, sender:$sender, msg:$data")
    }
})
```