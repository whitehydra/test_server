package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_portfolio_category")
public class PortfolioCategoryEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int id_category;

    @Column(name = "name_category", length = 256)
    private String name_category;

    @Column(name = "sort_category")
    private int sort_category;

    public PortfolioCategoryEntity(){}

    /*SETTERS*/

    public void setId_category(int id_category) { this.id_category = id_category; }
    public void setName_category(String name_category) { this.name_category = name_category; }
    public void setSort_category(int sort_category) { this.sort_category = sort_category; }

    /*GETTERS*/

    public int getId_category() { return id_category; }
    public String getName_category() { return name_category; }
    public int getSort_category() { return sort_category; }
}
