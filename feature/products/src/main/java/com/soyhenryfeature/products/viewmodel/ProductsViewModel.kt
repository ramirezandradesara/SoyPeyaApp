package com.soyhenryfeature.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyhenryfeature.products.data.model.Product
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
@Inject constructor( private val repository: ProductsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Error in ProductsViewModel: ${exception.message}")
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _uiState.value = ProductsUiState.Loading
            try {
                val products = repository.getProducts()
                _uiState.value = ProductsUiState.Success(products)
            } catch (e: Exception) {
                _uiState.value = ProductsUiState.Error(e.message ?: "Error in loading products")
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                repository.addProduct(product)
                loadProducts() // Refresh the list
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
