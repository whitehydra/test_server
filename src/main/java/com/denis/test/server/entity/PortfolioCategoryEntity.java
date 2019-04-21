package com.denis.test.server.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tr_portfolio_category")
public class PortfolioCategoryEntity {
    @Id
    @Column(name = "id_category")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int categoryID;

    @Column(name = "name_category", length = 256)
    private String name_category;

    @Column(name = "sort_category")
    private int sort_category;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "category_criterion",
            joinColumns = {@JoinColumn(name = "id_category")},
            inverseJoinColumns = {@JoinColumn(name = "id_criterion")}
    )
    private List<PortfolioCriterionEntity> criteria;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "category_type",
            joinColumns = {@JoinColumn(name = "id_category")},
            inverseJoinColumns = {@JoinColumn(name = "id_type")}
    )
    private List<PortfolioTypeEntity> types;




    public PortfolioCategoryEntity(){}

    public PortfolioCategoryEntity(String name_category, int sort_category){
        this.name_category = name_category;
        this.sort_category = sort_category;
    }


    /*SETTERS*/

    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }
    public void setName_category(String name_category) { this.name_category = name_category; }
    public void setSort_category(int sort_category) { this.sort_category = sort_category; }
    public void setCriteria(List<PortfolioCriterionEntity> criteria) { this.criteria = criteria; }
    public void setTypes(List<PortfolioTypeEntity> types) { this.types = types; }


    /*GETTERS*/

    public int getCategoryID() { return categoryID; }
    public String getName_category() { return name_category; }
    public int getSort_category() { return sort_category; }
    public List<PortfolioCriterionEntity> getCriteria() { return criteria; }
    public List<PortfolioTypeEntity> getTypes() { return types; }
}
