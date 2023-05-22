package com.homework.HWBackend.model;

import javax.persistence.*; // 대충 쿼리없이 db 다루는거

@Entity
@Table(name = "Posts") // DB의 테이블 이름과 맞춰줍니다
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //pk 라고 명시함
    @Column(name = "post_id")
    private int id;

    @Column(name = "title") // 테이블 명
    private String title; //변수명은 마음대로

    @Column(name = "main")
    private String main;

    //fk 들
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Boards board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    //getter setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Boards getBoard() {
        return board;
    }

    public void setBoard(Boards board) {
        this.board = board;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}