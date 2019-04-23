package com.denis.test.server.service;
import com.denis.test.server.entity.*;
import com.denis.test.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CriterionRepository criterionRepository;
    @Autowired
    private TypeRepository typeRepository;


    //****GET BY ID

    @Override
    public PortfolioEntity getPortfolioById(int id) {
        Optional<PortfolioEntity> portfolioEntity = portfolioRepository.findById(id);
        if (portfolioEntity.isPresent()){
            return portfolioEntity.get();
        }
        else return null;
    }
    @Override
    public PortfolioCategoryEntity getCategoryById(int id){
        Optional<PortfolioCategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(categoryEntity.isPresent()){
            return categoryEntity.get();
        }
        else return null;
    }
    @Override
    public PortfolioCriterionEntity getCriterionById(int id){
        Optional<PortfolioCriterionEntity> criterionEntity = criterionRepository.findById(id);
        if(criterionEntity.isPresent()){
            return criterionEntity.get();
        }
        else return null;
    }
    @Override
    public PortfolioTypeEntity getTypeById(int id){
        Optional<PortfolioTypeEntity> typeEntity = typeRepository.findById(id);
        if(typeEntity.isPresent()){
            return typeEntity.get();
        }
        else return null;
    }

    //********************************************************************************


    //****SAVE

    @Override
    public PortfolioEntity savePortfolio(PortfolioEntity portfolioEntity) {
        try {
            return portfolioRepository.saveAndFlush(portfolioEntity);
        }
        catch (Exception e){
            return null;
        }
    }
    @Override
    public PortfolioCategoryEntity saveCategory(PortfolioCategoryEntity categoryEntity){
        try {
            return categoryRepository.saveAndFlush(categoryEntity);
        }
        catch (Exception e){
            return null;
        }
    }
    @Override
    public void saveCategories(List<PortfolioCategoryEntity> categoryEntity){
        for(PortfolioCategoryEntity category:categoryEntity){
            saveCategory(category);
        }
    }


    @Override
    public PortfolioCriterionEntity saveCriterion(PortfolioCriterionEntity criterionEntity){
        try {
            return criterionRepository.saveAndFlush(criterionEntity);
        }
        catch (Exception e){
            return null;
        }
    }
    @Override
    public void saveCriteria(List<PortfolioCriterionEntity> criterionEntity){
        for(PortfolioCriterionEntity criterion:criterionEntity){
            saveCriterion(criterion);
        }
    }
    @Override
    public PortfolioTypeEntity saveType(PortfolioTypeEntity typeEntity){
        try {
            return typeRepository.saveAndFlush(typeEntity);
        }
        catch (Exception e){
            return null;
        }
    }
    @Override
    public void saveTypes(List<PortfolioTypeEntity> typeEntity){
        for(PortfolioTypeEntity type:typeEntity){
            saveType(type);
        }
    }

    //********************************************************************************

    @Override
    public PortfolioCategoryEntity addCriteria(PortfolioCategoryEntity category,List<PortfolioCriterionEntity> criteria){
        category.setCriteria(criteria);
        return categoryRepository.saveAndFlush(category);
    }
    @Override
    public PortfolioCategoryEntity addTypes(PortfolioCategoryEntity category, List<PortfolioTypeEntity> types){
        category.setTypes(types);
        return categoryRepository.saveAndFlush(category);
    }







    @Override
    public PortfolioEntity addFileToPortfolio(PortfolioFileEntity file){

        PortfolioEntity portfolio = getPortfolioById(file.getId_portfolio());
        portfolio.getFiles().add(file);

        try {
            fileRepository.save(file);
        }
        catch (Exception e){
            String qwe = e.getMessage();
        }

        return portfolioRepository.saveAndFlush(portfolio);
    }


    @Override
    public List<PortfolioCategoryEntity> getCategoriesList(){
        return categoryRepository.findAll();
    }

    @Override
    public List<PortfolioCriterionEntity> getCriteriaListByCategoryId(int id){
        PortfolioCategoryEntity category = getCategoryById(id);
        if(category!=null) return getCategoryById(id).getCriteria();
        return null;
    }


    @Override
    public List<PortfolioTypeEntity> getTypesListByCategoryId(int id){
        PortfolioCategoryEntity category = getCategoryById(id);
        if(category!=null) return getCategoryById(id).getTypes();
        return null;
    }



    @Override
    public List<PortfolioEntity> getListByUsername(String username){
        return portfolioRepository.findByAuthor_Username(username);
    }


    @Override
    public Set<PortfolioFileEntity> getFilesByPortfolioId(int id){
        PortfolioEntity portfolio = getPortfolioById(id);
        return portfolio.getFiles();
    }






    @Override
    public void removePortfolio(int id) {
        try {
            portfolioRepository.deleteById(id);
        }
        catch (Exception e){
            String qwe = "qwe";
        }
    }
    @Override
    public void removeFile(PortfolioFileEntity file){
        try {
            fileRepository.delete(file);
        }
        catch (Exception e){
            String qwe = "qwe";
        }


    }
}
