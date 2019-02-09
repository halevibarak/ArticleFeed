package com.mvvm.model

import com.google.gson.annotations.SerializedName


/**
 * Created by Barak on 24/08/2017.
 */

data class Article(
        @SerializedName("title")
        var title: String,
        @SerializedName("author")
        var author: String,
        @SerializedName("url")
        var url: String,
        @SerializedName("urlToImage")
        var urlToImage: String,
        @SerializedName("publishedAt")
        var publishedAt: String)

