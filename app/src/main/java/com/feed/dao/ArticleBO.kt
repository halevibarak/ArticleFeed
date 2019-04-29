package com.feed.dao


import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Path
import org.simpleframework.xml.Root

import java.util.ArrayList


/**
 * Created by Zhuinden on 2016.07.28..
 */
@Root(name = "articles")
class ArticleBO {
    @Path("data/images")
    @ElementList(type = ArtBO::class, inline = true)
    val articles: ArrayList<ArtBO>? = null

}