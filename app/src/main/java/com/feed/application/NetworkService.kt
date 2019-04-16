package com.feed.application

import com.feed.model.ArticleResponse

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
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
        @get:GET("everything?q=bitcoin&sortBy=publishedAt&apiKey=3b6ed1bf2c3d457eb16dd72a9529deaa")
        val articles: Observable<ArticleResponse>
    }

    companion object {
        const val baseUrl = "https://newsapi.org/v2/"
    }
}