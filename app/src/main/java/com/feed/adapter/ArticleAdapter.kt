package com.feed.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.feed.R
import com.feed.interfaces.DescriptionInterface
import com.feed.model.Article
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.contact_item.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.contact_item.*


class ArticleAdapter(private val mArticleList: ArrayList<Article>, private val mLisenner: DescriptionInterface) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    @SuppressLint("SimpleDateFormat")
    internal var sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @SuppressLint("SimpleDateFormat")
    internal var SimpleDateFormatUI = SimpleDateFormat("EEE, d MMM yyyy HH:mm")


    init {
        sdf.timeZone = TimeZone.getTimeZone("IL")
    }


    override fun getItemCount(): Int {
        return mArticleList.size
    }

    fun getItem(position: Int): Article? {
        return mArticleList[position]
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val horizontalItem = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ArticleViewHolder(horizontalItem)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(mArticleList[position])

    }

    inner class ArticleViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(article: Article) {
            title_text.text = article.title
            try {
                val date = sdf.parse(article.publishedAt)
                date_text.text = SimpleDateFormatUI.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            Glide.with(containerView).load(article.urlToImage).into(img_view)
            containerView.setOnClickListener { mLisenner.goToDescription(article.url) }

        }

    }


}
