package com.denis.test.server.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_user")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int id_usr;

    @Column(name = "username", nullable = false, length = 30)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String username;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "password", nullable = false, length = 128)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "level")
    private String level;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "studyGroup")
    private String studyGroup;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mail")
    private String mail;

    @Column(name = "info")
    private String info;

    @JsonIgnore
    @Column(name = "token")
    private String token;

    public UserEntity(){ }

    /*SETTERS*/

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }
    public void setUsername(String username) { this.username = username; }
    public void setName(String name) {this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setLevel(String level) { this.level = level; }
    public void setFaculty(String faculty) {this.faculty = faculty; }
    public void setStudyGroup(String studyGroup) {this.studyGroup = studyGroup; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setMail(String mail) { this.mail = mail; }
    public void setInfo(String info) { this.info = info; }
    public void setToken(String token) { this.token = token; }
    /*GETTERS*/

    public int getId_usr() { return id_usr; }
    public String getUsername() { return username; }
    public String getName() {return name; }
    public String getPassword() { return password; }
    public String getLevel() { return level; }
    public String getFaculty() {return faculty; }
    public String getStudyGroup() { return studyGroup; }
    public String getPhone() { return phone; }
    public String getMail() {return mail; }
    public String getInfo() {return info; }
    public String getToken() { return token; }
}
