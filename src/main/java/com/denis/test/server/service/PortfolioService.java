package com.denis.test.server.service;
import com.denis.test.server.entity.*;

import java.util.List;

public interface PortfolioService {
    PortfolioEntity getPortfolioById(int id);
    PortfolioCategoryEntity getCategoryById(int id);
    PortfolioCriterionEntity getCriterionById(int id);
    PortfolioTypeEntity getTypeById(int id);


    PortfolioEntity savePortfolio(PortfolioEntity portfolioEntity);
    PortfolioCategoryEntity saveCategory(PortfolioCategoryEntity categoryEntity);
    PortfolioCriterionEntity saveCriterion(PortfolioCriterionEntity criterionEntity);
    PortfolioTypeEntity saveType(PortfolioTypeEntity typeEntity);




    PortfolioEntity addFileToPortfolio(PortfolioFileEntity file);
    List<PortfolioEntity> getListByUsername(String username);




    void removePortfolio(int id);
}
