package ga.aninf.stock.service;

import ga.aninf.stock.domain.*; // for static metamodels
import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.repository.StructureRepository;
import ga.aninf.stock.service.criteria.StructureCriteria;
import ga.aninf.stock.service.dto.StructureDTO;
import ga.aninf.stock.service.mapper.StructureMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Structure} entities in the database.
 * The main input is a {@link StructureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link StructureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StructureQueryService extends QueryService<Structure> {

    private final Logger log = LoggerFactory.getLogger(StructureQueryService.class);

    private final StructureRepository structureRepository;

    private final StructureMapper structureMapper;

    public StructureQueryService(StructureRepository structureRepository, StructureMapper structureMapper) {
        this.structureRepository = structureRepository;
        this.structureMapper = structureMapper;
    }

    /**
     * Return a {@link Page} of {@link StructureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StructureDTO> findByCriteria(StructureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Structure> specification = createSpecification(criteria);
        return structureRepository.findAll(specification, page).map(structureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StructureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Structure> specification = createSpecification(criteria);
        return structureRepository.count(specification);
    }

    /**
     * Function to convert {@link StructureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Structure> createSpecification(StructureCriteria criteria) {
        Specification<Structure> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Structure_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Structure_.libelle));
            }
            if (criteria.getTelephone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelephone(), Structure_.telephone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Structure_.email));
            }
            if (criteria.getAdresse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAdresse(), Structure_.adresse));
            }
            if (criteria.getDeleteAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeleteAt(), Structure_.deleteAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Structure_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Structure_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Structure_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Structure_.lastModifiedDate));
            }
            if (criteria.getEntrepotId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEntrepotId(), root -> root.join(Structure_.entrepots, JoinType.LEFT).get(Entrepot_.id))
                );
            }
            if (criteria.getEmployeId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmployeId(), root -> root.join(Structure_.employes, JoinType.LEFT).get(Employe_.id))
                );
            }
            if (criteria.getVenteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getVenteId(), root -> root.join(Structure_.ventes, JoinType.LEFT).get(Vente_.id))
                );
            }
            if (criteria.getAbonnementId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAbonnementId(),
                        root -> root.join(Structure_.abonnements, JoinType.LEFT).get(Abonnement_.id)
                    )
                );
            }
        }
        return specification;
    }
}
