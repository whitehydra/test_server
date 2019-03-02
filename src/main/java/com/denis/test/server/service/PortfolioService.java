package com.denis.test.server.service;
import com.denis.test.server.entity.PortfolioEntity;

public interface PortfolioService {
    PortfolioEntity getById(int id);
    PortfolioEntity save(PortfolioEntity portfolioEntity);
    void remove(int id);
}
