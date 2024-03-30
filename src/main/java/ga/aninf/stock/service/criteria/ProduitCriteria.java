package ga.aninf.stock.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ga.aninf.stock.domain.Produit} entity. This class is used
 * in {@link ga.aninf.stock.web.rest.ProduitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter libelle;

    private StringFilter slug;

    private BigDecimalFilter prixUnitaire;

    private LocalDateFilter dateExpiration;

    private InstantFilter deleteAt;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private UUIDFilter categorieId;

    private UUIDFilter mouvementStockId;

    private UUIDFilter stockId;

    private UUIDFilter inventaireId;

    private UUIDFilter venteId;

    private Boolean distinct;

    public ProduitCriteria() {}

    public ProduitCriteria(ProduitCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.libelle = other.optionalLibelle().map(StringFilter::copy).orElse(null);
        this.slug = other.optionalSlug().map(StringFilter::copy).orElse(null);
        this.prixUnitaire = other.optionalPrixUnitaire().map(BigDecimalFilter::copy).orElse(null);
        this.dateExpiration = other.optionalDateExpiration().map(LocalDateFilter::copy).orElse(null);
        this.deleteAt = other.optionalDeleteAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.categorieId = other.optionalCategorieId().map(UUIDFilter::copy).orElse(null);
        this.mouvementStockId = other.optionalMouvementStockId().map(UUIDFilter::copy).orElse(null);
        this.stockId = other.optionalStockId().map(UUIDFilter::copy).orElse(null);
        this.inventaireId = other.optionalInventaireId().map(UUIDFilter::copy).orElse(null);
        this.venteId = other.optionalVenteId().map(UUIDFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ProduitCriteria copy() {
        return new ProduitCriteria(this);
    }

    public UUIDFilter getId() {
        return id;
    }

    public Optional<UUIDFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public UUIDFilter id() {
        if (id == null) {
            setId(new UUIDFilter());
        }
        return id;
    }

    public void setId(UUIDFilter id) {
        this.id = id;
    }

    public StringFilter getLibelle() {
        return libelle;
    }

    public Optional<StringFilter> optionalLibelle() {
        return Optional.ofNullable(libelle);
    }

    public StringFilter libelle() {
        if (libelle == null) {
            setLibelle(new StringFilter());
        }
        return libelle;
    }

    public void setLibelle(StringFilter libelle) {
        this.libelle = libelle;
    }

    public StringFilter getSlug() {
        return slug;
    }

    public Optional<StringFilter> optionalSlug() {
        return Optional.ofNullable(slug);
    }

    public StringFilter slug() {
        if (slug == null) {
            setSlug(new StringFilter());
        }
        return slug;
    }

    public void setSlug(StringFilter slug) {
        this.slug = slug;
    }

    public BigDecimalFilter getPrixUnitaire() {
        return prixUnitaire;
    }

    public Optional<BigDecimalFilter> optionalPrixUnitaire() {
        return Optional.ofNullable(prixUnitaire);
    }

    public BigDecimalFilter prixUnitaire() {
        if (prixUnitaire == null) {
            setPrixUnitaire(new BigDecimalFilter());
        }
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimalFilter prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public LocalDateFilter getDateExpiration() {
        return dateExpiration;
    }

    public Optional<LocalDateFilter> optionalDateExpiration() {
        return Optional.ofNullable(dateExpiration);
    }

    public LocalDateFilter dateExpiration() {
        if (dateExpiration == null) {
            setDateExpiration(new LocalDateFilter());
        }
        return dateExpiration;
    }

    public void setDateExpiration(LocalDateFilter dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public InstantFilter getDeleteAt() {
        return deleteAt;
    }

    public Optional<InstantFilter> optionalDeleteAt() {
        return Optional.ofNullable(deleteAt);
    }

    public InstantFilter deleteAt() {
        if (deleteAt == null) {
            setDeleteAt(new InstantFilter());
        }
        return deleteAt;
    }

    public void setDeleteAt(InstantFilter deleteAt) {
        this.deleteAt = deleteAt;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public Optional<StringFilter> optionalCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            setCreatedBy(new StringFilter());
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Optional<StringFilter> optionalLastModifiedBy() {
        return Optional.ofNullable(lastModifiedBy);
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            setLastModifiedBy(new StringFilter());
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Optional<InstantFilter> optionalLastModifiedDate() {
        return Optional.ofNullable(lastModifiedDate);
    }

    public InstantFilter lastModifiedDate() {
        if (lastModifiedDate == null) {
            setLastModifiedDate(new InstantFilter());
        }
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UUIDFilter getCategorieId() {
        return categorieId;
    }

    public Optional<UUIDFilter> optionalCategorieId() {
        return Optional.ofNullable(categorieId);
    }

    public UUIDFilter categorieId() {
        if (categorieId == null) {
            setCategorieId(new UUIDFilter());
        }
        return categorieId;
    }

    public void setCategorieId(UUIDFilter categorieId) {
        this.categorieId = categorieId;
    }

    public UUIDFilter getMouvementStockId() {
        return mouvementStockId;
    }

    public Optional<UUIDFilter> optionalMouvementStockId() {
        return Optional.ofNullable(mouvementStockId);
    }

    public UUIDFilter mouvementStockId() {
        if (mouvementStockId == null) {
            setMouvementStockId(new UUIDFilter());
        }
        return mouvementStockId;
    }

    public void setMouvementStockId(UUIDFilter mouvementStockId) {
        this.mouvementStockId = mouvementStockId;
    }

    public UUIDFilter getStockId() {
        return stockId;
    }

    public Optional<UUIDFilter> optionalStockId() {
        return Optional.ofNullable(stockId);
    }

    public UUIDFilter stockId() {
        if (stockId == null) {
            setStockId(new UUIDFilter());
        }
        return stockId;
    }

    public void setStockId(UUIDFilter stockId) {
        this.stockId = stockId;
    }

    public UUIDFilter getInventaireId() {
        return inventaireId;
    }

    public Optional<UUIDFilter> optionalInventaireId() {
        return Optional.ofNullable(inventaireId);
    }

    public UUIDFilter inventaireId() {
        if (inventaireId == null) {
            setInventaireId(new UUIDFilter());
        }
        return inventaireId;
    }

    public void setInventaireId(UUIDFilter inventaireId) {
        this.inventaireId = inventaireId;
    }

    public UUIDFilter getVenteId() {
        return venteId;
    }

    public Optional<UUIDFilter> optionalVenteId() {
        return Optional.ofNullable(venteId);
    }

    public UUIDFilter venteId() {
        if (venteId == null) {
            setVenteId(new UUIDFilter());
        }
        return venteId;
    }

    public void setVenteId(UUIDFilter venteId) {
        this.venteId = venteId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProduitCriteria that = (ProduitCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(slug, that.slug) &&
            Objects.equals(prixUnitaire, that.prixUnitaire) &&
            Objects.equals(dateExpiration, that.dateExpiration) &&
            Objects.equals(deleteAt, that.deleteAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(categorieId, that.categorieId) &&
            Objects.equals(mouvementStockId, that.mouvementStockId) &&
            Objects.equals(stockId, that.stockId) &&
            Objects.equals(inventaireId, that.inventaireId) &&
            Objects.equals(venteId, that.venteId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            libelle,
            slug,
            prixUnitaire,
            dateExpiration,
            deleteAt,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            categorieId,
            mouvementStockId,
            stockId,
            inventaireId,
            venteId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLibelle().map(f -> "libelle=" + f + ", ").orElse("") +
            optionalSlug().map(f -> "slug=" + f + ", ").orElse("") +
            optionalPrixUnitaire().map(f -> "prixUnitaire=" + f + ", ").orElse("") +
            optionalDateExpiration().map(f -> "dateExpiration=" + f + ", ").orElse("") +
            optionalDeleteAt().map(f -> "deleteAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalCategorieId().map(f -> "categorieId=" + f + ", ").orElse("") +
            optionalMouvementStockId().map(f -> "mouvementStockId=" + f + ", ").orElse("") +
            optionalStockId().map(f -> "stockId=" + f + ", ").orElse("") +
            optionalInventaireId().map(f -> "inventaireId=" + f + ", ").orElse("") +
            optionalVenteId().map(f -> "venteId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
