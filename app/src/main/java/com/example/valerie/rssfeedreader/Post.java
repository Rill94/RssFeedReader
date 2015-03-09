package com.example.valerie.rssfeedreader;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Valerie on 02.03.2015.
 */
public class Post implements Serializable
{
    private String title;
    private String descr;
    private List<SyndEnclosureImpl> enclosures;
    private Date date;
    private String url;
    private List<SyndCategoryImpl> category;

    public Post(String title, String descr, List<SyndEnclosureImpl> enclosures, Date date, List<SyndCategoryImpl> category) {
        this.title = title;
        this.descr = descr;
        this.enclosures = enclosures;
        this.date = date;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<SyndEnclosureImpl> getEnclosures() {
        return enclosures;
    }

    public void setEnclosures(List<SyndEnclosureImpl> enclosures) {
        this.enclosures = enclosures;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SyndCategoryImpl> getCategory() {
        return category;
    }

    public void setCategory(List<SyndCategoryImpl> category) {
        this.category = category;
    }
}
