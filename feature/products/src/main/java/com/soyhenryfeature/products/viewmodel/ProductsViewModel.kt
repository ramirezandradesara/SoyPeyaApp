package com.soyhenryfeature.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenry.core.model.database.entities.ProductEntity
import com.soyhenry.core.repository.ProductsRepository
import com.soyhenry.core.state.UiState
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
    private val repository: ProductsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ProductEntity>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<ProductEntity>>> = _uiState

    private var allProducts: List<ProductEntity> = emptyList()

    private val _filterText = MutableStateFlow("")
    val filterText: StateFlow<String> = _filterText.asStateFlow()

    init {
        loadProducts()
    }

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Error in ProductsViewModel: ${exception.message}")
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                repository.refreshProducts()
                allProducts = repository.getAllProducts()
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

        val filtered = if (text.isBlank()) {
            allProducts
        } else {
            allProducts.filter { product ->
                product.productName.contains(text, ignoreCase = true)
            }
        }
        _uiState.value = UiState.Success(filtered)
    }
}
