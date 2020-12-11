package com.StepUP.madadgaar.models;

public class Video_id_Model {

   private String title1;
    private String sub_title1;
    private String sub_playlist1;
   private String Video_id;
    private String VideoTitle;
   private String Video_Counter;
   private String quiz;

    public Video_id_Model() {
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public String getVideo_Counter() {
        return Video_Counter;
    }

    public void setVideo_Counter(String video_Counter) {
        Video_Counter = video_Counter;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getSub_title1() {
        return sub_title1;
    }

    public void setSub_title1(String sub_title1) {
        this.sub_title1 = sub_title1;
    }

    public String getSub_playlist1() {
        return sub_playlist1;
    }

    public void setSub_playlist1(String sub_playlist1) {
        this.sub_playlist1 = sub_playlist1;
    }

    public String getVideo_id() {
        return Video_id;
    }

    public void setVideo_id(String video_id) {
        Video_id = video_id;
    }

    public String getVideoTitle() {
        return VideoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        VideoTitle = videoTitle;
    }
}
