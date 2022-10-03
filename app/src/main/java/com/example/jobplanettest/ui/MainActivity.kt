package com.example.jobplanettest.ui

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.jobplanettest.R
import com.example.jobplanettest.adapter.ChipAdapter
import com.example.jobplanettest.adapter.EnterPriseAdapter
import com.example.jobplanettest.adapter.RecruitAdapter
import com.example.jobplanettest.databinding.ActivityMainBinding
import com.example.jobplanettest.model.RecruitItem
import com.example.jobplanettest.model.enums.ChipTypeEnum
import com.example.jobplanettest.ui.base.BaseActivity
import com.example.jobplanettest.util.UiState
import com.example.jobplanettest.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainViewModel: MainViewModel by viewModels()
    private val recruitAdapter: RecruitAdapter by lazy { RecruitAdapter() }
    private val enterPriseAdapter: EnterPriseAdapter by lazy { EnterPriseAdapter() }

    private val config = ConcatAdapter.Config.Builder()
        .setIsolateViewTypes(false)
        .setStableIdMode(ConcatAdapter.Config.StableIdMode.NO_STABLE_IDS)
        .build()

    private val chipAdapter: ChipAdapter by lazy {
        ChipAdapter(
            callback = { chipTypeEnum ->
                mainViewModel.changeChipType(chipTypeEnum)
            }
        )
    }
    private val concatAdapter: ConcatAdapter by lazy {
        ConcatAdapter(config, chipAdapter, recruitAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
        setOnObserver()
        setOnViewWatcher()
        mainViewModel.getRecruitList()
        mainViewModel.getEnterPriseList()
    }

    private fun setOnObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mainViewModel.recruitEventFlow.collect { event ->
                        when (event) {
                            is UiState.Success -> {
                                mainViewModel.updateRecruit(event.data)
                            }

                            is UiState.Loading -> {
                            }

                            is UiState.Error -> {
                            }
                        }
                    }
                }

                launch {
                    mainViewModel.enterPriseEventFlow.collect { event ->
                        when (event) {
                            is UiState.Success -> {
                                mainViewModel.updateEnterPrise(event.data)
                            }

                            is UiState.Loading -> {
                            }

                            is UiState.Error -> {
                            }
                        }
                    }
                }

                launch {
                    mainViewModel.recruitFilterStateFlow.collect { value: List<RecruitItem> ->
                        recruitAdapter.submitRecruitList(value)
                    }
                }

                launch {
                    mainViewModel.enterPriseFilterStateFlow.collect { value ->
                        enterPriseAdapter.submitRecruitList(value)
                    }
                }
                launch {
                    mainViewModel.chipTypeStateFlow.collect { value ->
                        when (value) {
                            ChipTypeEnum.ENTERPRISE -> {
                                concatAdapter.removeAdapter(recruitAdapter)
                                concatAdapter.addAdapter(enterPriseAdapter)
                            }
                            ChipTypeEnum.RECRUIT -> {
                                concatAdapter.removeAdapter(enterPriseAdapter)
                                concatAdapter.addAdapter(recruitAdapter)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initAdapter() {
        GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (concatAdapter.getItemViewType(position)) {
                        R.layout.item_recruit -> 1
                        else -> 2
                    }
                }
            }
        }.let { gridLayoutManager ->
            binding.rvMain.adapter = concatAdapter
            binding.rvMain.layoutManager = gridLayoutManager
        }
        // 칩 동기화
    }

    private fun setOnViewWatcher() {
        binding.svTitle.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    when (mainViewModel.chipTypeStateFlow.value) {
                        ChipTypeEnum.RECRUIT -> mainViewModel.searchRecruit(newText.orEmpty())
                        ChipTypeEnum.ENTERPRISE -> mainViewModel.searchEnterPrise(newText.orEmpty())
                    }
                    return false
                }
            }
        )
    }
}
