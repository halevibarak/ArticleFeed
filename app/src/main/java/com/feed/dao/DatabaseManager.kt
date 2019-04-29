package com.zhuinden.mvvmaacrxjavaretrofitroom.data.local.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Database
import com.feed.dao.Article
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.local.dao.CatDao


@Database(entities = [Article::class], version = 1, exportSchema = true)
@TypeConverters(RoomTypeConverters::class)
abstract class DatabaseManager : RoomDatabase() {
    abstract val catDao: CatDao
}