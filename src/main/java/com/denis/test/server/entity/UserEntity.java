package com.denis.test.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


//Сущность, отображение модели данных в базе данных
@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private long id;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "level")
    private int level = 0;







    //Обязателен конструктор, геттеры и сеттеры

    public UserEntity(){ }

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }
}
