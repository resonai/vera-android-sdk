package com.app.vera.demo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.app.vera.databinding.ActivityLoginBinding
import com.resonai.common.helpers.CallbackRequestToken
import com.resonai.common.helpers.Languages
import com.resonai.irocket.VeraConfiguration
import com.resonai.irocket.VeraEvents

/*
* This demo app represents an example of how we can simply configure VeraConfiguration.Builder.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var veraBuilder: VeraConfiguration.Builder? = null
    private var permissions =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    /**
     * This system callback method called when the activity is starting.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    /**
     * This method bind all views
     */
    private fun bindView() = with(binding) {
        btnStart.setOnClickListener {
            checkPermissions()
        }
    }

    /**
     * This method check if all permission was granted, if not method asking to be give.
     */
    private fun checkPermissions() {
        var allPermissions = true
        for (per in permissions) {
            allPermissions =
                allPermissions && checkSelfPermission(per) == PackageManager.PERMISSION_GRANTED
        }

        if (!allPermissions) {
            requestPermissions(permissions, MY_REQUEST_CODE)
        } else {
            openVeraScreen()
        }
    }

    /**
     * This method represent a callback from #requestPermissions, when all permission was granted.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openVeraScreen()
                showToast("Camera permission granted")
            } else {
                showToast("Camera permission denied")
            }
        }
    }

    /**
     * This method represent configuration and opening Vera SDK.
     */
    private fun openVeraScreen() {
        binding.btnStart.isVisible = false
        binding.container.isVisible = true
        veraBuilder = VeraConfiguration.Builder(
            fragmentManager = supportFragmentManager,
            container = binding.container
        )
            .setClientAppID("vera_client_app")
            .setLanguage(Languages.EN)
            .setHideHeader(true)
            .setSiteIDs(listOf("sdk-sample-site"))
            .onMessageListener(object : VeraEvents.VeraOnMessageListener {
                override fun onMessage(sender: String?, data: String?) {
                    showToast("Vera is sending event message back, sender:$sender, msg:$data")
                }
            })
            .onRequestRefreshToken(object : VeraEvents.VeraRequestTokenListener {
                override fun requestRefreshToken(callbackRequestToken: CallbackRequestToken?) {
                    showToast("Vera request refreshed token")
                }
            })
            .onCloseListener(object : VeraEvents.VeraCloseListener {
                override fun closeVera() {
                    showToast("Vera was closed")
                }
            })

        setVeraDeeplink(isDeeplinkEnabled = false)
        setVeraMessage(isMessageEnabled = false)
        veraBuilder?.startWithoutLogin()
    }

    /**
     * This method represent a opening deeplink in Resonai place.
     * Put "true" in arguments to enable deeplink.
     */
    private fun setVeraDeeplink(isDeeplinkEnabled: Boolean) {
        if (isDeeplinkEnabled) {
            //Deeplink path of Resonai place
            val deeplinkPath =
                "https://vera.resonai.com/#/play/sdk-sample-site/com.resonai.navigation/%7B%22key%22%3A%228207e1fe-3c5a-11ee-9750-12f3c6ba63d8%22%7D"

            /**
             * @param deeplinkComponent an deeplink path
             * @param isOutSideDeeplink if deeplink will set in app, then pass `false`,
             * if it will came from background, for example clicking on link and catched in `onNewIntent`
             * then should be pass true.
             */
            veraBuilder?.setDeeplinkComponent(
                deeplinkComponent = deeplinkPath,
                isOutSideDeeplink = false
            )
        }
    }

    /**
     * This method represent sending custom ARXs events.
     * Put "true" in arguments to pass ARX messages.
     */
    private fun setVeraMessage(isMessageEnabled: Boolean) {
        if (isMessageEnabled) {
            //Pass name of ARX event
            val msgReceiver = "custom_arx_name"

            //Pass data which you want to set
            val msgData = "custom_data"
            veraBuilder?.setMessage(msgReceiver, msgData)
        }
    }

    companion object {
        private const val MY_REQUEST_CODE = 100
    }
}