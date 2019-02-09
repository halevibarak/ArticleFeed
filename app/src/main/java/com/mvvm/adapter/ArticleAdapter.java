package com.mvvm.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvvm.R;
import com.mvvm.interfaces.DescriptionInterface;
import com.mvvm.model.Article;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Barak on 24/08/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> fullContactCellList;
    private DescriptionInterface mLisenner;
    private List<Article> mFilterData;
    @SuppressLint("SimpleDateFormat")
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    @SuppressLint("SimpleDateFormat")
    DateFormat SimpleDateFormatUI = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");


    public ArticleAdapter(List<Article> contactList, DescriptionInterface listenner) {
        this.fullContactCellList = contactList;
        mFilterData = new ArrayList<>();
        mFilterData.addAll(fullContactCellList);
        mLisenner = listenner;
        sdf.setTimeZone(TimeZone.getTimeZone("IL"));
    }


    @Override
    public int getItemCount() {
        return mFilterData.size();
    }

    public Article getItem(int position) {
        return mFilterData.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View horizontalItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ArticleViewHolder(horizontalItem);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        final Article article = getItem(position);
        if (article != null) {
            holder.titleView.setText(article.getTitle());
            Date date = null;
            try {
                date = sdf.parse(article.getPublishedAt());
                holder.dateView.setText(SimpleDateFormatUI.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Glide.with(holder.imgView.getContext()).load(article.getUrlToImage()).into(holder.imgView);
        }
        holder.itemView.setOnClickListener(v -> mLisenner.goToDescription(article.getUrl()));
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {


        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleView =  itemView.findViewById(R.id.title_text);
            dateView =  itemView.findViewById(R.id.date_text);
            imgView   =  itemView.findViewById(R.id.img_view);

        }

        private final ImageView imgView;
        TextView titleView;
        TextView dateView;
    }


}
