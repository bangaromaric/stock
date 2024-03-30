package ga.aninf.stock.service;

import ga.aninf.stock.domain.*; // for static metamodels
import ga.aninf.stock.domain.Abonnement;
import ga.aninf.stock.repository.AbonnementRepository;
import ga.aninf.stock.service.criteria.AbonnementCriteria;
import ga.aninf.stock.service.dto.AbonnementDTO;
import ga.aninf.stock.service.mapper.AbonnementMapper;
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
 * Service for executing complex queries for {@link Abonnement} entities in the database.
 * The main input is a {@link AbonnementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AbonnementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AbonnementQueryService extends QueryService<Abonnement> {

    private final Logger log = LoggerFactory.getLogger(AbonnementQueryService.class);

    private final AbonnementRepository abonnementRepository;

    private final AbonnementMapper abonnementMapper;

    public AbonnementQueryService(AbonnementRepository abonnementRepository, AbonnementMapper abonnementMapper) {
        this.abonnementRepository = abonnementRepository;
        this.abonnementMapper = abonnementMapper;
    }

    /**
     * Return a {@link Page} of {@link AbonnementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AbonnementDTO> findByCriteria(AbonnementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Abonnement> specification = createSpecification(criteria);
        return abonnementRepository.findAll(specification, page).map(abonnementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AbonnementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Abonnement> specification = createSpecification(criteria);
        return abonnementRepository.count(specification);
    }

    /**
     * Function to convert {@link AbonnementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Abonnement> createSpecification(AbonnementCriteria criteria) {
        Specification<Abonnement> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Abonnement_.id));
            }
            if (criteria.getDateDebut() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDebut(), Abonnement_.dateDebut));
            }
            if (criteria.getDateFin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateFin(), Abonnement_.dateFin));
            }
            if (criteria.getStatutAbonnement() != null) {
                specification = specification.and(buildSpecification(criteria.getStatutAbonnement(), Abonnement_.statutAbonnement));
            }
            if (criteria.getPrix() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrix(), Abonnement_.prix));
            }
            if (criteria.getDeleteAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeleteAt(), Abonnement_.deleteAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Abonnement_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Abonnement_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Abonnement_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Abonnement_.lastModifiedDate));
            }
            if (criteria.getStructureId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getStructureId(),
                        root -> root.join(Abonnement_.structure, JoinType.LEFT).get(Structure_.id)
                    )
                );
            }
            if (criteria.getPlansAbonnementId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPlansAbonnementId(),
                        root -> root.join(Abonnement_.plansAbonnement, JoinType.LEFT).get(PlansAbonnement_.id)
                    )
                );
            }
            if (criteria.getPaiementId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPaiementId(), root -> root.join(Abonnement_.paiement, JoinType.LEFT).get(Paiement_.id))
                );
            }
        }
        return specification;
    }
}
