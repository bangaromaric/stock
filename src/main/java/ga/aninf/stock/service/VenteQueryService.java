package ga.aninf.stock.service;

import ga.aninf.stock.domain.*; // for static metamodels
import ga.aninf.stock.domain.Vente;
import ga.aninf.stock.repository.VenteRepository;
import ga.aninf.stock.service.criteria.VenteCriteria;
import ga.aninf.stock.service.dto.VenteDTO;
import ga.aninf.stock.service.mapper.VenteMapper;
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
 * Service for executing complex queries for {@link Vente} entities in the database.
 * The main input is a {@link VenteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link VenteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VenteQueryService extends QueryService<Vente> {

    private final Logger log = LoggerFactory.getLogger(VenteQueryService.class);

    private final VenteRepository venteRepository;

    private final VenteMapper venteMapper;

    public VenteQueryService(VenteRepository venteRepository, VenteMapper venteMapper) {
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
    }

    /**
     * Return a {@link Page} of {@link VenteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VenteDTO> findByCriteria(VenteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vente> specification = createSpecification(criteria);
        return venteRepository.findAll(specification, page).map(venteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VenteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vente> specification = createSpecification(criteria);
        return venteRepository.count(specification);
    }

    /**
     * Function to convert {@link VenteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vente> createSpecification(VenteCriteria criteria) {
        Specification<Vente> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Vente_.id));
            }
            if (criteria.getQuantite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantite(), Vente_.quantite));
            }
            if (criteria.getMontant() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMontant(), Vente_.montant));
            }
            if (criteria.getVenteDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVenteDate(), Vente_.venteDate));
            }
            if (criteria.getDeleteAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeleteAt(), Vente_.deleteAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Vente_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Vente_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Vente_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Vente_.lastModifiedDate));
            }
            if (criteria.getProduitId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getProduitId(), root -> root.join(Vente_.produit, JoinType.LEFT).get(Produit_.id))
                );
            }
            if (criteria.getStructureId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStructureId(), root -> root.join(Vente_.structure, JoinType.LEFT).get(Structure_.id))
                );
            }
        }
        return specification;
    }
}
