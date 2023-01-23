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

    private fun ViewPager2.setupTransformer() {
        setPageTransformer { page, position ->
            val sidePageScaleY = 0.8f
            val isFrontPage = 1 - abs(position) // 1 if is front, 0 if not

            // makes side page height to (sidePageScaleY * 100)% of full size, front page to full size
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
            findNavController().navigate(AppScreens.CartScreen.Entry)
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
            productDetails.observe(viewLifecycleOwner) {
                fillDetailsUi(it)
            }
            uiState.observe(viewLifecycleOwner) {
                updateUiState(it)
            }
        }
    }

    private fun fillDetailsUi(details: ProductDetails) {
        with(binding) {
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

    private fun setProductCapacity(details: ProductDetails) {
        details.capacity.forEachIndexed { i, capacity ->
            with(capacityTextViews[i]) {
                makeVisible()
                text = "$capacity GB"
            }
        }
    }

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

    private fun View.makeVisible() {
        visibility = View.VISIBLE
    }

    private fun Int.toPriceFormat(): String =
        if (this >= 1000) {
            "${"$%.${3}f".format(this / 1000.0)}.00"
        } else "$$this.00"
}