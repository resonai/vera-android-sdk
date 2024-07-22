package com.resonai.vera.demo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.resonai.vera.demo.databinding.ActivityLoginBinding
import com.resonai.vera.demo.ext.showToast
import com.resonai.vera.demo.ui.ContainerFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

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

    private fun openVeraScreen() {
        binding.btnStart.isVisible = false
        binding.container.isVisible = true
        val fragment = ContainerFragment()
        supportFragmentManager.beginTransaction()
            .replace(
                binding.container.id,
                fragment,
                ContainerFragment::class.java.getSimpleName()
            )
            .addToBackStack(ContainerFragment::class.java.getSimpleName())
            .commit()

    }

    override fun onBackPressed() {
        binding.btnStart.isVisible = true
        binding.container.isVisible = false
        if (supportFragmentManager.backStackEntryCount >= 1) {
            supportFragmentManager.popBackStack(
                ContainerFragment::class.java.getSimpleName(),
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } else super.onBackPressed()
    }

    companion object {
        private const val MY_REQUEST_CODE = 100
    }
}
