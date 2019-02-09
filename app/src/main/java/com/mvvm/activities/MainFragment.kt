package com.mvvm.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import com.mvvm.R
import com.mvvm.adapter.ArticleAdapter
import com.mvvm.adapter.ArticleDecoration
import com.mvvm.interfaces.DescriptionInterface
import com.mvvm.model.Article
import rx.Subscription
import java.util.*


class MainFragment : Fragment(), DescriptionInterface {
    private var contactsAdapter: ArticleAdapter? = null
    private var articles: List<Article>? = null
    private var mProgressView: ProgressBar? = null
    private val subscription: Subscription? = null
    private var postModel: ArticleModel? = null
    private var recyclerView: RecyclerView? = null
    private var mTimeStamp: Long=0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
                R.layout.fragment_main, container, false)
        retainInstance = true
        mTimeStamp = System.currentTimeMillis()
        recyclerView = rootView.findViewById(R.id.article_list)
        mProgressView = rootView.findViewById(R.id.determinateBar)
        postModel = ViewModelProviders.of(this).get(ArticleModel::class.java)
        postModel!!.articleList!!.observe(this, Observer { articles_ ->
            mProgressView!!.visibility = View.GONE
            this.articles = articles_
            updateUI()
        })
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        articles = ArrayList()


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
                mProgressView!!.visibility = View.GONE
            } else {
                mProgressView!!.visibility = View.GONE
                contactsAdapter = ArticleAdapter(articles, this)

                recyclerView!!.visibility = View.VISIBLE
                recyclerView!!.layoutManager = LinearLayoutManager(recyclerView!!.context)
                recyclerView!!.addItemDecoration(ArticleDecoration(dpToPx(10)))
                recyclerView!!.adapter = contactsAdapter
            }
        } else {
            Toast.makeText(context, "Check the Internet Connection", Toast.LENGTH_LONG).show()
        }


    }

    fun rxUnSubscribe() {
        if (subscription != null && !subscription.isUnsubscribed)
            subscription.unsubscribe()
    }

    override fun onPause() {
        super.onPause()
        rxUnSubscribe()
    }



    override fun onResume() {
        super.onResume()
        if (System.currentTimeMillis() > (mTimeStamp + 2000)) {
            postModel!!.refreshData()
        }


    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }

        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}
