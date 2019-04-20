package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_portfolio_criterion")
public class PortfolioCriterionEntity {
    @Id
    @Column(name = "id_criterion")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int criterionID;

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

    public void setCriterionID(int criterionID) { this.criterionID = criterionID; }
    public void setName_criterion(String name_criterion) { this.name_criterion = name_criterion; }
    public void setSort_criterion(int sort_criterion) { this.sort_criterion = sort_criterion; }

    /*GETTERS*/

    public int getCriterionID() { return criterionID; }
    public String getName_criterion() { return name_criterion; }
    public int getSort_criterion() { return sort_criterion; }
}
