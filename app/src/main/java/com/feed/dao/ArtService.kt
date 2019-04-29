package com.feed.dao

import io.reactivex.Single
import retrofit2.http.GET

interface ArtService {
    @GET("everything?q=bitcoin&from=2019-03-28&sortBy=publishedAt&apiKey=20db1cfcc97442bcb409ee97e3360cd9")
    fun getArticles(): Single<ArticleBO>
}