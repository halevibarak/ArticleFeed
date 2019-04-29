package com.feed.activities

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.feed.application.RxApplication
import com.feed.dao.Article
import com.feed.model.ArticleResponse
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers





class ArticleModel(application: Application) : AndroidViewModel(application) {

    private val mediatorLiveData = MediatorLiveData<List<Article>>()
    private var ArticleList= JsonLiveData()
    private var mTmeStamp: Long = 0

    private var subscription: Subscription? = null

    val articleList: JsonLiveData
        get() = ArticleList

    init {
        mediatorLiveData.addSource(ArticleList, { mediatorLiveData.value = it })
    }
    override fun onCleared() {
        subscription!!.unsubscribe()
    }

    fun refreshDataIfNeeded() {
        if (System.currentTimeMillis() > mTmeStamp + 5000) {
            mediatorLiveData.removeSource(ArticleList)
            ArticleList = JsonLiveData()
            mediatorLiveData.addSource(ArticleList) {
                mediatorLiveData.value = it
                ArticleList = JsonLiveData()
            }
        }
    }

    inner class JsonLiveData : MutableLiveData<ArrayList<Article>>() {

        init {
            loadData()
        }

        private fun loadData() {
            val service = RxApplication.instance_!!.networkService
            val articleResponseObservable = service!!.articleAPI.articles
            mTmeStamp = System.currentTimeMillis()
//            subscription = articleResponseObservable.observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(object : Observer<ArticleResponse> {
//                        override fun onCompleted() {}
//                        override fun onError(e: Throwable) {
//                            value = null
//                        }
//
//                        override fun onNext(response: ArticleResponse) {
//                            value = response.articles
//                        }
//                    })
        }
    }
}