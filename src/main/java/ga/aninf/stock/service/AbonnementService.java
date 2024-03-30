package ga.aninf.stock.service;

import ga.aninf.stock.domain.Abonnement;
import ga.aninf.stock.repository.AbonnementRepository;
import ga.aninf.stock.service.dto.AbonnementDTO;
import ga.aninf.stock.service.mapper.AbonnementMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.Abonnement}.
 */
@Service
@Transactional
public class AbonnementService {

    private final Logger log = LoggerFactory.getLogger(AbonnementService.class);

    private final AbonnementRepository abonnementRepository;

    private final AbonnementMapper abonnementMapper;

    public AbonnementService(AbonnementRepository abonnementRepository, AbonnementMapper abonnementMapper) {
        this.abonnementRepository = abonnementRepository;
        this.abonnementMapper = abonnementMapper;
    }

    /**
     * Save a abonnement.
     *
     * @param abonnementDTO the entity to save.
     * @return the persisted entity.
     */
    public AbonnementDTO save(AbonnementDTO abonnementDTO) {
        log.debug("Request to save Abonnement : {}", abonnementDTO);
        Abonnement abonnement = abonnementMapper.toEntity(abonnementDTO);
        abonnement = abonnementRepository.save(abonnement);
        return abonnementMapper.toDto(abonnement);
    }

    /**
     * Update a abonnement.
     *
     * @param abonnementDTO the entity to save.
     * @return the persisted entity.
     */
    public AbonnementDTO update(AbonnementDTO abonnementDTO) {
        log.debug("Request to update Abonnement : {}", abonnementDTO);
        Abonnement abonnement = abonnementMapper.toEntity(abonnementDTO);
        abonnement = abonnementRepository.save(abonnement);
        return abonnementMapper.toDto(abonnement);
    }

    /**
     * Partially update a abonnement.
     *
     * @param abonnementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AbonnementDTO> partialUpdate(AbonnementDTO abonnementDTO) {
        log.debug("Request to partially update Abonnement : {}", abonnementDTO);

        return abonnementRepository
            .findById(abonnementDTO.getId())
            .map(existingAbonnement -> {
                abonnementMapper.partialUpdate(existingAbonnement, abonnementDTO);

                return existingAbonnement;
            })
            .map(abonnementRepository::save)
            .map(abonnementMapper::toDto);
    }

    /**
     * Get all the abonnements with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AbonnementDTO> findAllWithEagerRelationships(Pageable pageable) {
        return abonnementRepository.findAllWithEagerRelationships(pageable).map(abonnementMapper::toDto);
    }

    /**
     * Get one abonnement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AbonnementDTO> findOne(UUID id) {
        log.debug("Request to get Abonnement : {}", id);
        return abonnementRepository.findOneWithEagerRelationships(id).map(abonnementMapper::toDto);
    }

    /**
     * Delete the abonnement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Abonnement : {}", id);
        abonnementRepository.deleteById(id);
    }
}
