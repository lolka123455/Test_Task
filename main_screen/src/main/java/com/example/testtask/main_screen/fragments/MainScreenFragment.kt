package com.example.testtask.main_screen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.state_network_connection.R
import com.example.testtask.main_screen.databinding.FragmentMainBinding
import com.example.testtask.main_screen.entities.main_page.BestSeller
import com.example.testtask.main_screen.entities.main_page.HotSale
import com.example.testtask.main_screen.adapters.composite.CompositeAdapter
import com.example.testtask.main_screen.adapters.bestSeller.BestSellerDelegateAdapter
import com.example.testtask.main_screen.adapters.categories.CategoriesDelegateAdapter
import com.example.testtask.main_screen.adapters.categories.CategoryItemTag
import com.example.testtask.main_screen.adapters.hotSales.HotSalesDelegateAdapter
import com.example.testtask.main_screen.adapters.search.SearchDelegateAdapter
import com.example.testtask.main_screen.adapters.section.SectionDelegateAdapter
import com.example.testtask.main_screen.databinding.BottomSheetDialogFilterBinding
import com.example.testtask.main_screen.filterCategory.FilterCategory
import com.example.testtask.main_screen.viewmodel.MainViewModel
import com.example.testtask.navigation.AppScreens
import com.example.testtask.state_network_connection.UiState
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModel<MainViewModel>()

    private val categoryItemClickListener: (CategoryItemTag) -> Unit = { itemTag ->
        viewModel.changeCategory(itemTag)
    }

    private val bestSellerItemClickListener: (BestSeller) -> Unit = {
        openItemDetails()
    }

    private val hotSalesItemClickListener: (HotSale) -> Unit = {
        openItemDetails()
    }

    private val categoriesDelegateAdapter = CategoriesDelegateAdapter(categoryItemClickListener)

    private val compositeAdapter: CompositeAdapter by lazy {
        val builder = CompositeAdapter.Builder()
        builder.add(SectionDelegateAdapter())
        builder.add(categoriesDelegateAdapter)
        builder.add(SearchDelegateAdapter())
        builder.add(HotSalesDelegateAdapter(hotSalesItemClickListener))
        builder.add(BestSellerDelegateAdapter(bestSellerItemClickListener))
        builder.build()
    }

    private var brandsFilterItems = emptyList<String>()
    private var pricesFilterItems = emptyList<String>()
    private var sizesFilterItems = emptyList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        setupRecyclerView()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            filterImageView.setOnClickListener { showFilter() }
            bottomCartImageView.setOnClickListener { openCart() }
        }
    }

    private fun openCart() {
        findNavController().navigate(AppScreens.CartScreen)
    }

    private fun openItemDetails() {
        findNavController().navigate(AppScreens.DetailsScreen)
    }

    private fun showFilter() {
        saveCurrentSelectedItems()
        showFilterBottomSheetDialog()
    }

    private fun saveCurrentSelectedItems() {
        viewModel.saveCurrentSelectedItems()
    }

    private fun showFilterBottomSheetDialog() {
        val filterDialog = createFilterBottomSheetDialog()
        filterDialog.show()
    }

    private fun createFilterBottomSheetDialog(): BottomSheetDialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val binding = BottomSheetDialogFilterBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)
        setupSpinners(binding)
        setupDoneButton(binding, bottomSheetDialog)
        setupCloseButton(binding, bottomSheetDialog)
        return bottomSheetDialog
    }

    private fun setupSpinners(binding: BottomSheetDialogFilterBinding) {
        binding.brandsSpinner.setFilterSpinner(FilterCategory.BRAND)
        binding.priceSpinner.setFilterSpinner(FilterCategory.PRICE)
        binding.sizeSpinner.setFilterSpinner(FilterCategory.SIZE)
    }

    private fun setupDoneButton(binding: BottomSheetDialogFilterBinding,
                                bottomSheetDialog: BottomSheetDialog) {
        binding.doneTextView.setOnClickListener { bottomSheetDialog.dismiss() }
    }

    private fun setupCloseButton(binding: BottomSheetDialogFilterBinding,
                                 bottomSheetDialog: BottomSheetDialog) {
        binding.closeImageView.setOnClickListener {
            undoFilterChanges()
            bottomSheetDialog.dismiss()
        }
    }

    private fun undoFilterChanges() {
        viewModel.restorePreviousSelections()
    }

    private fun Spinner.setFilterSpinner(category: FilterCategory) {
        val items = getFilterItems(category)
        adapter = createSpinnerAdapter(items)
        tag = category
        onItemSelectedListener = createFilterSpinnerOnItemSelectedListener()
    }

    private fun getFilterItems(category: FilterCategory): List<String> {
        return when (category) {
            FilterCategory.BRAND -> brandsFilterItems
            FilterCategory.PRICE -> pricesFilterItems
            FilterCategory.SIZE -> sizesFilterItems
        }
    }

    private fun createFilterSpinnerOnItemSelectedListener() =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                v: View?,
                position: Int,
                id: Long
            ) {
                when (adapterView?.tag) {
                    FilterCategory.BRAND -> brandSelected(brandsFilterItems[position])
                    FilterCategory.PRICE -> {}
                    FilterCategory.SIZE -> {}
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    private fun brandSelected(brand: String) {
        viewModel.setSelectedBrand(brand)
    }

    private fun createSpinnerAdapter(items: List<String>): ArrayAdapter<String> {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return adapter
    }

    private fun setupRecyclerView() {
        binding.mainRecyclerView.apply {
            adapter = compositeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }
    }

    private fun observe() {
        observeUIState()
        observeMainPageUiItems()
        observeCartSize()
        observeSelectedCategoryTag()
        observeBrands()
        observePrices()
        observeSizes()
    }

    private fun observeUIState(){
        viewModel.uiState
            .onEach { updateUiState(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeMainPageUiItems(){
        viewModel.mainPageUiItems
            .onEach { compositeAdapter.submitList(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeCartSize(){
        viewModel.cartSize
            .onEach { setCartSize(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeSelectedCategoryTag(){
        viewModel.selectedCategoryTag
            .onEach { categoriesDelegateAdapter.setSelectedItem(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeBrands(){
        viewModel.brands
            .onEach { brandsFilterItems = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observePrices(){
        viewModel.prices
            .onEach { pricesFilterItems = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeSizes(){
        viewModel.sizes
            .onEach { sizesFilterItems = it }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setCartSize(size: Int) {
        binding.bottomCartCountTextView.apply {
            text = size.toString()
            visibility = if (size > 0) View.VISIBLE else View.GONE
        }
    }

    private fun updateUiState(state: UiState) {
        when (state) {
            is UiState.Success -> with(binding) {
                successStateUi.visibility = View.VISIBLE
                errorStateUi.errorStateUi.visibility = View.GONE
            }
            is UiState.Error -> with(binding) {
                successStateUi.visibility = View.GONE
                errorStateUi.errorStateUi.visibility = View.VISIBLE
                setTryAgainButtonClickListener()
            }
            is UiState.Loading -> with(binding) {
                successStateUi.visibility = View.VISIBLE
            }
        }
    }

    private fun setTryAgainButtonClickListener() {
        val tryAgainTextView = view?.findViewById<FrameLayout>(R.id.tryAgainFrame)
        tryAgainTextView?.setOnClickListener {
            viewModel.retryNetworkCall()
        }
    }
}