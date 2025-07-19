package com.soyhenryfeature.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.domain.Product
import com.soyhenry.core.state.UiState
import com.soyhenryfeature.products.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel
@Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> = _uiState

    private var allProducts: List<Product> = emptyList()

    private val _filterText = MutableStateFlow("")
    val filterText: StateFlow<String> = _filterText.asStateFlow()

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Error in ProductsViewModel: ${exception.message}")
    }

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _selectedPrice = MutableStateFlow(200f)
    val selectedPrice: StateFlow<Float> = _selectedPrice.asStateFlow()

    fun onCategorySelected(category: String?) {
        _selectedCategory.value = category
        applyFilter()
    }

    fun onPriceSelected(price: Float) {
        _selectedPrice.value = price
        applyFilter()
    }


    fun loadProducts(refreshData: Boolean = false) {
        viewModelScope.launch {
            try {
                allProducts = getProductsUseCase(refreshData)
                applyFilter()
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error loading products")
            }
        }
    }

    fun onFilterTextChange(filter: String) {
        _filterText.value = filter
        applyFilter()
    }

    private fun applyFilter() {
        val text = _filterText.value.trim()
        val category = _selectedCategory.value
        val maxPrice = _selectedPrice.value

        val filtered = allProducts.filter { product ->
            val matchesText = text.isBlank() || product.name.contains(text, ignoreCase = true)
            val matchesCategory = category == null || product.category.equals(category, ignoreCase = true)
            val matchesPrice = product.price <= maxPrice

            matchesText && matchesCategory && matchesPrice
        }

        _uiState.value = UiState.Success(filtered)
    }
}
