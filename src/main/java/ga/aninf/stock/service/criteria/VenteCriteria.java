package ga.aninf.stock.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ga.aninf.stock.domain.Vente} entity. This class is used
 * in {@link ga.aninf.stock.web.rest.VenteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ventes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VenteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private LongFilter quantite;

    private BigDecimalFilter montant;

    private InstantFilter venteDate;

    private InstantFilter deleteAt;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private UUIDFilter produitId;

    private UUIDFilter structureId;

    private Boolean distinct;

    public VenteCriteria() {}

    public VenteCriteria(VenteCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.quantite = other.optionalQuantite().map(LongFilter::copy).orElse(null);
        this.montant = other.optionalMontant().map(BigDecimalFilter::copy).orElse(null);
        this.venteDate = other.optionalVenteDate().map(InstantFilter::copy).orElse(null);
        this.deleteAt = other.optionalDeleteAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.produitId = other.optionalProduitId().map(UUIDFilter::copy).orElse(null);
        this.structureId = other.optionalStructureId().map(UUIDFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public VenteCriteria copy() {
        return new VenteCriteria(this);
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

    public LongFilter getQuantite() {
        return quantite;
    }

    public Optional<LongFilter> optionalQuantite() {
        return Optional.ofNullable(quantite);
    }

    public LongFilter quantite() {
        if (quantite == null) {
            setQuantite(new LongFilter());
        }
        return quantite;
    }

    public void setQuantite(LongFilter quantite) {
        this.quantite = quantite;
    }

    public BigDecimalFilter getMontant() {
        return montant;
    }

    public Optional<BigDecimalFilter> optionalMontant() {
        return Optional.ofNullable(montant);
    }

    public BigDecimalFilter montant() {
        if (montant == null) {
            setMontant(new BigDecimalFilter());
        }
        return montant;
    }

    public void setMontant(BigDecimalFilter montant) {
        this.montant = montant;
    }

    public InstantFilter getVenteDate() {
        return venteDate;
    }

    public Optional<InstantFilter> optionalVenteDate() {
        return Optional.ofNullable(venteDate);
    }

    public InstantFilter venteDate() {
        if (venteDate == null) {
            setVenteDate(new InstantFilter());
        }
        return venteDate;
    }

    public void setVenteDate(InstantFilter venteDate) {
        this.venteDate = venteDate;
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

    public UUIDFilter getProduitId() {
        return produitId;
    }

    public Optional<UUIDFilter> optionalProduitId() {
        return Optional.ofNullable(produitId);
    }

    public UUIDFilter produitId() {
        if (produitId == null) {
            setProduitId(new UUIDFilter());
        }
        return produitId;
    }

    public void setProduitId(UUIDFilter produitId) {
        this.produitId = produitId;
    }

    public UUIDFilter getStructureId() {
        return structureId;
    }

    public Optional<UUIDFilter> optionalStructureId() {
        return Optional.ofNullable(structureId);
    }

    public UUIDFilter structureId() {
        if (structureId == null) {
            setStructureId(new UUIDFilter());
        }
        return structureId;
    }

    public void setStructureId(UUIDFilter structureId) {
        this.structureId = structureId;
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
        final VenteCriteria that = (VenteCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantite, that.quantite) &&
            Objects.equals(montant, that.montant) &&
            Objects.equals(venteDate, that.venteDate) &&
            Objects.equals(deleteAt, that.deleteAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(produitId, that.produitId) &&
            Objects.equals(structureId, that.structureId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            quantite,
            montant,
            venteDate,
            deleteAt,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            produitId,
            structureId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VenteCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalQuantite().map(f -> "quantite=" + f + ", ").orElse("") +
            optionalMontant().map(f -> "montant=" + f + ", ").orElse("") +
            optionalVenteDate().map(f -> "venteDate=" + f + ", ").orElse("") +
            optionalDeleteAt().map(f -> "deleteAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalProduitId().map(f -> "produitId=" + f + ", ").orElse("") +
            optionalStructureId().map(f -> "structureId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
