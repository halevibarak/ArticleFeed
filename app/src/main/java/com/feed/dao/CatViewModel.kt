package com.feed.dao

import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay

import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

class CatViewModel(
    val catRepository: CatRepository
) : ViewModel() {
    val cats: Flowable<List<Article>> = catRepository.startListeningForCats()

    val openCatEvent = PublishRelay.create<Article>()

    fun observeCats(onNext: (List<Article>) -> Unit, onError: (Throwable) -> Unit = {}): Disposable =
        cats.subscribeBy(onNext = onNext, onError = onError)

    fun handleScrollToBottom(scrolled: Boolean) {
        catRepository.startFetch(scrolled)
    }

    fun openCatPage(cat: Article) {
        openCatEvent.accept(cat)
    }
}