package com.feed.dao;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Zhuinden on 2016.07.28..
 */
@Root(name = "image")
public class ArtBO {
    @Element(name = "title")
    private String title;

    @Element(name = "author")
    private String author;

    @Element(name = "url")
    private String url;

    @Element(name = "urlToImage")
    private String urlToImage;

    @Element(name = "publishedAt")
    private String publishedAt;


    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}