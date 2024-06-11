package com.resonai.vera.demo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.resonai.common.helpers.CallbackRequestToken
import com.resonai.common.helpers.Languages
import com.resonai.irocket.VeraConfiguration
import com.resonai.irocket.VeraEvents
import com.resonai.vera.demo.common.BaseFragment
import com.resonai.vera.demo.databinding.FragmentVeraContainerBinding
import com.resonai.vera.demo.ext.showToast

class VeraContainerFragment : BaseFragment() {

    private companion object {
        const val CLIENT_APP_ID = "vera_client_app"
        const val SDK_SAMPLE_SITE = "sdk-sample-site"
    }

    private var binding: FragmentVeraContainerBinding? = null
    private var veraBuilder: VeraConfiguration.Builder? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVeraContainerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVera()

        subscribeObservables(onSendMessage = { deepLinkUrl, shouldRestart ->
            if (shouldRestart) {
                veraBuilder?.startWithoutLogin()
            }

            veraBuilder?.setDeeplinkComponent(deepLinkUrl)
        })
    }

    private fun setupVera() {
        veraBuilder = VeraConfiguration.Builder(
            fragmentManager = childFragmentManager,
            container = binding!!.fragmentLoginContainer
        )
            .setClientAppID(CLIENT_APP_ID)
            .setLanguage(Languages.EN)
            .setHideHeader(true)
            .setSiteIDs(listOf(SDK_SAMPLE_SITE))
            .onMessageListener(object : VeraEvents.VeraOnMessageListener {
                override fun onMessage(sender: String?, data: String?) {
                    context?.showToast("Vera is sending event message back, sender:$sender, msg:$data")
                }
            })
            .onRequestRefreshToken(object : VeraEvents.VeraRequestTokenListener {
                override fun requestRefreshToken(callbackRequestToken: CallbackRequestToken?) {
                    context?.showToast("Vera request refreshed token")
                }
            })
            .onCloseListener(object : VeraEvents.VeraCloseListener {
                override fun closeVera() {
                    context?.showToast("Vera was closed")
                }
            })

        veraBuilder?.startWithoutLogin()
    }

    /**
     * This method represent sending custom ARXs events.
     * Put "true" in arguments to pass ARX messages.
     */
    private fun setVeraMessage(isMessageEnabled: Boolean) {
        //Pass name of ARX event
        val msgReceiver = "custom_arx_name"

        //Pass data which you want to set
        val msgData = "custom_data"
        veraBuilder?.setMessage(msgReceiver, msgData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
