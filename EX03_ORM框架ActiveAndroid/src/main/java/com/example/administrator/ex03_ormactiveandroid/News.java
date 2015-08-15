package com.example.administrator.ex03_ormactiveandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by Administrator on 15.4.12.
 */
@Table(name="news")
public class News extends Model {
    @Column
    String title;
    @Column
    String subTitle;
    @Column(name="from_src")
    String from;

    public List<PicInfo> getPics(){
        return getMany(PicInfo.class, "news");
    }

    public News() {
    }

    public News(String title, String subTitle, String from) {
        this.title = title;
        this.subTitle = subTitle;
        this.from = from;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
