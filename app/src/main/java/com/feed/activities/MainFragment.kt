package com.feed.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.feed.interfaces.DescriptionInterface
import com.feed.model.Article
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*


class MainFragment : android.support.v4.app.Fragment(), DescriptionInterface {
    private var contactsAdapter: ArticleAdapter? = null
    private var articles: ArrayList<Article>? = null
    private var postModel: ArticleModel? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(
                R.layout.fragment_main, container, false)
        retainInstance = true
        postModel = ViewModelProviders.of(this).get(ArticleModel::class.java)
        postModel!!.articleList.observe(this, Observer { articles_ ->
            determinateBar.visibility = View.GONE
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

    override fun onResume() {
        super.onResume()
        postModel!!.refreshDataIfNeeded()

    }

    companion object {

    }

    fun newInstance(): MainFragment {
        return MainFragment()
    }
    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        contactsAdapter = null
        articles = null
        postModel = null
    }
}
