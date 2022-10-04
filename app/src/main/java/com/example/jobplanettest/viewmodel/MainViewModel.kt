package com.example.jobplanettest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobplanettest.model.CellItem
import com.example.jobplanettest.model.RecruitItem
import com.example.jobplanettest.model.enums.ChipTypeEnum
import com.example.jobplanettest.usecase.GetEnterPriseListBaseUseCase
import com.example.jobplanettest.usecase.GetRecruitListBaseUseCase
import com.example.jobplanettest.util.UiState
import com.example.studymatchingapp.util.flow.MutableEventFlow
import com.example.studymatchingapp.util.flow.asEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRecruitListUseCase: @JvmSuppressWildcards GetRecruitListBaseUseCase,
    private val getEnterPriseListUseCase: @JvmSuppressWildcards GetEnterPriseListBaseUseCase
) : ViewModel() {

    private val _recruitEventFlow = MutableEventFlow<UiState<List<RecruitItem>>>()
    val recruitEventFlow = _recruitEventFlow.asEventFlow()
    private var recruitItemList = listOf<RecruitItem>()

    private val _recruitFilterStateFlow = MutableStateFlow<List<RecruitItem>>(recruitItemList)
    val recruitFilterStateFlow = _recruitFilterStateFlow.asStateFlow()

    private val _enterPriseEventFlow = MutableEventFlow<UiState<List<CellItem>>>()
    val enterPriseEventFlow = _enterPriseEventFlow.asEventFlow()
    private var enterPriseItemList = listOf<CellItem>()

    private val _enterPriseFilterStateFlow = MutableStateFlow<List<CellItem>>(enterPriseItemList)
    val enterPriseFilterStateFlow = _enterPriseFilterStateFlow.asStateFlow()

    // filterType
    private val _chipTypeStateFlow = MutableStateFlow<ChipTypeEnum>(ChipTypeEnum.RECRUIT)
    val chipTypeStateFlow = _chipTypeStateFlow.asStateFlow()

    fun getRecruitList() {
        viewModelScope.launch {
            getRecruitListUseCase(Unit).collect { uiState ->
                _recruitEventFlow.emit(uiState)
            }
        }
    }

    fun getEnterPriseList() {
        viewModelScope.launch {
            getEnterPriseListUseCase(Unit).collect { uiState ->
                _enterPriseEventFlow.emit(uiState)
            }
        }
    }

    fun updateEnterPrise(list: List<CellItem>) {
        _enterPriseFilterStateFlow.value = list
        enterPriseItemList = list
    }

    fun searchEnterPrise(searchText: String) {
        _enterPriseFilterStateFlow.update {
            Timber.tag("recruitItemList")
                .d(enterPriseItemList.filter { it.name?.contains(searchText) ?: run { false } }.toString())
            enterPriseItemList.filter { it.name?.contains(searchText) ?: run { false } }
        }
    }

    fun searchRecruit(searchText: String) {
        _recruitFilterStateFlow.update {
            Timber.tag("recruitItemList").d(recruitItemList.filter { it.title.contains(searchText) }.toString())
            recruitItemList.filter { it.title.contains(searchText) }
        }
    }

    fun updateRecruit(list: List<RecruitItem>) {
        _recruitFilterStateFlow.value = list
        recruitItemList = list
    }

    fun changeChipType(chipTypeEnum: ChipTypeEnum) {
        _chipTypeStateFlow.update { chipTypeEnum }
    }
}
