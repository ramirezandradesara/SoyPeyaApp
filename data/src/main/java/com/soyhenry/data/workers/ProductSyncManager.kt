package com.soyhenry.data.workers

import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProductSyncManager @Inject constructor(
    private val workManagerHelper: WorkManagerHelper
) {
    fun schedulePeriodicProductSync() {
        workManagerHelper.schedulePeriodicTask<ProductSyncWorker>(
            uniqueWorkName = "PRODUCT_SYNC_WORKER",
            repeatInterval = 15,
            timeUnit = TimeUnit.MINUTES,
        )
    }

    fun syncNow() {
        workManagerHelper.scheduleOneTimeTask<ProductSyncWorker>(
            uniqueWorkName = "PRODUCT_SYNC_NOW",
        )
    }

    fun cancelProductSync() {
        workManagerHelper.cancelWork("ProductSyncWorker")
    }
}
