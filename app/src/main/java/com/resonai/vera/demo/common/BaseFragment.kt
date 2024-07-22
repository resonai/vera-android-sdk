package com.resonai.vera.demo.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.resonai.vera.demo.Action
import com.resonai.vera.demo.VeraViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

// This is base fragment, Fragments from `demo app` extends it
// to have common method and state, because we use in all fragments
// same methods.
open class BaseFragment : Fragment() {

    private var actionJob: Job? = null
    val viewModel: VeraViewModel by activityViewModels()

    fun subscribeObservables(onSendMessage: (String, String, Boolean) -> Unit) {
        actionJob = lifecycleScope.launch {
            viewModel.action.collect { action ->
                when (action) {
                    is Action.SendMessage -> onSendMessage.invoke(
                        action.msg,
                        action.siteId,
                        action.shouldRestart
                    )
                }
            }
        }
    }

    private fun onDestroyJob() {
        actionJob?.cancel()
        actionJob = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        onDestroyJob()
    }
}
