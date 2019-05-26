package com.denis.test.server.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "tr_user_group")
public class GroupEntity {
    @Id
    @Column(name = "id_group")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int groupID;

    @Column(name = "short_name", length = 16)
    private String shortName;

    @Column(name = "full_name", length = 64)
    private String fullName;

    public GroupEntity(){}

    public GroupEntity(String short_name, String full_name){
        this.shortName = short_name;
        this.fullName = full_name;
    }

    /*SETTERS*/

    public void setGroupID(int groupID) { this.groupID = groupID; }
    public void setShortName(String shortName) { this.shortName = shortName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    /*GETTERS*/

    public int getGroupID() { return groupID; }
    public String getShortName() { return shortName; }
    public String getFullName() { return fullName; }
}
