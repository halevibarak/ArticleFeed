package com.zhuinden.mvvmaacrxjavaretrofitroom.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.feed.dao.Article
import io.reactivex.Flowable


@Dao
interface CatDao {
    @Query("SELECT * FROM ${Article.TABLE_NAME}")
    fun getArticles(): Flowable<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(cats: List<Article>)

    @Query("SELECT COUNT(*) FROM ${Article.TABLE_NAME}")
    fun count(): Int
}