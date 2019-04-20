package com.denis.test.server.service;
import com.denis.test.server.entity.*;
import com.denis.test.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public PortfolioCriterionEntity saveCriterion(PortfolioCriterionEntity criterionEntity){
        try {
            return criterionRepository.saveAndFlush(criterionEntity);
        }
        catch (Exception e){
            return null;
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

    //********************************************************************************




    @Override
    public PortfolioEntity addFileToPortfolio(PortfolioFileEntity file){

        PortfolioEntity portfolio = getPortfolioById(file.getId_portfolio());
        portfolio.getFiles().add(file);
        fileRepository.saveAndFlush(file);
        return portfolioRepository.saveAndFlush(portfolio);
    }


    @Override
    public List<PortfolioEntity> getListByUsername(String username){
        return portfolioRepository.findByAuthor_Username(username);
    }








    @Override
    public void removePortfolio(int id) { portfolioRepository.deleteById(id); }
}
