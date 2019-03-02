package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_portfolio")
public class PortfolioEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int id_portfolio;

    @Column(name = "name", length = 1024)
    private String name;

    @Column(name = "date_event", length = 10)
    private String date_event;

    @Column(name = "date_publication", length = 10)
    private String date_publication;

    @Column(name = "id_category")
    private int id_category;

    @Column(name = "id_criterion")
    private int id_criterion;

    @Column(name = "id_type")
    private int id_type;

    @Column(name = "id_usr")
    private int id_usr;

    public PortfolioEntity(){}

    /*SETTERS*/

    public void setId_portfolio(int id_portfolio) { this.id_portfolio = id_portfolio; }
    public void setName(String name) { this.name = name; }
    public void setDate_event(String date_event) { this.date_event = date_event; }
    public void setDate_publication(String date_publication) { this.date_publication = date_publication; }
    public void setId_category(int id_category) { this.id_category = id_category; }
    public void setId_criterion(int id_criterion) { this.id_criterion = id_criterion; }
    public void setId_type(int id_type) { this.id_type = id_type; }
    public void setId_usr(int id_usr) { this.id_usr = id_usr; }

    /*GETTERS*/

    public int getId_portfolio() { return id_portfolio; }
    public String getName() { return name; }
    public String getDate_event() { return date_event; }
    public String getDate_publication() { return date_publication; }
    public int getId_category() { return id_category; }
    public int getId_criterion() { return id_criterion; }
    public int getId_type() { return id_type; }
    public int getId_usr() { return id_usr; }
}
