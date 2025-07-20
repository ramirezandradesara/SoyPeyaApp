package com.soyhenry.data.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.soyhenry.data.repository.ProductsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@HiltWorker
class ProductSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val entryPoint = EntryPointAccessors.fromApplication(
                applicationContext,
                ProductSyncManagerEntryPoint::class.java
            )
            val getProductsUseCase = entryPoint.getProductsUseCase()
            getProductsUseCase.invoke(refreshData = true)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ProductSyncManagerEntryPoint {
    fun getProductsUseCase(): GetProductsUseCase
}

class GetProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
){
    suspend operator fun invoke(refreshData: Boolean) = productsRepository.getAllProducts(refreshData)
}
