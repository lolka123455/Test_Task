package com.example.testtask.detail_screen.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.testtask.detail_screen.R
import com.example.testtask.detail_screen.databinding.FragmentDetailsBinding
import com.example.testtask.detail_screen.entities.ProductDetails
import com.example.testtask.detail_screen.viewmodel.DetailsViewModel
import com.example.testtask.detail_screen.adapters.ProductImagesViewPagerAdapter
import com.example.testtask.navigation.AppScreens
import com.example.testtask.state_network_connection.UiState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

class DetailsScreenFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val viewModel by viewModel<DetailsViewModel>()

    private val adapter = ProductImagesViewPagerAdapter()

    private var colorImageViews: List<ImageView> = emptyList()
    private var capacityTextViews: List<TextView> = emptyList()

    private lateinit var chosenColorImageView: ImageView
    private lateinit var chosenCapacityTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupColorAndCapacityViews()
        setupViewPager()
        observe()
    }

    private fun setupViewPager() {
        with(binding.viewPager) {
            adapter = this@DetailsScreenFragment.adapter
            setupTransformer()
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    /**
     * Sets up a page transformer for the ViewPager2 that scales the side pages to 80% of their original height
     * and keeps the front page at 100% of its original height.
     */

    private fun ViewPager2.setupTransformer() {
        setPageTransformer { page, position ->
            val sidePageScaleY = 0.8f
            val isFrontPage = 1 - abs(position)
            page.scaleY = sidePageScaleY + isFrontPage * (1 - sidePageScaleY)
        }
    }

    private fun setupColorAndCapacityViews() {
        chosenColorImageView = binding.firstColorImageView
        chosenCapacityTextView = binding.firstCapacity
        colorImageViews = with(binding) {
            listOf(firstColorImageView, secondColorImageView)
        }
        capacityTextViews = with(binding) {
            listOf(firstCapacity, secondCapacity)
        }
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        setColorsClickListener()
        setCapacityClickListener()
        setBackViewClickListener()
        setCartClickListener()
    }

    private fun setCartClickListener() {
        binding.cartImageView.setOnClickListener {
            findNavController().navigate(AppScreens.CartScreen)
        }
    }

    private fun setBackViewClickListener() {
        binding.backImageView.setOnClickListener { requireActivity().onBackPressed() }
    }

    private fun setCapacityClickListener() {
        capacityTextViews.forEach { capacityTextView ->
            capacityTextView.setOnClickListener {
                unselectChosenCapacity()
                selectCapacity(it as TextView)
            }
        }
    }

    private fun setColorsClickListener() {
        colorImageViews.forEach { colorImageView ->
            colorImageView.setOnClickListener {
                unselectChosenColor()
                selectColor(it as ImageView)
            }
        }
    }

    private fun selectCapacity(capacityToSelect: TextView) {
        with(capacityToSelect) {
            backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.orange))
            setTextColor(Color.WHITE)
        }
        chosenCapacityTextView = capacityToSelect
    }

    private fun unselectChosenCapacity() {
        with(chosenCapacityTextView) {
            backgroundTintList = null
            setTextColor(ContextCompat.getColor(requireContext(), R.color.dark_grey))
        }
    }

    private fun selectColor(colorToSelect: ImageView) {
        colorToSelect.setImageResource(R.drawable.ic_selected)
        chosenColorImageView = colorToSelect
    }

    private fun unselectChosenColor() {
        chosenColorImageView.setImageDrawable(null)
    }

    private fun observe() {
        with(viewModel) {
            productDetails.onEach {
                fillDetailsUi(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
            uiState.onEach {
                updateUiState(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    /**
     * This function is used to fill the UI elements with the details of a product.
     * @param details: ProductDetails? - an object containing all the details of the product.
     */

    private fun fillDetailsUi(details: ProductDetails?) {
        with(binding) {
            if (details != null) {
                titleTextView.text = details.title
                ratingBar.rating = details.rating.toFloat()
                cpuTextView.text = details.cpu
                cameraTextView.text = details.camera
                ramTextView.text = details.ram
                sdTextView.text = details.sd
                priceTextView.text = details.price.toPriceFormat()

                favouriteImageView.setImageResource(
                    if (details.isFavorites) {
                        R.drawable.ic_favorite
                    } else {
                        R.drawable.ic_favorite_border
                    }
                )

                setProductColors(details)
                setProductCapacity(details)
                adapter.currentList = details.images
            }
        }
    }

    /**
     * This function updates the capacity of the product in the UI.
     * @param details: ProductDetails - A data class that contains the product information.
     * @property capacity: List<Int> - A list of integers representing the capacity of the product.
     *
     * @see ProductDetails
     *
     */

    private fun setProductCapacity(details: ProductDetails) {
        details.capacity.forEachIndexed { i, capacity ->
            with(capacityTextViews[i]) {
                makeVisible()
                text = "$capacity GB"
            }
        }
    }

    /**
     * setProductColors sets the background color of the colorImageViews based on the color
     * property of the passed in [ProductDetails] object.
     *
     * @param details a [ProductDetails] object that contains the color property
     */

    private fun setProductColors(details: ProductDetails) {
        details.color.forEachIndexed { i, color ->
            with(colorImageViews[i]) {
                makeVisible()
                backgroundTintList = ColorStateList.valueOf(Color.parseColor(color))
            }
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

    private fun View.makeVisible() {
        visibility = View.VISIBLE
    }

    /**
    *Converts an Int to a formatted price String.
    *@return Formatted price String.
    *If the input Int is greater than or equal to 1000, the format is "$x,xxx.00".
    *If the input Int is less than 1000, the format is "x.00".
     */

    private fun Int.toPriceFormat(): String =
        if (this >= 1000) {
            "${"$%.${3}f".format(this / 1000.0)}.00"
        } else "$$this.00"
}