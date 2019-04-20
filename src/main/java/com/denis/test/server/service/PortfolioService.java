package com.denis.test.server.service;
import com.denis.test.server.entity.PortfolioEntity;
import com.denis.test.server.entity.PortfolioFileEntity;

import java.util.List;

public interface PortfolioService {
    PortfolioEntity getById(int id);
    PortfolioEntity addFileToPortfolio(PortfolioFileEntity file);
    List<PortfolioEntity> getListByUsername(String username);
    PortfolioEntity save(PortfolioEntity portfolioEntity);
    void remove(int id);
}
