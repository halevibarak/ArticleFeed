package com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.task

import com.feed.dao.ArtBO
import com.feed.dao.ArtService
import com.feed.dao.Article
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.local.dao.CatDao

import com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.task.CatTaskManager.Companion.SAVE_CATS_TASK
import com.zhuinden.mvvmaacrxjavaretrofitroom.utils.schedulers.Scheduler
import io.reactivex.Single
import io.reactivex.rxkotlin.subscribeBy

class CatTaskFactory(
        val catsTaskManager: CatTaskManager,
        val catsService: ArtService,
        val catDao: CatDao,
        val networkScheduler: Scheduler,
        val ioScheduler: Scheduler
) {
    fun createSaveArticleTask(): Single<Unit> = Single.create { emitter ->
        catsTaskManager.registerTask(SAVE_CATS_TASK)
        catsService.getArticles()
            .subscribeOn(networkScheduler.asRxScheduler())
            .observeOn(ioScheduler.asRxScheduler())
            .doFinally { catsTaskManager.unregisterTask(SAVE_CATS_TASK) }
            .subscribeBy(onSuccess = { catsBO ->
                val arrayList = catsBO.articles
                if (arrayList == null) {
                    emitter.onSuccess(Unit)
                    return@subscribeBy
                }
//                val maxRank = catDao.count()
//                catsBO.articles.mapIndexed { index: Int, catBO: ArtBO ->
//                    with(catBO) {
//                        Article(title!!,author, url,urlToImage, publishedAt)
//                    }
//                }.let { catList ->
//                    catDao.insertArticles(catList)
//                }
                emitter.onSuccess(Unit)
            }, onError = { throwable ->
                emitter.onError(throwable)
            })
    }
}