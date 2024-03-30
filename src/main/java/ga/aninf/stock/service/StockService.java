package ga.aninf.stock.service;

import ga.aninf.stock.domain.Stock;
import ga.aninf.stock.repository.StockRepository;
import ga.aninf.stock.service.dto.StockDTO;
import ga.aninf.stock.service.mapper.StockMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.Stock}.
 */
@Service
@Transactional
public class StockService {

    private final Logger log = LoggerFactory.getLogger(StockService.class);

    private final StockRepository stockRepository;

    private final StockMapper stockMapper;

    public StockService(StockRepository stockRepository, StockMapper stockMapper) {
        this.stockRepository = stockRepository;
        this.stockMapper = stockMapper;
    }

    /**
     * Save a stock.
     *
     * @param stockDTO the entity to save.
     * @return the persisted entity.
     */
    public StockDTO save(StockDTO stockDTO) {
        log.debug("Request to save Stock : {}", stockDTO);
        Stock stock = stockMapper.toEntity(stockDTO);
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    /**
     * Update a stock.
     *
     * @param stockDTO the entity to save.
     * @return the persisted entity.
     */
    public StockDTO update(StockDTO stockDTO) {
        log.debug("Request to update Stock : {}", stockDTO);
        Stock stock = stockMapper.toEntity(stockDTO);
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    /**
     * Partially update a stock.
     *
     * @param stockDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<StockDTO> partialUpdate(StockDTO stockDTO) {
        log.debug("Request to partially update Stock : {}", stockDTO);

        return stockRepository
            .findById(stockDTO.getId())
            .map(existingStock -> {
                stockMapper.partialUpdate(existingStock, stockDTO);

                return existingStock;
            })
            .map(stockRepository::save)
            .map(stockMapper::toDto);
    }

    /**
     * Get all the stocks with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<StockDTO> findAllWithEagerRelationships(Pageable pageable) {
        return stockRepository.findAllWithEagerRelationships(pageable).map(stockMapper::toDto);
    }

    /**
     * Get one stock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StockDTO> findOne(UUID id) {
        log.debug("Request to get Stock : {}", id);
        return stockRepository.findOneWithEagerRelationships(id).map(stockMapper::toDto);
    }

    /**
     * Delete the stock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Stock : {}", id);
        stockRepository.deleteById(id);
    }
}
