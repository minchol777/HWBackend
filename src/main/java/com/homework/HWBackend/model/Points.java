package com.homework.HWBackend.model;

import javax.persistence.*;


@Entity
@Table(name = "Points") // DB의 테이블 이름과 맞춰줍니다
public class Points {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private int id;

    @Column(name="point")
    private int point;


    @OneToOne
    @JoinColumn(name ="user_id")
    private Users userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }
}
