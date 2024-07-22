package com.resonai.vera.demo.ui.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.resonai.vera.demo.common.BaseFragment
import com.resonai.vera.demo.databinding.FragmentFeaturesBinding

class FeaturesFragment : BaseFragment() {

    private var binding: FragmentFeaturesBinding? = null
    private val adapter by lazy { DeeplinkCategoryAdapter() }
    private var deepLinkUrl: String = ""
    private var siteId: String = ""

    private val deeplinks: List<DeeplinkCategory> by lazy {
        val azrieliDeeplinks = listOf(
            Deeplink(
                "Info center (key)",
                "https://vera.resonai.com/#/play/azrieli-hashalom-tlv/com.resonai.navigation/%7B%22key%22%3A%225c82a554-27a2-11ed-a7e4-2678fb2dc7e4%22%7D",
                "azrieli-hashalom-tlv"
            ),
            Deeplink(
                "Toilet (ID)",
                "https://vera.resonai.com/#/play/azrieli-hashalom-tlv/com.resonai.navigation/%7B%22id%22%3A%22T3%22%7D",
                "azrieli-hashalom-tlv"
            ),
            Deeplink(
                "Coordinate (G center)",
                "https://vera.resonai.com/#/play/azrieli-hashalom-tlv/com.resonai.navigation/%7B%22coord%22%3A%7B%22x%22%3A1006.2586843848667%2C%22y%22%3A93.7%2C%22z%22%3A-1001.68445442079%7D%7D",
                "azrieli-hashalom-tlv"
            )
        )

        val hataasiaDeeplinks = listOf(
            Deeplink(
                "Lounge (key)",
                "https://vera.resonai.com/#/play/hataasia-9-3/com.resonai.navigation/%7B%22key%22%3A%2257983ba6-1abe-11ef-bf41-825059af468f%22%7D",
                "hataasia-9-3"
            ),
            Deeplink(
                "Eyal's desk (ID)",
                "https://vera.resonai.com/#/play/hataasia-9-3/com.resonai.navigation/%7B%22id%22%3A%22eyals%22%7D",
                "hataasia-9-3"
            ),
            Deeplink(
                "Coordinate (4th floor loft)",
                "https://vera.resonai.com/#/play/hataasia-9-3/com.resonai.navigation/%7B%22coord%22%3A%7B%22x%22%3A-2.105348930358886%2C%22y%22%3A16.56725105878516%2C%22z%22%3A-12.233418884277345%7D%7D",
                "hataasia-9-3"
            )
        )

        listOf(
            DeeplinkCategory("Azrieli", azrieliDeeplinks),
            DeeplinkCategory("Hataasia", hataasiaDeeplinks)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeaturesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() = binding?.apply {
        recyclerView.adapter = adapter
        adapter.setOnCategoryClicked { category ->
            clearState()
            val tempDeeplinks = deeplinks.map {
                it.copy(isSelected = it.category == category.category)
            }

            adapter.submitList(tempDeeplinks)
        }

        adapter.setOnDeeplinkClicked {
            val tempDeeplinks = deeplinks.map { category ->
                val tempCategory = category.copy(
                    deeplinks = category.deeplinks.map { deeplink ->
                        deeplink.copy(isSelected = deeplink.name == it.name)
                    }
                )

                tempCategory.copy(isSelected = tempCategory.deeplinks.any { it.isSelected })
            }

            adapter.submitList(tempDeeplinks)
            deepLinkUrl = it.url
            siteId = it.siteId
            buttonDeeplinkAndRestart.isVisible = true
         //   buttonDeeplinkOnly.isVisible = true
        }

        adapter.submitList(deeplinks)

        buttonDeeplinkAndRestart.setOnClickListener {
            viewModel.sendMessage(deepLinkUrl, siteId,true)
            clearState()
            adapter.submitList(deeplinks)
        }

        buttonDeeplinkOnly.setOnClickListener {
            viewModel.sendMessage(deepLinkUrl,siteId)
            clearState()
            adapter.submitList(deeplinks)
        }
    }

    private fun clearState() = binding?.apply {
        deepLinkUrl = ""
        buttonDeeplinkAndRestart.isVisible = false
        buttonDeeplinkOnly.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}

data class Deeplink(
    val name: String,
    val url: String,
    val siteId: String,
    val isSelected: Boolean = false
)

data class DeeplinkCategory(
    val category: String,
    val deeplinks: List<Deeplink>,
    val isSelected: Boolean = false
)
