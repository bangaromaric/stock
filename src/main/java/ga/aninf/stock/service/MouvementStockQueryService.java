package ga.aninf.stock.service;

import ga.aninf.stock.domain.*; // for static metamodels
import ga.aninf.stock.domain.MouvementStock;
import ga.aninf.stock.repository.MouvementStockRepository;
import ga.aninf.stock.service.criteria.MouvementStockCriteria;
import ga.aninf.stock.service.dto.MouvementStockDTO;
import ga.aninf.stock.service.mapper.MouvementStockMapper;
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
 * Service for executing complex queries for {@link MouvementStock} entities in the database.
 * The main input is a {@link MouvementStockCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link MouvementStockDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MouvementStockQueryService extends QueryService<MouvementStock> {

    private final Logger log = LoggerFactory.getLogger(MouvementStockQueryService.class);

    private final MouvementStockRepository mouvementStockRepository;

    private final MouvementStockMapper mouvementStockMapper;

    public MouvementStockQueryService(MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
    }

    /**
     * Return a {@link Page} of {@link MouvementStockDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MouvementStockDTO> findByCriteria(MouvementStockCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MouvementStock> specification = createSpecification(criteria);
        return mouvementStockRepository.findAll(specification, page).map(mouvementStockMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MouvementStockCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MouvementStock> specification = createSpecification(criteria);
        return mouvementStockRepository.count(specification);
    }

    /**
     * Function to convert {@link MouvementStockCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MouvementStock> createSpecification(MouvementStockCriteria criteria) {
        Specification<MouvementStock> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), MouvementStock_.id));
            }
            if (criteria.getQuantite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantite(), MouvementStock_.quantite));
            }
            if (criteria.getTypeMouvement() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeMouvement(), MouvementStock_.typeMouvement));
            }
            if (criteria.getTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionDate(), MouvementStock_.transactionDate));
            }
            if (criteria.getDeleteAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeleteAt(), MouvementStock_.deleteAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MouvementStock_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), MouvementStock_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MouvementStock_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLastModifiedDate(), MouvementStock_.lastModifiedDate)
                );
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProduitId(), root -> root.join(MouvementStock_.produit, JoinType.LEFT).get(Produit_.id))
                );
            }
            if (criteria.getEntrepotId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEntrepotId(),
                        root -> root.join(MouvementStock_.entrepot, JoinType.LEFT).get(Entrepot_.id)
                    )
                );
            }
        }
        return specification;
    }
}
