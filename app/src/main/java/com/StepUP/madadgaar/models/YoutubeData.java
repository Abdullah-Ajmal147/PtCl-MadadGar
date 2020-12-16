package com.StepUP.madadgaar.models;

import java.io.Serializable;

public class YoutubeData implements Serializable {

    private String title;
    private String thumbnail;
    private String vedio_id;

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    private String quiz;

    public YoutubeData(String title, String thumbnail, String vedio_id) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.vedio_id = vedio_id;
    }

    public String getVedio_id() {
        return vedio_id;
    }

    public void setVedio_id(String vedio_id) {
        this.vedio_id = vedio_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
