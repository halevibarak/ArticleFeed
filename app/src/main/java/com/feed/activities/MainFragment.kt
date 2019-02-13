package com.feed.activities

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
import com.feed.R
import com.feed.adapter.ArticleAdapter
import com.feed.adapter.ArticleDecoration
import com.feed.interfaces.DescriptionInterface
import com.feed.model.Article
import java.util.*


class MainFragment : Fragment(), DescriptionInterface {
    private var contactsAdapter: ArticleAdapter? = null
    private var articles: ArrayList<Article>? = null
    private var mProgressView: ProgressBar? = null
    private var postModel: ArticleModel? = null
    private var recyclerView: RecyclerView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
                R.layout.fragment_main, container, false)
        retainInstance = true
        recyclerView = rootView.findViewById(R.id.article_list)
        mProgressView = rootView.findViewById(R.id.determinateBar)
        postModel = ViewModelProviders.of(this).get(ArticleModel::class.java)
        postModel!!.articleList!!.observe(this, Observer { articles_ ->
            mProgressView!!.visibility = View.GONE
            if (articles==null){
                this.articles = articles_
                updateUI()
            }else{
                if (articles_!=null)
                for (art in articles_) {
                    this.articles!!.add(art)
                }
                contactsAdapter!!.notifyDataSetChanged()
            }
        })
        return rootView
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

    override fun onResume() {
        super.onResume()
        postModel!!.refreshDataIfNeeded()

    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }

        fun dpToPx(dp: Int): Int {
            return (dp * Resources.getSystem().displayMetrics.density).toInt()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contactsAdapter = null
        articles = null
        mProgressView = null
        postModel = null
        recyclerView = null
    }
}
