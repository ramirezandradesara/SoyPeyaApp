package com.soyhenryfeature.products.ui

import ProductFilterBottomSheetContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soyhenryfeature.products.viewmodel.ProductsViewModel
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.soyhenry.core.state.UiState
import com.soyhenry.feature.cart.viewmodel.CartViewModel
import com.soyhenry.library.ui.components.state.EmptyState
import com.soyhenry.library.ui.components.container.ViewContainer
import androidx.compose.ui.res.stringResource
import com.soyhenry.library.ui.components.state.ErrorState
import com.soyhenryfeature.products.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsView(
    viewModel: ProductsViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val categoriesState by viewModel.categoriesState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val filterText by viewModel.filterText.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedPrice by viewModel.selectedPrice.collectAsStateWithLifecycle()

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    fun onApplyFilters(category: String?, price: Float) {
        viewModel.onCategorySelected(category)
        viewModel.onPriceSelected(price)
        showBottomSheet = false
    }

    LaunchedEffect(Unit) {
        viewModel.loadInitialData(refreshData = true)
    }

    ViewContainer(title = stringResource(R.string.products_title)) {
        FilterBar(
            filterText = filterText,
            onFilterTextChange = viewModel::onFilterTextChange,
            onOpenFilters = { showBottomSheet = true }
        )

        when (val state = uiState) {
            is UiState.Loading -> {
                ProductsSkeletonList()
            }

            is UiState.Success -> {
                val products = state.data
                if (products.isEmpty()) {
                    EmptyState(
                        title = stringResource(R.string.no_products_title),
                        subtitle = stringResource(R.string.no_products_subtitle),
                        icon = Icons.Default.Search,
                    )
                } else {
                    ProductList(
                        products = products,
                        onAddToCart = cartViewModel::addToCart
                    )
                }

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = { showBottomSheet = false },
                        sheetState = bottomSheetState
                    ) {
                        ProductFilterBottomSheetContent(
                            categoryState = categoriesState,
                            selectedCategory = selectedCategory,
                            selectedPrice = selectedPrice,
                            onApplyFilters =  { category, price -> onApplyFilters(category, price) }
                        )
                    }
                }
            }

            is UiState.Error -> {
                ErrorState(message = state.message)
            }
        }
    }
}