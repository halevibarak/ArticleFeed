package com.feed.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Barak on 24/08/2017.
 */

class ArticleResponse(@field:SerializedName("articles")
                      var articles: List<Article>)


