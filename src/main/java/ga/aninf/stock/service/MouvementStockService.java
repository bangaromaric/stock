package ga.aninf.stock.service;

import ga.aninf.stock.domain.MouvementStock;
import ga.aninf.stock.repository.MouvementStockRepository;
import ga.aninf.stock.service.dto.MouvementStockDTO;
import ga.aninf.stock.service.mapper.MouvementStockMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.MouvementStock}.
 */
@Service
@Transactional
public class MouvementStockService {

    private final Logger log = LoggerFactory.getLogger(MouvementStockService.class);

    private final MouvementStockRepository mouvementStockRepository;

    private final MouvementStockMapper mouvementStockMapper;

    public MouvementStockService(MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
    }

    /**
     * Save a mouvementStock.
     *
     * @param mouvementStockDTO the entity to save.
     * @return the persisted entity.
     */
    public MouvementStockDTO save(MouvementStockDTO mouvementStockDTO) {
        log.debug("Request to save MouvementStock : {}", mouvementStockDTO);
        MouvementStock mouvementStock = mouvementStockMapper.toEntity(mouvementStockDTO);
        mouvementStock = mouvementStockRepository.save(mouvementStock);
        return mouvementStockMapper.toDto(mouvementStock);
    }

    /**
     * Update a mouvementStock.
     *
     * @param mouvementStockDTO the entity to save.
     * @return the persisted entity.
     */
    public MouvementStockDTO update(MouvementStockDTO mouvementStockDTO) {
        log.debug("Request to update MouvementStock : {}", mouvementStockDTO);
        MouvementStock mouvementStock = mouvementStockMapper.toEntity(mouvementStockDTO);
        mouvementStock = mouvementStockRepository.save(mouvementStock);
        return mouvementStockMapper.toDto(mouvementStock);
    }

    /**
     * Partially update a mouvementStock.
     *
     * @param mouvementStockDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MouvementStockDTO> partialUpdate(MouvementStockDTO mouvementStockDTO) {
        log.debug("Request to partially update MouvementStock : {}", mouvementStockDTO);

        return mouvementStockRepository
            .findById(mouvementStockDTO.getId())
            .map(existingMouvementStock -> {
                mouvementStockMapper.partialUpdate(existingMouvementStock, mouvementStockDTO);

                return existingMouvementStock;
            })
            .map(mouvementStockRepository::save)
            .map(mouvementStockMapper::toDto);
    }

    /**
     * Get all the mouvementStocks with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MouvementStockDTO> findAllWithEagerRelationships(Pageable pageable) {
        return mouvementStockRepository.findAllWithEagerRelationships(pageable).map(mouvementStockMapper::toDto);
    }

    /**
     * Get one mouvementStock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MouvementStockDTO> findOne(UUID id) {
        log.debug("Request to get MouvementStock : {}", id);
        return mouvementStockRepository.findOneWithEagerRelationships(id).map(mouvementStockMapper::toDto);
    }

    /**
     * Delete the mouvementStock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete MouvementStock : {}", id);
        mouvementStockRepository.deleteById(id);
    }
}
