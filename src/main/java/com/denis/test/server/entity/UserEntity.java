package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_user")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int id_usr;

    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @Column(name = "password", nullable = false, length = 30)
    private String password;

    @Column(name = "level")
    private int level = 0;

    public UserEntity(){ }

    /*SETTERS*/

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setLevel(int level) { this.level = level; }

    /*GETTERS*/

    public long getId_usr() { return id_usr; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public int getLevel() { return level; }
}
