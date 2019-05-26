package com.denis.test.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tr_user_faculty")
public class FacultyEntity {
    @Id
    @Column(name = "id_faculty")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int facultyID;

    @Column(name = "short_name", length = 16)
    private String shortName;

    @Column(name = "full_name", length = 64)
    private String fullName;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "faculty_group",
            joinColumns = {@JoinColumn(name = "id_faculty")},
            inverseJoinColumns = {@JoinColumn(name = "id_group")}
    )
    private List<GroupEntity> groups;



    public FacultyEntity(){ }

    public FacultyEntity(String short_name, String full_name){
        this.shortName = short_name;
        this.fullName = full_name;
    }

    /*SETTERS*/

    public void setFacultyID(int facultyID) { this.facultyID = facultyID; }
    public void setShortName(String shortName) { this.shortName = shortName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setGroups(List<GroupEntity> groups) { this.groups = groups; }

    /*GETTERS*/

    public int getFacultyID() { return facultyID; }
    public String getShortName() { return shortName; }
    public String getFullName() { return fullName; }
    public List<GroupEntity> getGroups() { return groups; }
}
