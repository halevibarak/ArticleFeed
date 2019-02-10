package com.feed.activities

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.feed.application.RxApplication
import com.feed.model.Article
import com.feed.model.ArticleResponse
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * Created by Barak on 1/10/2018.
 */

class ArticleModel(application: Application) : AndroidViewModel(application) {

    private var mTmeStamp: Long = 0
    private var ArticleList: JsonLiveData? = null
    private var subscription: Subscription? = null


    private val refresh = MutableLiveData<Int>()

    val articleList: MutableLiveData<List<Article>>?
        get() = ArticleList

    init {
        if (ArticleList == null)
            ArticleList = JsonLiveData()

    }

    override fun onCleared() {
        subscription!!.unsubscribe()
    }

    fun refreshData() {
        if (System.currentTimeMillis() > mTmeStamp + 2000 ){
            ArticleList = JsonLiveData()
        }
    }

    inner class JsonLiveData() : MutableLiveData<List<Article>>() {

        init {
            LoadData()
        }


        private fun LoadData() {
            val service = RxApplication.instance_!!.networkService
            val articleResponseObservable = service!!.articleAPI.articles
            Log.d("network", "network network network network network network network ")
            mTmeStamp = System.currentTimeMillis()
            subscription = articleResponseObservable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())

                    .subscribe(object : Observer<ArticleResponse> {
                        override fun onCompleted() {}

                        override fun onError(e: Throwable) {
                            value = null
                            refresh.value = 1
                        }

                        override fun onNext(response: ArticleResponse) {
                            value = response.articles
                            refresh.value = 1
                        }
                    })

        }


    }


}