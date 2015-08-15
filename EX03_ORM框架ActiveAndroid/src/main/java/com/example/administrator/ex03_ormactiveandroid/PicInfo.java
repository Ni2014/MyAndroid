package com.example.administrator.ex03_ormactiveandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Administrator on 15.4.12.
 */
@Table(name="picinfo")
public class PicInfo extends Model {
    @Column
    String picUrl;

    @Column(name="news")
    News news;

    public PicInfo() {
    }

    public PicInfo(String picUrl, News news) {
        this.picUrl = picUrl;
        this.news = news;
    }

    @Override
    public String toString() {
        return "PicInfo{" +
                "picUrl='" + picUrl + '\'' +
                ", news=" + news +
                '}';
    }
}
