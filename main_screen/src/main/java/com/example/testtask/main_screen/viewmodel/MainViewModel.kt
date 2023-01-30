package com.example.testtask.main_screen.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.testtask.main_screen.entities.main_page.BestSeller
import com.example.testtask.main_screen.entities.main_page.HotSale
import com.example.testtask.main_screen.usecases.GetCartUseCase
import com.example.testtask.main_screen.usecases.GetMainPageUseCase
import com.example.testtask.main_screen.adapters.delegate.DelegateAdapterItem
import com.example.testtask.state_network_connection.UiState
import com.example.testtask.main_screen.adapters.categories.CategoryItemTag
import com.example.testtask.main_screen.adapters.bestSeller.BestSellerDelegateItem
import com.example.testtask.main_screen.adapters.categories.CategoriesDelegateItem
import com.example.testtask.main_screen.adapters.hotSales.HotSalesDelegateItem
import com.example.testtask.main_screen.adapters.search.SearchDelegateItem
import com.example.testtask.main_screen.adapters.section.SectionDelegateItem
import com.example.testtask.state_network_connection.FetchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val getMainPageUseCase: GetMainPageUseCase,
    private val getCartUseCase: GetCartUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private lateinit var mainPageUiItemsFlow: Flow<List<DelegateAdapterItem>>
    val mainPageUiItems: Flow<List<DelegateAdapterItem>>
        get() = mainPageUiItemsFlow

    private val _cartSize = MutableStateFlow(0)
    val cartSize: StateFlow<Int> = _cartSize

    private val _selectedCategoryTag = MutableStateFlow(CategoryItemTag.PHONE)
    val selectedCategoryTag: StateFlow<CategoryItemTag> = _selectedCategoryTag

    private val _brands = MutableStateFlow(listOf("Samsung"))
    val brands: StateFlow<List<String>> = _brands

    private val _prices = MutableStateFlow(listOf("$0 - $10000"))
    val prices: StateFlow<List<String>> = _prices

    private val _sizes = MutableStateFlow(listOf("4.5 to 5.5 inches"))
    val sizes: StateFlow<List<String>> = _sizes

    private var bestSeller: List<BestSeller> = emptyList()
    private var hotSale: List<HotSale> = emptyList()
    private var selectedBrand = ""
    private var previouslySelectedBrand = ""

    init {
        getMainPage()
        getCart()
        setupMainPageUiItems()
    }

    fun retryNetworkCall() {
        getMainPage()
        getCart()
    }

    private fun setupMainPageUiItems() {
        val selectCategorySectionDelegateItem = SectionDelegateItem(
            "Select Category",
            "view all"
        )
        val hotSalesSectionDelegateItem = SectionDelegateItem(
            "Hot sales",
            "see more"
        )
        val bestSellerSectionDelegateItem = SectionDelegateItem(
            "Best Seller",
            "see more"
        )
        val categoriesDelegateItem = CategoriesDelegateItem()
        val searchDelegateItem = SearchDelegateItem()
        mainPageUiItemsFlow = flow {
            while (true) {
                val hotSalesDelegateItem = HotSalesDelegateItem(hotSale)
                val bestSellerDelegateItem = BestSellerDelegateItem(bestSeller)
                emit(
                    listOf(
                        selectCategorySectionDelegateItem,
                        categoriesDelegateItem,
                        searchDelegateItem,
                        hotSalesSectionDelegateItem,
                        hotSalesDelegateItem,
                        bestSellerSectionDelegateItem,
                        bestSellerDelegateItem
                    )
                )
                setIssuanceDelay(1)
            }
        }
    }

    private suspend fun setIssuanceDelay(delayTime: Long) {
        delay(delayTime)
    }

    private fun getMainPage() {
        viewModelScope.launch(Dispatchers.IO) {
            val mainPageCallResult = getMainPageUseCase.execute()
            if (mainPageCallResult is FetchResult.SuccessDataUpload) {
                hotSale = mainPageCallResult.data.hotSale
                bestSeller = mainPageCallResult.data.bestSeller
                getFilterOptions()
                _uiState.value = UiState.Success
            } else {
                _uiState.value = UiState.Error
            }
        }
    }

    private fun getFilterOptions() {
        viewModelScope.launch(Dispatchers.Default) {
            val bestSellerBrands = bestSeller.map {
                it.extractBrand()
            }
            val homeStoreBrands = hotSale.map {
                it.extractBrand()
            }
            val brands = (bestSellerBrands + homeStoreBrands).distinct()
            _brands.value = brands
            selectedBrand = brands[0]
        }
    }

    private fun HotSale.extractBrand() =
        title.split(" ").first()

    private fun BestSeller.extractBrand() =
        title.split(" ").first()

    private fun getCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val cartCallResult = getCartUseCase.execute()
            if (cartCallResult is FetchResult.SuccessDataUpload) {
                _cartSize.value = cartCallResult.data.itemsCount
            } else {
                Log.e("cart", "error case")
            }
        }
    }

    fun changeCategory(tag: CategoryItemTag) {
        _selectedCategoryTag.value = tag
    }

    fun setSelectedBrand(brand: String) {
        selectedBrand = brand
        _brands.value.let { brands ->
            val selectedBrandIndex = brands.indexOf(selectedBrand)
            Collections.swap(brands, selectedBrandIndex, 0)
            _brands.value = brands
        }
    }

    // saves selected items in case user cancel filter options after some changing
    fun saveCurrentSelectedItems() {
        previouslySelectedBrand = selectedBrand
    }

    // restores selected items with items saved before opening filter dialog
    fun restorePreviousSelections() {
        selectedBrand = previouslySelectedBrand
        setSelectedBrand(selectedBrand)
    }
}