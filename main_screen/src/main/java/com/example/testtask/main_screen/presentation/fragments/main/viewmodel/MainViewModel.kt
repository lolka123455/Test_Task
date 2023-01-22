package com.example.testtask.main_screen.presentation.fragments.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.testtask.main_screen.domain.entities.main_page.BestSeller
import com.example.testtask.main_screen.domain.entities.main_page.HotSale
import com.example.testtask.main_screen.domain.usecases.GetCartUseCase
import com.example.testtask.main_screen.domain.usecases.GetMainPageUseCase
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.items.*
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.DelegateAdapterItem
import com.example.testtask.state_network_connection.UiState
import com.example.testtask.main_screen.presentation.adapters.delegate_adapter.adapters.CategoryItemTag
import com.example.testtask.state_network_connection.FetchResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(
    private val getMainPageUseCase: GetMainPageUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val dispatcherIo: CoroutineDispatcher = Dispatchers.IO,
    private val dispatcherDefault: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()

    private lateinit var mainPageUiItemsFlow: Flow<List<DelegateAdapterItem>>

    private val _cartSize = MutableLiveData<Int>()
    private val _selectedCategoryTag = MutableLiveData<CategoryItemTag>(CategoryItemTag.PHONE)
    private val _brands = MutableLiveData<List<String>>()
    private val _prices = MutableLiveData<List<String>>(listOf("$0 - $10000"))
    private val _sizes = MutableLiveData<List<String>>(listOf("4.5 to 5.5 inches"))

    val uiState: LiveData<UiState> = _uiState

    val mainPageUiItems: LiveData<List<DelegateAdapterItem>>
        get() = mainPageUiItemsFlow.asLiveData()

    val cartSize: LiveData<Int> = _cartSize
    val selectedCategoryTag: LiveData<CategoryItemTag> = _selectedCategoryTag
    val brands: LiveData<List<String>> = _brands
    val prices: LiveData<List<String>> = _prices
    val sizes: LiveData<List<String>> = _sizes

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
                delay(500L)
            }
        }
    }

    private fun getMainPage() {
        viewModelScope.launch(dispatcherIo) {
            val mainPageCallResult = getMainPageUseCase.execute()
            if (mainPageCallResult is FetchResult.Success) {
                hotSale = mainPageCallResult.data.hotSale
                bestSeller = mainPageCallResult.data.bestSeller
                getFilterOptions()
                _uiState.postValue(UiState.Success)
            } else {
                _uiState.postValue(UiState.Error)
            }
        }
    }

    private fun getFilterOptions() {
        viewModelScope.launch(dispatcherDefault) {
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

    private fun getCart() {
        viewModelScope.launch(dispatcherIo) {
            val cartCallResult = getCartUseCase.execute()
            if (cartCallResult is FetchResult.Success) {
                _cartSize.postValue(cartCallResult.data.itemsCount)
            } else {
                Log.e("cart", "error case")
            }
        }
    }

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