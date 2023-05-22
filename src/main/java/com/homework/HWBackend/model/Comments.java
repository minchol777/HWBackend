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
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
