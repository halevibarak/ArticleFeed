package com.feed.dao

import android.util.Log
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.local.dao.CatDao
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.task.CatTaskFactory
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.task.CatTaskManager
import com.zhuinden.mvvmaacrxjavaretrofitroom.utils.schedulers.Scheduler
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy

class CatRepository(
    val catDao: CatDao,
    val catTaskManager: CatTaskManager,
    val catTaskFactory: CatTaskFactory,
    val mainScheduler: Scheduler
) {
    companion object {
        const val TAG = "CatRepository"
    }

    fun startListeningForCats(): Flowable<List<Article>> =
        catDao.getArticles()
            .distinctUntilChanged()
            .observeOn(mainScheduler.asRxScheduler())
            .replay(1)
            .autoConnect(0)

    private fun isNoDataDownloaded(): Boolean = catDao.count() == 0

    private fun isDownloadInProgress(): Boolean =
        catTaskManager.isRegistered(CatTaskManager.SAVE_CATS_TASK)

    fun startFetch(scrolled: Boolean) {
        if(!isDownloadInProgress() && (scrolled || isNoDataDownloaded())) {
            Log.i(TAG, "Running fetch task.");
            catTaskFactory.createSaveArticleTask().subscribeBy(onSuccess = {
                // Success
            }, onError = { throwable ->
                Log.i(TAG, "Fetching failed", throwable)
            })
        }
    }
}