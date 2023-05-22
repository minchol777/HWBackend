package com.homework.HWBackend.DTO;

public class CommentsDTO {

    private String main;

    private int post_id;

    private int user_id;


    //g s tter


    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
