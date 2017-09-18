package by.kraskovski.pms.service.impl;

import by.kraskovski.pms.domain.model.Stock;
import by.kraskovski.pms.domain.model.Store;
import by.kraskovski.pms.repository.StoreRepository;
import by.kraskovski.pms.service.ImageService;
import by.kraskovski.pms.service.StockService;
import by.kraskovski.pms.service.StoreService;
import by.kraskovski.pms.service.exception.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
@Slf4j
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StockService stockService;
    private final ImageService imageService;

    @Autowired
    public StoreServiceImpl(final StoreRepository storeRepository,
                            final ImageService imageService,
                            final StockService stockService) {
        this.storeRepository = storeRepository;
        this.imageService = imageService;
        this.stockService = stockService;
    }

    @Override
    public Store create(final Store object) {
        return storeRepository.save(object);
    }

    @Override
    public Store find(final String id) {
        return Optional.ofNullable(storeRepository.findOne(id))
                .orElseThrow(() -> new EntityNotFoundException("Store with id: " + id + " not found in db!"));
    }

    @Override
    public Store findByName(final String name) {
        return Optional.ofNullable(storeRepository.findByName(name))
                .orElseThrow(() -> new EntityNotFoundException("Store with name: " + name + " not found in db!"));
    }

    @Override
    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    @Override
    public void addStock(final String storeId, final String stockId) {
        final Store store = find(storeId);
        final Stock stock = stockService.find(stockId);
        if (store.getStockList().contains(stock)) {
            throw new IllegalArgumentException("Store:" + storeId + " already have relation with stock: " + stockId);
        }
        store.getStockList().add(stock);
        storeRepository.save(store);
    }

    @Override
    public void deleteStock(final String storeId, final String stockId) {
        final Store store = find(storeId);
        final Stock stock = stockService.find(stockId);
        if (!store.getStockList().contains(stock)) {
            throw new IllegalArgumentException("Store:" + storeId + " doesn't have relation with stock: " + stockId);
        }
        store.getStockList().remove(stock);
        storeRepository.save(store);
    }

    @Override
    public Store update(final Store object) {
        return storeRepository.save(object);
    }

    @Override
    public void delete(final String id) {
        final Store storeToDelete = storeRepository.findOne(id);
        if (isNotEmpty(storeToDelete.getLogo())) {
            try {
                imageService.delete(storeToDelete.getLogo());
            } catch (FileNotFoundException e) {
                log.warn("Store: {} doesn't have logo. This image id is: {} is not correct!",
                        storeToDelete.getId(), storeToDelete.getLogo());
            }
        }
        storeRepository.delete(storeToDelete);
    }

    @Override
    public void deleteAll() {
        storeRepository.deleteAll();
    }
}
