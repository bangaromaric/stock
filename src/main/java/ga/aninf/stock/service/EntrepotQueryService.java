package ga.aninf.stock.service;

import ga.aninf.stock.domain.*; // for static metamodels
import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.repository.EntrepotRepository;
import ga.aninf.stock.service.criteria.EntrepotCriteria;
import ga.aninf.stock.service.dto.EntrepotDTO;
import ga.aninf.stock.service.mapper.EntrepotMapper;
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
 * Service for executing complex queries for {@link Entrepot} entities in the database.
 * The main input is a {@link EntrepotCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EntrepotDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EntrepotQueryService extends QueryService<Entrepot> {

    private final Logger log = LoggerFactory.getLogger(EntrepotQueryService.class);

    private final EntrepotRepository entrepotRepository;

    private final EntrepotMapper entrepotMapper;

    public EntrepotQueryService(EntrepotRepository entrepotRepository, EntrepotMapper entrepotMapper) {
        this.entrepotRepository = entrepotRepository;
        this.entrepotMapper = entrepotMapper;
    }

    /**
     * Return a {@link Page} of {@link EntrepotDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EntrepotDTO> findByCriteria(EntrepotCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Entrepot> specification = createSpecification(criteria);
        return entrepotRepository.findAll(specification, page).map(entrepotMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EntrepotCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Entrepot> specification = createSpecification(criteria);
        return entrepotRepository.count(specification);
    }

    /**
     * Function to convert {@link EntrepotCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Entrepot> createSpecification(EntrepotCriteria criteria) {
        Specification<Entrepot> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Entrepot_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Entrepot_.libelle));
            }
            if (criteria.getSlug() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlug(), Entrepot_.slug));
            }
            if (criteria.getCapacite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapacite(), Entrepot_.capacite));
            }
            if (criteria.getDeleteAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeleteAt(), Entrepot_.deleteAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Entrepot_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Entrepot_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Entrepot_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Entrepot_.lastModifiedDate));
            }
            if (criteria.getStructureId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStructureId(), root -> root.join(Entrepot_.structure, JoinType.LEFT).get(Structure_.id))
                );
            }
            if (criteria.getMouvementStockId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getMouvementStockId(),
                        root -> root.join(Entrepot_.mouvementStocks, JoinType.LEFT).get(MouvementStock_.id)
                    )
                );
            }
            if (criteria.getStockId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStockId(), root -> root.join(Entrepot_.stocks, JoinType.LEFT).get(Stock_.id))
                );
            }
            if (criteria.getInventaireId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getInventaireId(),
                        root -> root.join(Entrepot_.inventaires, JoinType.LEFT).get(Inventaire_.id)
                    )
                );
            }
        }
        return specification;
    }
}
