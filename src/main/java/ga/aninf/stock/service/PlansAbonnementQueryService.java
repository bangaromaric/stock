package ga.aninf.stock.service;

import ga.aninf.stock.domain.*; // for static metamodels
import ga.aninf.stock.domain.PlansAbonnement;
import ga.aninf.stock.repository.PlansAbonnementRepository;
import ga.aninf.stock.service.criteria.PlansAbonnementCriteria;
import ga.aninf.stock.service.dto.PlansAbonnementDTO;
import ga.aninf.stock.service.mapper.PlansAbonnementMapper;
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
 * Service for executing complex queries for {@link PlansAbonnement} entities in the database.
 * The main input is a {@link PlansAbonnementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PlansAbonnementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PlansAbonnementQueryService extends QueryService<PlansAbonnement> {

    private final Logger log = LoggerFactory.getLogger(PlansAbonnementQueryService.class);

    private final PlansAbonnementRepository plansAbonnementRepository;

    private final PlansAbonnementMapper plansAbonnementMapper;

    public PlansAbonnementQueryService(PlansAbonnementRepository plansAbonnementRepository, PlansAbonnementMapper plansAbonnementMapper) {
        this.plansAbonnementRepository = plansAbonnementRepository;
        this.plansAbonnementMapper = plansAbonnementMapper;
    }

    /**
     * Return a {@link Page} of {@link PlansAbonnementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PlansAbonnementDTO> findByCriteria(PlansAbonnementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PlansAbonnement> specification = createSpecification(criteria);
        return plansAbonnementRepository.findAll(specification, page).map(plansAbonnementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PlansAbonnementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PlansAbonnement> specification = createSpecification(criteria);
        return plansAbonnementRepository.count(specification);
    }

    /**
     * Function to convert {@link PlansAbonnementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PlansAbonnement> createSpecification(PlansAbonnementCriteria criteria) {
        Specification<PlansAbonnement> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), PlansAbonnement_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), PlansAbonnement_.libelle));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), PlansAbonnement_.prix));
            }
            if (criteria.getDuree() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuree(), PlansAbonnement_.duree));
            }
            if (criteria.getDeleteAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeleteAt(), PlansAbonnement_.deleteAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), PlansAbonnement_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), PlansAbonnement_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PlansAbonnement_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLastModifiedDate(), PlansAbonnement_.lastModifiedDate)
                );
            }
            if (criteria.getAbonnementId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAbonnementId(),
                        root -> root.join(PlansAbonnement_.abonnements, JoinType.LEFT).get(Abonnement_.id)
                    )
                );
            }
            if (criteria.getPaiementId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPaiementId(),
                        root -> root.join(PlansAbonnement_.paiements, JoinType.LEFT).get(Paiement_.id)
                    )
                );
            }
        }
        return specification;
    }
}
