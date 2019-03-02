package com.denis.test.server.entity;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

@Entity
@Table(name = "tr_portfolio_file")
public class PortfolioFileEntity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment",strategy = "increment")
    private int id_file;

    @Column(name = "file_name", length = 256)
    private String file_name;

    @Column(name = "file_src", length = 256)
    private String file_src;

    @Column(name = "file_type", length = 10)
    private String file_type;

    @Column(name = "id_portfolio")
    private int id_portfolio;

    @Column(name = "id_massage")
    private int id_massage;

    public PortfolioFileEntity(){}

    /*SETTERS*/

    public void setId_file(int id_file) { this.id_file = id_file; }
    public void setFile_name(String file_name) { this.file_name = file_name; }
    public void setFile_src(String file_src) { this.file_src = file_src; }
    public void setFile_type(String file_type) { this.file_type = file_type; }
    public void setId_portfolio(int id_portfolio) { this.id_portfolio = id_portfolio; }
    public void setId_massage(int id_massage) { this.id_massage = id_massage; }

    /*GETTERS*/

    public int getId_file() { return id_file; }
    public String getFile_name() { return file_name; }
    public String getFile_src() { return file_src; }
    public String getFile_type() { return file_type; }
    public int getId_portfolio() { return id_portfolio; }
    public int getId_massage() { return id_massage; }
}
