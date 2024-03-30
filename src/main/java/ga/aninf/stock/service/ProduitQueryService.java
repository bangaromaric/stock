package ga.aninf.stock.service;

import ga.aninf.stock.domain.*; // for static metamodels
import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.repository.ProduitRepository;
import ga.aninf.stock.service.criteria.ProduitCriteria;
import ga.aninf.stock.service.dto.ProduitDTO;
import ga.aninf.stock.service.mapper.ProduitMapper;
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
 * Service for executing complex queries for {@link Produit} entities in the database.
 * The main input is a {@link ProduitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProduitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProduitQueryService extends QueryService<Produit> {

    private final Logger log = LoggerFactory.getLogger(ProduitQueryService.class);

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    public ProduitQueryService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    /**
     * Return a {@link Page} of {@link ProduitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProduitDTO> findByCriteria(ProduitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.findAll(specification, page).map(produitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProduitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Produit> specification = createSpecification(criteria);
        return produitRepository.count(specification);
    }

    /**
     * Function to convert {@link ProduitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Produit> createSpecification(ProduitCriteria criteria) {
        Specification<Produit> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Produit_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Produit_.libelle));
            }
            if (criteria.getSlug() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlug(), Produit_.slug));
            }
            if (criteria.getPrixUnitaire() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixUnitaire(), Produit_.prixUnitaire));
            }
            if (criteria.getDateExpiration() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateExpiration(), Produit_.dateExpiration));
            }
            if (criteria.getDeleteAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeleteAt(), Produit_.deleteAt));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Produit_.createdBy));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Produit_.createdDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Produit_.lastModifiedBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Produit_.lastModifiedDate));
            }
            if (criteria.getCategorieId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCategorieId(), root -> root.join(Produit_.categorie, JoinType.LEFT).get(Categorie_.id))
                );
            }
            if (criteria.getMouvementStockId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getMouvementStockId(),
                        root -> root.join(Produit_.mouvementStocks, JoinType.LEFT).get(MouvementStock_.id)
                    )
                );
            }
            if (criteria.getStockId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getStockId(), root -> root.join(Produit_.stocks, JoinType.LEFT).get(Stock_.id))
                );
            }
            if (criteria.getInventaireId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getInventaireId(),
                        root -> root.join(Produit_.inventaires, JoinType.LEFT).get(Inventaire_.id)
                    )
                );
            }
            if (criteria.getVenteId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getVenteId(), root -> root.join(Produit_.ventes, JoinType.LEFT).get(Vente_.id))
                );
            }
        }
        return specification;
    }
}
