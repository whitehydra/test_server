package com.denis.test.server.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "tr_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
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


    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_faculty")
    private FacultyEntity faculty;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_group")
    private GroupEntity group;


    @Column(name = "phone")
    private String phone;

    @Column(name = "mail")
    private String mail;

    @Column(name = "info")
    private String info;

    @JsonIgnore
    @Column(name = "token")
    private String token;

    @JsonIgnore
    @Column(name = "pin")
    private String pin;

    @Column(name = "avatar")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String avatar;


    public UserEntity(){ }


    public UserEntity(String username, String name, String password, String level){
        this.username = username;
        this.name = name;
        this.password = password;
        this.level = level;
    }

    public static UserEntity CreateObjectFromMap (Map<String, Object> obj){
        if(obj.get("username")!=null&&obj.get("name")!=null&&obj.get("password")!=null&&
                obj.get("level")!=null&&obj.get("id_faculty")!=null&&obj.get("id_group")!=null)
        {
            return new UserEntity(obj.get("username").toString(),obj.get("name").toString(),
                    obj.get("password").toString(),obj.get("level").toString());
        }
        return null;
    }







    /*SETTERS*/

    public void setId_usr(int id_usr) {
        this.id_usr = id_usr;
    }
    public void setUsername(String username) { this.username = username; }
    public void setName(String name) {this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setLevel(String level) { this.level = level; }
    public void setFaculty(FacultyEntity faculty) { this.faculty = faculty; }
    public void setGroup(GroupEntity group) { this.group = group; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setMail(String mail) { this.mail = mail; }
    public void setInfo(String info) { this.info = info; }
    public void setToken(String token) { this.token = token; }
    public void setPin(String pin) { this.pin = pin; }
    public void setAvatar(String avatar) { this.avatar = avatar; }




    /*GETTERS*/

    public int getId_usr() { return id_usr; }
    public String getUsername() { return username; }
    public String getName() {return name; }
    public String getPassword() { return password; }
    public String getLevel() { return level; }
    public FacultyEntity getFaculty() { return faculty; }
    public GroupEntity getGroup() { return group; }
    public String getPhone() { return phone; }
    public String getMail() {return mail; }
    public String getInfo() {return info; }
    public String getToken() { return token; }
    public String getPin() { return pin; }
    public String getAvatar() { return avatar; }
}
