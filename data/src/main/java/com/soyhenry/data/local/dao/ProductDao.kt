package com.soyhenry.data.local.dao

import androidx.room.*
import com.soyhenry.core.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProduct(productId: String)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: String): ProductEntity?

    @Query("DELETE FROM products")
    suspend fun clearAll()

    @Query("SELECT EXISTS(SELECT 1 FROM cart_items WHERE productId = :productId)")
    suspend fun isProductUsedInCart(productId: String): Boolean

    @Transaction
    suspend fun updateProducts(newProducts: List<ProductEntity>) {
        newProducts.forEach { product ->
            val existing = getProductById(product.id)
            if (existing != null) {
                updateProduct(product)
            } else {
                insertProduct(product)
            }
        }
    }
}