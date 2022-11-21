package com.example.testtask.main_screen.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testtask.main_screen.adapters.delegateAdapter.DelegateAdapterItem
import com.example.testtask.main_screen.adapters.selectCategory.CategoryItemTag
import com.example.testtask.main_screen.network.models.BestSeller
import com.example.testtask.main_screen.network.models.HotSale
import com.example.testtask.main_screen.usecases.GetMainDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val getMainDataUseCase: GetMainDataUseCase
) : ViewModel() {

    private lateinit var mainPageUiItemsFlow: StateFlow<List<DelegateAdapterItem>>


    private val _cartSize = MutableLiveData<Int>()
    private val _selectedCategoryTag = MutableLiveData(CategoryItemTag.PHONE)
    private val _brands = MutableLiveData<List<String>>()
    private val _prices = MutableLiveData(listOf("$0 - $10000"))
    private val _sizes = MutableLiveData(listOf("4.5 to 5.5 inches"))

    val mainPageUiItems: StateFlow<List<DelegateAdapterItem>>
        get() = mainPageUiItemsFlow

    private var bestSeller: List<BestSeller> = emptyList()
    private var hotSale: List<HotSale> = emptyList()
    private var selectedBrand = ""
    private var previouslySelectedBrand = ""

    init {
        getMainPage()
    }

    private fun getMainPage() {
        viewModelScope.launch(Dispatchers.IO) {
            val mainPageCallResult = getMainDataUseCase.invoke()
            hotSale = mainPageCallResult.hotSale
            bestSeller = mainPageCallResult.bestSeller
            getFilterOptions()
        }
    }

    private fun getFilterOptions() {
        viewModelScope.launch(Dispatchers.IO) {
            val bestSellerBrands = bestSeller.map {
                it.extractBrand()
            }
            val homeStoreBrands = hotSale.map {
                it.extractBrand()
            }
            val brands = (bestSellerBrands + homeStoreBrands).distinct()
            _brands.postValue(brands)
            selectedBrand = brands[0]
        }
    }

    private fun HotSale.extractBrand() =
        title.split(" ").first()

    private fun BestSeller.extractBrand() =
        title.split(" ").first()

    fun changeCategory(tag: CategoryItemTag) {
        _selectedCategoryTag.postValue(tag)
    }

    fun setSelectedBrand(brand: String) {
        selectedBrand = brand
        _brands.value?.let { brands ->
            val selectedBrandIndex = brands.indexOf(selectedBrand)
            Collections.swap(brands, selectedBrandIndex, 0)
            _brands.postValue(brands)
        }
    }

    fun saveCurrentSelectedItems() {
        previouslySelectedBrand = selectedBrand
    }

    fun restorePreviousSelections() {
        selectedBrand = previouslySelectedBrand
        setSelectedBrand(selectedBrand)
    }

}