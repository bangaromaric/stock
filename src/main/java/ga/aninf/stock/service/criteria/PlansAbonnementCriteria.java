package ga.aninf.stock.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ga.aninf.stock.domain.PlansAbonnement} entity. This class is used
 * in {@link ga.aninf.stock.web.rest.PlansAbonnementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /plans-abonnements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlansAbonnementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter libelle;

    private BigDecimalFilter prix;

    private StringFilter duree;

    private InstantFilter deleteAt;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private UUIDFilter abonnementId;

    private UUIDFilter paiementId;

    private Boolean distinct;

    public PlansAbonnementCriteria() {}

    public PlansAbonnementCriteria(PlansAbonnementCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.libelle = other.optionalLibelle().map(StringFilter::copy).orElse(null);
        this.prix = other.optionalPrix().map(BigDecimalFilter::copy).orElse(null);
        this.duree = other.optionalDuree().map(StringFilter::copy).orElse(null);
        this.deleteAt = other.optionalDeleteAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.abonnementId = other.optionalAbonnementId().map(UUIDFilter::copy).orElse(null);
        this.paiementId = other.optionalPaiementId().map(UUIDFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PlansAbonnementCriteria copy() {
        return new PlansAbonnementCriteria(this);
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

    public BigDecimalFilter getPrix() {
        return prix;
    }

    public Optional<BigDecimalFilter> optionalPrix() {
        return Optional.ofNullable(prix);
    }

    public BigDecimalFilter prix() {
        if (prix == null) {
            setPrix(new BigDecimalFilter());
        }
        return prix;
    }

    public void setPrix(BigDecimalFilter prix) {
        this.prix = prix;
    }

    public StringFilter getDuree() {
        return duree;
    }

    public Optional<StringFilter> optionalDuree() {
        return Optional.ofNullable(duree);
    }

    public StringFilter duree() {
        if (duree == null) {
            setDuree(new StringFilter());
        }
        return duree;
    }

    public void setDuree(StringFilter duree) {
        this.duree = duree;
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

    public UUIDFilter getAbonnementId() {
        return abonnementId;
    }

    public Optional<UUIDFilter> optionalAbonnementId() {
        return Optional.ofNullable(abonnementId);
    }

    public UUIDFilter abonnementId() {
        if (abonnementId == null) {
            setAbonnementId(new UUIDFilter());
        }
        return abonnementId;
    }

    public void setAbonnementId(UUIDFilter abonnementId) {
        this.abonnementId = abonnementId;
    }

    public UUIDFilter getPaiementId() {
        return paiementId;
    }

    public Optional<UUIDFilter> optionalPaiementId() {
        return Optional.ofNullable(paiementId);
    }

    public UUIDFilter paiementId() {
        if (paiementId == null) {
            setPaiementId(new UUIDFilter());
        }
        return paiementId;
    }

    public void setPaiementId(UUIDFilter paiementId) {
        this.paiementId = paiementId;
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
        final PlansAbonnementCriteria that = (PlansAbonnementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(prix, that.prix) &&
            Objects.equals(duree, that.duree) &&
            Objects.equals(deleteAt, that.deleteAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(abonnementId, that.abonnementId) &&
            Objects.equals(paiementId, that.paiementId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            libelle,
            prix,
            duree,
            deleteAt,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            abonnementId,
            paiementId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlansAbonnementCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLibelle().map(f -> "libelle=" + f + ", ").orElse("") +
            optionalPrix().map(f -> "prix=" + f + ", ").orElse("") +
            optionalDuree().map(f -> "duree=" + f + ", ").orElse("") +
            optionalDeleteAt().map(f -> "deleteAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalAbonnementId().map(f -> "abonnementId=" + f + ", ").orElse("") +
            optionalPaiementId().map(f -> "paiementId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
