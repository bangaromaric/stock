package ga.aninf.stock.service;

import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.repository.EntrepotRepository;
import ga.aninf.stock.service.dto.EntrepotDTO;
import ga.aninf.stock.service.mapper.EntrepotMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.Entrepot}.
 */
@Service
@Transactional
public class EntrepotService {

    private final Logger log = LoggerFactory.getLogger(EntrepotService.class);

    private final EntrepotRepository entrepotRepository;

    private final EntrepotMapper entrepotMapper;

    public EntrepotService(EntrepotRepository entrepotRepository, EntrepotMapper entrepotMapper) {
        this.entrepotRepository = entrepotRepository;
        this.entrepotMapper = entrepotMapper;
    }

    /**
     * Save a entrepot.
     *
     * @param entrepotDTO the entity to save.
     * @return the persisted entity.
     */
    public EntrepotDTO save(EntrepotDTO entrepotDTO) {
        log.debug("Request to save Entrepot : {}", entrepotDTO);
        Entrepot entrepot = entrepotMapper.toEntity(entrepotDTO);
        entrepot = entrepotRepository.save(entrepot);
        return entrepotMapper.toDto(entrepot);
    }

    /**
     * Update a entrepot.
     *
     * @param entrepotDTO the entity to save.
     * @return the persisted entity.
     */
    public EntrepotDTO update(EntrepotDTO entrepotDTO) {
        log.debug("Request to update Entrepot : {}", entrepotDTO);
        Entrepot entrepot = entrepotMapper.toEntity(entrepotDTO);
        entrepot = entrepotRepository.save(entrepot);
        return entrepotMapper.toDto(entrepot);
    }

    /**
     * Partially update a entrepot.
     *
     * @param entrepotDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntrepotDTO> partialUpdate(EntrepotDTO entrepotDTO) {
        log.debug("Request to partially update Entrepot : {}", entrepotDTO);

        return entrepotRepository
            .findById(entrepotDTO.getId())
            .map(existingEntrepot -> {
                entrepotMapper.partialUpdate(existingEntrepot, entrepotDTO);

                return existingEntrepot;
            })
            .map(entrepotRepository::save)
            .map(entrepotMapper::toDto);
    }

    /**
     * Get all the entrepots with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EntrepotDTO> findAllWithEagerRelationships(Pageable pageable) {
        return entrepotRepository.findAllWithEagerRelationships(pageable).map(entrepotMapper::toDto);
    }

    /**
     * Get one entrepot by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntrepotDTO> findOne(UUID id) {
        log.debug("Request to get Entrepot : {}", id);
        return entrepotRepository.findOneWithEagerRelationships(id).map(entrepotMapper::toDto);
    }

    /**
     * Delete the entrepot by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Entrepot : {}", id);
        entrepotRepository.deleteById(id);
    }
}
