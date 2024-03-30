package ga.aninf.stock.service;

import ga.aninf.stock.domain.PlansAbonnement;
import ga.aninf.stock.repository.PlansAbonnementRepository;
import ga.aninf.stock.service.dto.PlansAbonnementDTO;
import ga.aninf.stock.service.mapper.PlansAbonnementMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.PlansAbonnement}.
 */
@Service
@Transactional
public class PlansAbonnementService {

    private final Logger log = LoggerFactory.getLogger(PlansAbonnementService.class);

    private final PlansAbonnementRepository plansAbonnementRepository;

    private final PlansAbonnementMapper plansAbonnementMapper;

    public PlansAbonnementService(PlansAbonnementRepository plansAbonnementRepository, PlansAbonnementMapper plansAbonnementMapper) {
        this.plansAbonnementRepository = plansAbonnementRepository;
        this.plansAbonnementMapper = plansAbonnementMapper;
    }

    /**
     * Save a plansAbonnement.
     *
     * @param plansAbonnementDTO the entity to save.
     * @return the persisted entity.
     */
    public PlansAbonnementDTO save(PlansAbonnementDTO plansAbonnementDTO) {
        log.debug("Request to save PlansAbonnement : {}", plansAbonnementDTO);
        PlansAbonnement plansAbonnement = plansAbonnementMapper.toEntity(plansAbonnementDTO);
        plansAbonnement = plansAbonnementRepository.save(plansAbonnement);
        return plansAbonnementMapper.toDto(plansAbonnement);
    }

    /**
     * Update a plansAbonnement.
     *
     * @param plansAbonnementDTO the entity to save.
     * @return the persisted entity.
     */
    public PlansAbonnementDTO update(PlansAbonnementDTO plansAbonnementDTO) {
        log.debug("Request to update PlansAbonnement : {}", plansAbonnementDTO);
        PlansAbonnement plansAbonnement = plansAbonnementMapper.toEntity(plansAbonnementDTO);
        plansAbonnement = plansAbonnementRepository.save(plansAbonnement);
        return plansAbonnementMapper.toDto(plansAbonnement);
    }

    /**
     * Partially update a plansAbonnement.
     *
     * @param plansAbonnementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlansAbonnementDTO> partialUpdate(PlansAbonnementDTO plansAbonnementDTO) {
        log.debug("Request to partially update PlansAbonnement : {}", plansAbonnementDTO);

        return plansAbonnementRepository
            .findById(plansAbonnementDTO.getId())
            .map(existingPlansAbonnement -> {
                plansAbonnementMapper.partialUpdate(existingPlansAbonnement, plansAbonnementDTO);

                return existingPlansAbonnement;
            })
            .map(plansAbonnementRepository::save)
            .map(plansAbonnementMapper::toDto);
    }

    /**
     * Get one plansAbonnement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlansAbonnementDTO> findOne(UUID id) {
        log.debug("Request to get PlansAbonnement : {}", id);
        return plansAbonnementRepository.findById(id).map(plansAbonnementMapper::toDto);
    }

    /**
     * Delete the plansAbonnement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete PlansAbonnement : {}", id);
        plansAbonnementRepository.deleteById(id);
    }
}
