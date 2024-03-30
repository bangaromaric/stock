package ga.aninf.stock.service;

import ga.aninf.stock.domain.Employe;
import ga.aninf.stock.repository.EmployeRepository;
import ga.aninf.stock.service.dto.EmployeDTO;
import ga.aninf.stock.service.mapper.EmployeMapper;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.Employe}.
 */
@Service
@Transactional
public class EmployeService {

    private final Logger log = LoggerFactory.getLogger(EmployeService.class);

    private final EmployeRepository employeRepository;

    private final EmployeMapper employeMapper;

    public EmployeService(EmployeRepository employeRepository, EmployeMapper employeMapper) {
        this.employeRepository = employeRepository;
        this.employeMapper = employeMapper;
    }

    /**
     * Save a employe.
     *
     * @param employeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeDTO save(EmployeDTO employeDTO) {
        log.debug("Request to save Employe : {}", employeDTO);
        Employe employe = employeMapper.toEntity(employeDTO);
        employe = employeRepository.save(employe);
        return employeMapper.toDto(employe);
    }

    /**
     * Update a employe.
     *
     * @param employeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeDTO update(EmployeDTO employeDTO) {
        log.debug("Request to update Employe : {}", employeDTO);
        Employe employe = employeMapper.toEntity(employeDTO);
        employe = employeRepository.save(employe);
        return employeMapper.toDto(employe);
    }

    /**
     * Partially update a employe.
     *
     * @param employeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployeDTO> partialUpdate(EmployeDTO employeDTO) {
        log.debug("Request to partially update Employe : {}", employeDTO);

        return employeRepository
            .findById(employeDTO.getId())
            .map(existingEmploye -> {
                employeMapper.partialUpdate(existingEmploye, employeDTO);

                return existingEmploye;
            })
            .map(employeRepository::save)
            .map(employeMapper::toDto);
    }

    /**
     * Get all the employes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmployeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return employeRepository.findAllWithEagerRelationships(pageable).map(employeMapper::toDto);
    }

    /**
     * Get one employe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployeDTO> findOne(UUID id) {
        log.debug("Request to get Employe : {}", id);
        return employeRepository.findOneWithEagerRelationships(id).map(employeMapper::toDto);
    }

    /**
     * Delete the employe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Employe : {}", id);
        employeRepository.deleteById(id);
    }
}
