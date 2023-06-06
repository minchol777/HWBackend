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

    public Post getPostid() {
        return postid;
    }

    public void setPostid(Post postid) {
        this.postid = postid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }
}
