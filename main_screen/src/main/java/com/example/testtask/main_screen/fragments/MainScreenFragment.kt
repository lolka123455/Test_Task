package com.example.testtask.main_screen.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.main_screen.R
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
import com.example.testtask.main_screen.filterCategory.FilterCategory
import com.example.testtask.main_screen.viewmodel.MainViewModel
import com.example.testtask.navigation.AppScreens
import com.example.testtask.state_network_connection.UiState
import com.google.android.material.bottomsheet.BottomSheetDialog
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

    private val compositeAdapter by lazy {
        CompositeAdapter.Builder()
            .add(SectionDelegateAdapter())
            .add(categoriesDelegateAdapter)
            .add(SearchDelegateAdapter())
            .add(HotSalesDelegateAdapter(hotSalesItemClickListener))
            .add(BestSellerDelegateAdapter(bestSellerItemClickListener))
            .build()
    }

    private var brandsFilterItems: List<String> = emptyList()
    private var pricesFilterItems: List<String> = emptyList()
    private var sizesFilterItems: List<String> = emptyList()

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
        viewModel.saveCurrentSelectedItems()
        val filterDialog = createFilterBottomSheetDialog()
        filterDialog.show()
    }

    private fun createFilterBottomSheetDialog(): BottomSheetDialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        with(bottomSheetDialog) {
            setContentView(R.layout.bottom_sheet_dialog_filter)
            findViewById<Spinner>(R.id.brandsSpinner)?.setFilterSpinner(FilterCategory.BRAND)
            findViewById<Spinner>(R.id.priceSpinner)?.setFilterSpinner(FilterCategory.PRICE)
            findViewById<Spinner>(R.id.sizeSpinner)?.setFilterSpinner(FilterCategory.SIZE)
            findViewById<TextView>(R.id.doneTextView)?.setOnClickListener { dismiss() }
            findViewById<ImageView>(R.id.closeImageView)?.setOnClickListener {
                undoFilterChanges()
                dismiss()
            }
        }
        return bottomSheetDialog
    }

    private fun undoFilterChanges() {
        viewModel.restorePreviousSelections()
    }

    private fun Spinner.setFilterSpinner(category: FilterCategory) {
        val items = when (category) {
            FilterCategory.BRAND -> brandsFilterItems
            FilterCategory.PRICE -> pricesFilterItems
            FilterCategory.SIZE -> sizesFilterItems
        }
        adapter = createSpinnerAdapter(items).apply { tag = category }
        onItemSelectedListener = createFilterSpinnerOnItemSelectedListener()
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

    private fun createSpinnerAdapter(items: List<String>) =
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            items
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

    private fun setupRecyclerView() {
        with(binding.mainRecyclerView) {
            adapter = compositeAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
        }
    }

    private fun observe() {
        with(viewModel) {
            uiState.observe(viewLifecycleOwner) {
                updateUiState(it)
            }
            mainPageUiItems.observe(viewLifecycleOwner) {
                compositeAdapter.submitList(it)
            }
            selectedCategoryTag.observe(viewLifecycleOwner) {
                categoriesDelegateAdapter.setSelectedItem(it)
            }
            brands.observe(viewLifecycleOwner) {
                this@MainScreenFragment.brandsFilterItems = it
            }
            prices.observe(viewLifecycleOwner) {
                this@MainScreenFragment.pricesFilterItems = it
            }
            sizes.observe(viewLifecycleOwner) {
                this@MainScreenFragment.sizesFilterItems = it
            }
            cartSize.observe(viewLifecycleOwner) {
                setCartSize(it)
            }
        }
    }

    private fun setCartSize(size: Int) {
        if (size > 0) {
            with(binding.bottomCartCountTextView) {
                text = size.toString()
                visibility = View.VISIBLE
            }
        } else {
            with(binding.bottomCartCountTextView) {
                text = size.toString()
                visibility = View.GONE
            }
        }
    }

    private fun updateUiState(state: UiState) {
        when (state) {
            is UiState.Success -> with(binding) {
                successStateUi.visibility = View.VISIBLE
//                errorStateUi.visibility = View.GONE
            }
            is UiState.Error -> with(binding) {
                successStateUi.visibility = View.GONE
//                errorStateUi.visibility = View.VISIBLE
                setTryAgainButtonClickListener()
            }
            is UiState.Loading -> {}
        }
    }

    private fun setTryAgainButtonClickListener() {
        view?.let {
            it.findViewById<FrameLayout>(
                com.example.testtask.state_network_connection.R.id.tryAgainFrame
            )?.let { tryAgainTextView ->
                tryAgainTextView.setOnClickListener {
                    viewModel.retryNetworkCall()
                }
            }
        }
    }
}