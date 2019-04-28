package com.feed.application

import com.feed.model.ArticleResponse

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import rx.Observable




class NetworkService  {
    val articleAPI: ArticleInterface

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        articleAPI = retrofit.create(ArticleInterface::class.java)
    }


    interface ArticleInterface {
        @get:GET("everything?q=bitcoin&from=2019-03-28&sortBy=publishedAt&apiKey=20db1cfcc97442bcb409ee97e3360cd9")
        val articles: Observable<ArticleResponse>
    }

    companion object {
        const val baseUrl = "https://newsapi.org/v2/"
    }
}