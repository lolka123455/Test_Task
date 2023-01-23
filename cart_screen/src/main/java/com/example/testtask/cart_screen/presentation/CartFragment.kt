package com.example.testtask.cart_screen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.cart_screen.databinding.FragmentCartBinding
import com.example.testtask.cart_screen.presentation.recyclerview.CartItemsAdapter
import com.example.testtask.cart_screen.presentation.viewmodel.CartViewModel
import com.example.testtask.state_network_connection.UiState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val viewModel: CartViewModel by viewModel<CartViewModel>()
    private val adapter: CartItemsAdapter by lazy { CartItemsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backImageView.setOnClickListener { requireActivity().onBackPressed() }

        observe()
        setupRecyclerView()
    }

    private fun observe() {
        with(viewModel) {
            uiState.observe(viewLifecycleOwner) {
                updateUiState(it)
            }
            cartItems.observe(viewLifecycleOwner) {
                adapter.currentList = it
            }
            total.observe(viewLifecycleOwner) {
                binding.totalTextView.text = it.toPriceFormat()
            }
            delivery.observe(viewLifecycleOwner) {
                binding.deliveryTextView.text = it
            }
        }
    }

    private fun updateUiState(state: UiState) {
        when (state) {
            is UiState.Success -> with(binding) {
                successStateUi.visibility = View.VISIBLE
//                errorStateUi.visibility = View.GONE
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
            is UiState.Error -> with(binding) {
                successStateUi.visibility = View.GONE
//                errorStateUi.visibility = View.VISIBLE
                setTryAgainButtonClickListener()
            }
            is UiState.Loading -> with(binding) {
                successStateUi.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
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

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = this@CartFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun Int.toPriceFormat(): String =
        if (this >= 1000) {
            "$%.${3}f us".format(this / 1000.0)
        } else "$$this us"
}


