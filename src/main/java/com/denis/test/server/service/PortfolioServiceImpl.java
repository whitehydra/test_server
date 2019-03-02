package com.denis.test.server.service;
import com.denis.test.server.entity.PortfolioEntity;
import com.denis.test.server.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository repository;

    @Override
    public PortfolioEntity getById(int id) {
        Optional<PortfolioEntity> portfolioEntity = repository.findById(id);
        if (portfolioEntity.isPresent()){
            return portfolioEntity.get();
        }
        else return null;
    }

    @Override
    public PortfolioEntity save(PortfolioEntity portfolioEntity) {
        return repository.saveAndFlush(portfolioEntity);
    }

    @Override
    public void remove(int id) { repository.deleteById(id); }
}
