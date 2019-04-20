package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_portfolio_criterion")
public class PortfolioCriterionEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int id_criterion;

    @Column(name = "name_criterion", length = 2048)
    private String name_criterion;

    @Column(name = "sort_criterion")
    private int sort_criterion;

    public PortfolioCriterionEntity(){}

    public PortfolioCriterionEntity(String name_criterion, int sort_criterion){
        this.name_criterion = name_criterion;
        this.sort_criterion = sort_criterion;
    }

    /*SETTERS*/

    public void setId_criterion(int id_criterion) { this.id_criterion = id_criterion; }
    public void setName_criterion(String name_criterion) { this.name_criterion = name_criterion; }
    public void setSort_criterion(int sort_criterion) { this.sort_criterion = sort_criterion; }

    /*GETTERS*/

    public int getId_criterion() { return id_criterion; }
    public String getName_criterion() { return name_criterion; }
    public int getSort_criterion() { return sort_criterion; }
}
