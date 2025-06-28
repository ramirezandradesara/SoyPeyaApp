package com.soyhenryfeature.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.model.Product
import com.soyhenryfeature.products.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel
@Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    private var allProducts: List<Product> = emptyList()

    private val _filterText = MutableStateFlow("")
    val filterText: StateFlow<String> = _filterText.asStateFlow()

    init {
        loadProducts()
    }

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Error in ProductsViewModel: ${exception.message}")
    }

    fun onFilterTextChange(filter: String) {
        _filterText.value = filter
        applyFilter()
    }

    private fun applyFilter() {
        val text = _filterText.value.trim()
        val filtered = if (text.isBlank()) {
            allProducts
        } else {
            allProducts.filter { product ->
                product.name.contains(text, ignoreCase = true) ||
                        product.description.contains(text, ignoreCase = true)
            }
        }
        _uiState.value = ProductsUiState.Success(filtered)
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                allProducts = repository.getProducts()
                applyFilter()
            } catch (e: Exception) {
                _uiState.value = ProductsUiState.Error(e.message ?: "Error loading products")
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.addProduct(product)
                loadProducts()
            } catch (e: Exception) {
                _uiState.value = ProductsUiState.Error("Failed to add product")
            }
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(productId)
                loadProducts()
            } catch (e: Exception) {
                _uiState.value = ProductsUiState.Error("Failed to delete product")
            }
        }
    }
}
