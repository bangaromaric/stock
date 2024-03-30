package ga.aninf.stock.service;

import ga.aninf.stock.domain.Inventaire;
import ga.aninf.stock.repository.InventaireRepository;
import ga.aninf.stock.service.dto.InventaireDTO;
import ga.aninf.stock.service.mapper.InventaireMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.Inventaire}.
 */
@Service
@Transactional
public class InventaireService {

    private final Logger log = LoggerFactory.getLogger(InventaireService.class);

    private final InventaireRepository inventaireRepository;

    private final InventaireMapper inventaireMapper;

    public InventaireService(InventaireRepository inventaireRepository, InventaireMapper inventaireMapper) {
        this.inventaireRepository = inventaireRepository;
        this.inventaireMapper = inventaireMapper;
    }

    /**
     * Save a inventaire.
     *
     * @param inventaireDTO the entity to save.
     * @return the persisted entity.
     */
    public InventaireDTO save(InventaireDTO inventaireDTO) {
        log.debug("Request to save Inventaire : {}", inventaireDTO);
        Inventaire inventaire = inventaireMapper.toEntity(inventaireDTO);
        inventaire = inventaireRepository.save(inventaire);
        return inventaireMapper.toDto(inventaire);
    }

    /**
     * Update a inventaire.
     *
     * @param inventaireDTO the entity to save.
     * @return the persisted entity.
     */
    public InventaireDTO update(InventaireDTO inventaireDTO) {
        log.debug("Request to update Inventaire : {}", inventaireDTO);
        Inventaire inventaire = inventaireMapper.toEntity(inventaireDTO);
        inventaire = inventaireRepository.save(inventaire);
        return inventaireMapper.toDto(inventaire);
    }

    /**
     * Partially update a inventaire.
     *
     * @param inventaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InventaireDTO> partialUpdate(InventaireDTO inventaireDTO) {
        log.debug("Request to partially update Inventaire : {}", inventaireDTO);

        return inventaireRepository
            .findById(inventaireDTO.getId())
            .map(existingInventaire -> {
                inventaireMapper.partialUpdate(existingInventaire, inventaireDTO);

                return existingInventaire;
            })
            .map(inventaireRepository::save)
            .map(inventaireMapper::toDto);
    }

    /**
     * Get all the inventaires with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<InventaireDTO> findAllWithEagerRelationships(Pageable pageable) {
        return inventaireRepository.findAllWithEagerRelationships(pageable).map(inventaireMapper::toDto);
    }

    /**
     * Get one inventaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventaireDTO> findOne(UUID id) {
        log.debug("Request to get Inventaire : {}", id);
        return inventaireRepository.findOneWithEagerRelationships(id).map(inventaireMapper::toDto);
    }

    /**
     * Delete the inventaire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Inventaire : {}", id);
        inventaireRepository.deleteById(id);
    }
}
