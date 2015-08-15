package com.example.administrator.ex02_gson;

import com.google.gson.annotations.Expose;

public class NewCourse {

    @Expose
    private String coursename;
    @Expose
    private String teachername;
    @Expose
    private String imgurl;

    /**
     * @return The coursename
     */
    public String getCoursename() {
        return coursename;
    }

    /**
     * @param coursename The coursename
     */
    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    /**
     * @return The teachername
     */
    public String getTeachername() {
        return teachername;
    }

    /**
     * @param teachername The teachername
     */
    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    /**
     * @return The imgurl
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * @param imgurl The imgurl
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

}
