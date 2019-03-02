package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tr_portfolio_massage")
public class PortfolioMassageEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int id_massage;

    @Column(name = "massage", length = 2048)
    private String massage;

    @Column(name = "date_event")
    private Date date_event;

    @Column(name = "id_usr")
    private int id_usr;

    @Column(name = "id_portfolio")
    private int id_portfolio;

    public PortfolioMassageEntity(){}

    /*SETTERS*/

    public void setId_massage(int id_massage) { this.id_massage = id_massage; }
    public void setMassage(String massage) { this.massage = massage; }
    public void setDate_event(Date date_event) { this.date_event = date_event; }
    public void setId_usr(int id_usr) { this.id_usr = id_usr; }
    public void setId_portfolio(int id_portfolio) { this.id_portfolio = id_portfolio; }

    /*GETTERS*/

    public int getId_massage() { return id_massage; }
    public String getMassage() { return massage; }
    public Date getDate_event() { return date_event; }
    public int getId_usr() { return id_usr; }
    public int getId_portfolio() { return id_portfolio; }
}
