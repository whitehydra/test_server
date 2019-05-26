package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_portfolio_type")
public class PortfolioTypeEntity {
    @Id
    @Column(name = "typeID")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int typeID;

    @Column(name = "name_type", length = 128)
    private String name_type;


    public PortfolioTypeEntity(){}

    public PortfolioTypeEntity(String name_type){
        this.name_type = name_type;
    }

    /*SETTERS*/

    public void setTypeID(int typeID) { this.typeID = typeID; }
    public void setName_type(String name_type) { this.name_type = name_type; }
    /*GETTERS*/

    public int getTypeID() { return typeID; }
    public String getName_type() { return name_type; }
}
