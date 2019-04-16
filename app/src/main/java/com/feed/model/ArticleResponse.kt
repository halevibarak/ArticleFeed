package com.feed.model

import com.google.gson.annotations.SerializedName




class ArticleResponse(@field:SerializedName("articles")
                      var articles: ArrayList<Article>)


