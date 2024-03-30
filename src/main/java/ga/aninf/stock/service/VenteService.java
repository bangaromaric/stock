package ga.aninf.stock.service;

import ga.aninf.stock.domain.Vente;
import ga.aninf.stock.repository.VenteRepository;
import ga.aninf.stock.service.dto.VenteDTO;
import ga.aninf.stock.service.mapper.VenteMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.Vente}.
 */
@Service
@Transactional
public class VenteService {

    private final Logger log = LoggerFactory.getLogger(VenteService.class);

    private final VenteRepository venteRepository;

    private final VenteMapper venteMapper;

    public VenteService(VenteRepository venteRepository, VenteMapper venteMapper) {
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
    }

    /**
     * Save a vente.
     *
     * @param venteDTO the entity to save.
     * @return the persisted entity.
     */
    public VenteDTO save(VenteDTO venteDTO) {
        log.debug("Request to save Vente : {}", venteDTO);
        Vente vente = venteMapper.toEntity(venteDTO);
        vente = venteRepository.save(vente);
        return venteMapper.toDto(vente);
    }

    /**
     * Update a vente.
     *
     * @param venteDTO the entity to save.
     * @return the persisted entity.
     */
    public VenteDTO update(VenteDTO venteDTO) {
        log.debug("Request to update Vente : {}", venteDTO);
        Vente vente = venteMapper.toEntity(venteDTO);
        vente = venteRepository.save(vente);
        return venteMapper.toDto(vente);
    }

    /**
     * Partially update a vente.
     *
     * @param venteDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VenteDTO> partialUpdate(VenteDTO venteDTO) {
        log.debug("Request to partially update Vente : {}", venteDTO);

        return venteRepository
            .findById(venteDTO.getId())
            .map(existingVente -> {
                venteMapper.partialUpdate(existingVente, venteDTO);

                return existingVente;
            })
            .map(venteRepository::save)
            .map(venteMapper::toDto);
    }

    /**
     * Get all the ventes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<VenteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return venteRepository.findAllWithEagerRelationships(pageable).map(venteMapper::toDto);
    }

    /**
     * Get one vente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VenteDTO> findOne(UUID id) {
        log.debug("Request to get Vente : {}", id);
        return venteRepository.findOneWithEagerRelationships(id).map(venteMapper::toDto);
    }

    /**
     * Delete the vente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Vente : {}", id);
        venteRepository.deleteById(id);
    }
}
