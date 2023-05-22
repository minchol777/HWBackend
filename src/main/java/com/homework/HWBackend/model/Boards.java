package com.homework.HWBackend.model;


import javax.persistence.*;

@Entity
@Table(name = "Boards")
public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 오토 에그리게이션 된다고 명시
    @Column(name = "board_id") // 이거 없으면 id라는 컬럼을 새로 만든다..
    private int id;

    @Column(name = "boardname") // 테이블 명
    private String boardname; //변수명은 마음대로

    //getter setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }
}
