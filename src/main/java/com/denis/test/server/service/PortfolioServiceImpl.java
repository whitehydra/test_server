package com.denis.test.server.service;
import com.denis.test.server.entity.PortfolioEntity;
import com.denis.test.server.entity.PortfolioFileEntity;
import com.denis.test.server.repository.FileRepository;
import com.denis.test.server.repository.PortfolioRepository;
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


    @Override
    public PortfolioEntity addFileToPortfolio(PortfolioFileEntity file){

        PortfolioEntity portfolio = getById(file.getId_portfolio());
        portfolio.getFiles().add(file);
        fileRepository.saveAndFlush(file);
        return portfolioRepository.saveAndFlush(portfolio);
    }


    @Override
    public List<PortfolioEntity> getListByUsername(String username){
        return portfolioRepository.findByAuthor_Username(username);
    }


    @Override
    public PortfolioEntity getById(int id) {
        Optional<PortfolioEntity> portfolioEntity = portfolioRepository.findById(id);
        if (portfolioEntity.isPresent()){
            return portfolioEntity.get();
        }
        else return null;
    }

    @Override
    public PortfolioEntity save(PortfolioEntity portfolioEntity) {

        try {
          //  return portfolioRepository.saveAndFlush(new PortfolioEntity())

            return portfolioRepository.saveAndFlush(portfolioEntity);
        }
        catch (Exception e){
            String  qwe= e.getMessage();
        }

        return portfolioRepository.saveAndFlush(portfolioEntity);
    }

    @Override
    public void remove(int id) { portfolioRepository.deleteById(id); }
}
