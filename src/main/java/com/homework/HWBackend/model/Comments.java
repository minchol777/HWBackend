package com.homework.HWBackend.model;

import javax.persistence.*;

@Entity
@Table(name = "Comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 오토 에그리게이션 된다고 명시
    @Column(name = "comments_id") // 이거 없으면 id라는 컬럼을 새로 만든다..
    private int id;

    @Column(name = "main")
    private String main;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userid;


    //g s tter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Post getPost_id() {
        return postid;
    }

    public void setPost_id(Post post_id) {
        this.postid = post_id;
    }

    public Users getUser_id() {
        return userid;
    }

    public void setUser_id(Users user_id) {
        this.userid = user_id;
    }
}
