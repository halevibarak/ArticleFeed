package com.feed.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.feed.R
import com.feed.adapter.ArticleAdapter
import com.feed.adapter.ArticleDecoration
import com.feed.application.RxApplication
import com.feed.interfaces.DescriptionInterface
import com.feed.dao.Article
import com.feed.dao.CatViewModel
import com.feed.dao.RecyclerViewScrollBottomOnSubscribe
import com.feed.dao.createViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*


class MainFragment : android.support.v4.app.Fragment(), DescriptionInterface {
    private var contactsAdapter: ArticleAdapter? = null
    private var articles: ArrayList<Article>? = null
    private var postModel: ArticleModel? = null
    private lateinit var catViewModel: CatViewModel
    private val compositeDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val application = context.applicationContext as RxApplication

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInarticle_liststanceState: Bundle?): View? {
        val rootView = inflater.inflate(
                R.layout.fragment_main, container, false)
        retainInstance = true
        val application = context!!.applicationContext as RxApplication
        catViewModel = createViewModel { CatViewModel(application.catRepository) }

        return rootView

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable += catViewModel.observeCats(onNext = { articles_ ->
            determinateBar.visibility = View.GONE
            if (articles==null){
//                this.articles = articles_
//                updateUI()
//            }else{
//                if (articles_!=null)
//                    for (art in articles_) {
//                        this.articles!!.add(art)
//                    }
//                contactsAdapter!!.notifyDataSetChanged()
            }
        })

        compositeDisposable += Observable.create(RecyclerViewScrollBottomOnSubscribe(article_list))
                .subscribeBy(onNext = { isScroll ->
                    catViewModel.handleScrollToBottom(isScroll)
                })


    }

    override fun goToDescription(url_: String) {
        val fragment = WebFragment()
        val bundle = Bundle()
        bundle.putString(WebFragment.LINK, url_)
        fragment.arguments = bundle
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.fade_in, R.anim.fade_out)
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun updateUI() {
        if (articles != null) {
            if (articles!!.size == 0) {
                determinateBar.visibility = View.GONE
            } else {
                determinateBar.visibility = View.GONE
                contactsAdapter = ArticleAdapter(articles!!, this)
                article_list.visibility = View.VISIBLE
                article_list.layoutManager = LinearLayoutManager(article_list.context)
                article_list.addItemDecoration(ArticleDecoration(dpToPx(10)))
                article_list.adapter = contactsAdapter
            }
        } else {
            Toast.makeText(context, "Check the Internet Connection", Toast.LENGTH_LONG).show()
        }


    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        contactsAdapter = null
        articles = null
        postModel = null
    }
}
