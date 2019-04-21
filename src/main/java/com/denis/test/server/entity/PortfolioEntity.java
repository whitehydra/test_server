package com.denis.test.server.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "tr_portfolio")
public class PortfolioEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int id_portfolio;

    @Column(name = "name", length = 1024)
    private String name;

    @Column(name = "date_event", length = 12)
    private String date_event;

    @Column(name = "date_publication", length = 12)
    private String date_publication;




    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_category")
    private PortfolioCategoryEntity category;


    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_criterion")
    private PortfolioCriterionEntity criterion;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_type")
    private PortfolioTypeEntity type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usr")
    private UserEntity author;


    @JsonIgnore
    @OneToMany(fetch =  FetchType.EAGER)
    @JoinColumn(name = "id_file")
    @JoinTable(
            name = "portfolio_file",
            joinColumns = {@JoinColumn(name = "id_portfolio")},
            inverseJoinColumns = {@JoinColumn(name = "id_file")}
    )
    private Set<PortfolioFileEntity> files;



    public PortfolioEntity(){}

    public PortfolioEntity(String name, String date_event, String date_publication) {
        this.name = name;
        this.date_event = date_event;
        this.date_publication = date_publication;
    }


    public static PortfolioEntity CreateObjectFromMap (Map<String, Object> qwe){
        if(qwe.get("name")!=null&&qwe.get("date_event")!=null&&qwe.get("date_publication")!=null&&
                qwe.get("id_category")!=null&&qwe.get("id_criterion")!=null&&qwe.get("id_type")!=null){

            return new PortfolioEntity(qwe.get("name").toString(),qwe.get("date_event").toString(),
                    qwe.get("date_publication").toString());
        }
        return null;
    }



    /*SETTERS*/

    public void setId_portfolio(int id_portfolio) { this.id_portfolio = id_portfolio; }
    public void setName(String name) { this.name = name; }
    public void setDate_event(String date_event) { this.date_event = date_event; }
    public void setDate_publication(String date_publication) { this.date_publication = date_publication; }
    public void setCategory(PortfolioCategoryEntity category) { this.category = category; }
    public void setCriterion(PortfolioCriterionEntity criterion) { this.criterion = criterion; }
    public void setType(PortfolioTypeEntity type) { this.type = type; }
    public void setAuthor(UserEntity author) { this.author = author; }
    public void setFiles(Set<PortfolioFileEntity> files) { this.files = files; }
    /*GETTERS*/

    public int getId_portfolio() { return id_portfolio; }
    public String getName() { return name; }
    public String getDate_event() { return date_event; }
    public String getDate_publication() { return date_publication; }
    public PortfolioCategoryEntity getCategory() { return category; }
    public PortfolioCriterionEntity getCriterion() { return criterion; }
    public PortfolioTypeEntity getType() { return type; }
    public UserEntity getAuthor() { return author; }
    public Set<PortfolioFileEntity> getFiles() { return files; }
}
