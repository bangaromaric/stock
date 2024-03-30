package ga.aninf.stock.service;

import ga.aninf.stock.domain.*; // for static metamodels
import ga.aninf.stock.domain.Inventaire;
import ga.aninf.stock.repository.InventaireRepository;
import ga.aninf.stock.service.criteria.InventaireCriteria;
import ga.aninf.stock.service.dto.InventaireDTO;
import ga.aninf.stock.service.mapper.InventaireMapper;
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
 * Service for executing complex queries for {@link Inventaire} entities in the database.
 * The main input is a {@link InventaireCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link InventaireDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventaireQueryService extends QueryService<Inventaire> {

    private final Logger log = LoggerFactory.getLogger(InventaireQueryService.class);

    private final InventaireRepository inventaireRepository;

    private final InventaireMapper inventaireMapper;

    public InventaireQueryService(InventaireRepository inventaireRepository, InventaireMapper inventaireMapper) {
        this.inventaireRepository = inventaireRepository;
        this.inventaireMapper = inventaireMapper;
    }

    /**
     * Return a {@link Page} of {@link InventaireDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InventaireDTO> findByCriteria(InventaireCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Inventaire> specification = createSpecification(criteria);
        return inventaireRepository.findAll(specification, page).map(inventaireMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventaireCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Inventaire> specification = createSpecification(criteria);
        return inventaireRepository.count(specification);
    }

    /**
     * Function to convert {@link InventaireCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Inventaire> createSpecification(InventaireCriteria criteria) {
        Specification<Inventaire> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Inventaire_.id));
            }
            if (criteria.getQuantite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantite(), Inventaire_.quantite));
            }
            if (criteria.getInventaireDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInventaireDate(), Inventaire_.inventaireDate));
            }
            if (criteria.getDeleteAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeleteAt(), Inventaire_.deleteAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Inventaire_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Inventaire_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Inventaire_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Inventaire_.lastModifiedDate));
            }
            if (criteria.getEntrepotId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEntrepotId(), root -> root.join(Inventaire_.entrepot, JoinType.LEFT).get(Entrepot_.id))
                );
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProduitId(), root -> root.join(Inventaire_.produit, JoinType.LEFT).get(Produit_.id))
                );
            }
        }
        return specification;
    }
}
