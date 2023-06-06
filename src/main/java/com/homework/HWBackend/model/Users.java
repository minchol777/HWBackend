package com.homework.HWBackend.model;


import javax.persistence.*;

@Entity
@Table(name = "Users") // DB의 테이블 이름과 맞춰줍니다
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "username") // 테이블 명
    private String username; //변수명은 마음대로

    @Column(name = "password")
    private String password;

    @Column(name = "admin")
    private boolean isAdmin;

    //g s

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
