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


    public PortfolioCriterionEntity(){}

    public PortfolioCriterionEntity(String name_criterion){
        this.name_criterion = name_criterion;
    }

    /*SETTERS*/

    public void setCriterionID(int criterionID) { this.criterionID = criterionID; }
    public void setName_criterion(String name_criterion) { this.name_criterion = name_criterion; }

    /*GETTERS*/

    public int getCriterionID() { return criterionID; }
    public String getName_criterion() { return name_criterion; }
}
