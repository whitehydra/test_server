package com.denis.test.server.service;
import com.denis.test.server.entity.*;

import java.util.List;
import java.util.Set;

public interface PortfolioService {
    PortfolioEntity getPortfolioById(int id);
    PortfolioCategoryEntity getCategoryById(int id);
    PortfolioCriterionEntity getCriterionById(int id);
    PortfolioTypeEntity getTypeById(int id);


    PortfolioEntity savePortfolio(PortfolioEntity portfolioEntity);
    PortfolioCategoryEntity saveCategory(PortfolioCategoryEntity categoryEntity);
    void saveCategories(List<PortfolioCategoryEntity> categoryEntity);
    PortfolioCriterionEntity saveCriterion(PortfolioCriterionEntity criterionEntity);
    void saveCriteria(List<PortfolioCriterionEntity> criterionEntity);
    PortfolioTypeEntity saveType(PortfolioTypeEntity typeEntity);
    void saveTypes(List<PortfolioTypeEntity> typeEntity);



    PortfolioCategoryEntity addCriteria(PortfolioCategoryEntity category, Set<PortfolioCriterionEntity> criteria);
    PortfolioCategoryEntity addTypes(PortfolioCategoryEntity category, Set<PortfolioTypeEntity> types);


    Set<PortfolioCategoryEntity> getCategoriesList();
    Set<PortfolioCriterionEntity> getCriteriaListByCategoryId(int id);
    Set<PortfolioTypeEntity> getTypesListByCategoryId(int id);

    PortfolioEntity addFileToPortfolio(PortfolioFileEntity file);
    List<PortfolioEntity> getListByUsername(String username);




    void removePortfolio(int id);
}
