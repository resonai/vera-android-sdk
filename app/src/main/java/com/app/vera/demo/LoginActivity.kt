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

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var veraBuilder: VeraConfiguration.Builder? = null
    private var permissions =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() = with(binding) {
        btnStart.setOnClickListener {
            checkPermissions()
        }
    }

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

    private fun openVeraScreen() {
        binding.btnStart.isVisible = false
        binding.container.isVisible = true
        veraBuilder = VeraConfiguration.Builder(supportFragmentManager, binding.container)
            .setClientAppID("vera_client_app")
            .setLanguage(Languages.EN)
            .setHideHeader(true)
            .setSiteIDs(listOf("hataasiya-9-2"))
            .onMessageListener(object : VeraEvents.VeraOnMessageListener {
                override fun onMessage(sender: String?, data: String?) {
                    showToast("Vera is sending message, sender:$sender, msg:$data")
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

        setVeraDeeplink()
        veraBuilder?.startWithoutLogin()
    }

    private fun setVeraDeeplink() {
        val deeplinkPath =
            "https://vera.resonai.com/#/play/azrieli-hashalom-tlv/com.resonai.navigation/%7B%22id%22%3A%22660%22%7D"
        val isDeeplinkEnabled = false
        if (isDeeplinkEnabled) {
            veraBuilder?.setDeeplinkComponent(deeplinkPath)
        }
    }

    companion object {
        private const val MY_REQUEST_CODE = 100
    }
}