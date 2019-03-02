package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_portfolio_type")
public class PortfolioTypeEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int id_type;

    @Column(name = "name_type", length = 128)
    private String name_type;

    @Column(name = "sort_type")
    private int sort_type;

    public PortfolioTypeEntity(){}

    /*SETTERS*/

    public void setId_type(int id_type) { this.id_type = id_type; }
    public void setName_type(String name_type) { this.name_type = name_type; }
    public void setSort_type(int sort_type) { this.sort_type = sort_type; }

    /*GETTERS*/

    public int getId_type() { return id_type; }
    public String getName_type() { return name_type; }
    public int getSort_type() { return sort_type; }
}
