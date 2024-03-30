package ga.aninf.stock.service.criteria;

import ga.aninf.stock.domain.enumeration.StatutAbonnement;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ga.aninf.stock.domain.Abonnement} entity. This class is used
 * in {@link ga.aninf.stock.web.rest.AbonnementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /abonnements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AbonnementCriteria implements Serializable, Criteria {

    /**
     * Class for filtering StatutAbonnement
     */
    public static class StatutAbonnementFilter extends Filter<StatutAbonnement> {

        public StatutAbonnementFilter() {}

        public StatutAbonnementFilter(StatutAbonnementFilter filter) {
            super(filter);
        }

        @Override
        public StatutAbonnementFilter copy() {
            return new StatutAbonnementFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private InstantFilter dateDebut;

    private InstantFilter dateFin;

    private StatutAbonnementFilter statutAbonnement;

    private BigDecimalFilter prix;

    private InstantFilter deleteAt;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private UUIDFilter structureId;

    private UUIDFilter plansAbonnementId;

    private UUIDFilter paiementId;

    private Boolean distinct;

    public AbonnementCriteria() {}

    public AbonnementCriteria(AbonnementCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.dateDebut = other.optionalDateDebut().map(InstantFilter::copy).orElse(null);
        this.dateFin = other.optionalDateFin().map(InstantFilter::copy).orElse(null);
        this.statutAbonnement = other.optionalStatutAbonnement().map(StatutAbonnementFilter::copy).orElse(null);
        this.prix = other.optionalPrix().map(BigDecimalFilter::copy).orElse(null);
        this.deleteAt = other.optionalDeleteAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.structureId = other.optionalStructureId().map(UUIDFilter::copy).orElse(null);
        this.plansAbonnementId = other.optionalPlansAbonnementId().map(UUIDFilter::copy).orElse(null);
        this.paiementId = other.optionalPaiementId().map(UUIDFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AbonnementCriteria copy() {
        return new AbonnementCriteria(this);
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

    public InstantFilter getDateDebut() {
        return dateDebut;
    }

    public Optional<InstantFilter> optionalDateDebut() {
        return Optional.ofNullable(dateDebut);
    }

    public InstantFilter dateDebut() {
        if (dateDebut == null) {
            setDateDebut(new InstantFilter());
        }
        return dateDebut;
    }

    public void setDateDebut(InstantFilter dateDebut) {
        this.dateDebut = dateDebut;
    }

    public InstantFilter getDateFin() {
        return dateFin;
    }

    public Optional<InstantFilter> optionalDateFin() {
        return Optional.ofNullable(dateFin);
    }

    public InstantFilter dateFin() {
        if (dateFin == null) {
            setDateFin(new InstantFilter());
        }
        return dateFin;
    }

    public void setDateFin(InstantFilter dateFin) {
        this.dateFin = dateFin;
    }

    public StatutAbonnementFilter getStatutAbonnement() {
        return statutAbonnement;
    }

    public Optional<StatutAbonnementFilter> optionalStatutAbonnement() {
        return Optional.ofNullable(statutAbonnement);
    }

    public StatutAbonnementFilter statutAbonnement() {
        if (statutAbonnement == null) {
            setStatutAbonnement(new StatutAbonnementFilter());
        }
        return statutAbonnement;
    }

    public void setStatutAbonnement(StatutAbonnementFilter statutAbonnement) {
        this.statutAbonnement = statutAbonnement;
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

    public UUIDFilter getPlansAbonnementId() {
        return plansAbonnementId;
    }

    public Optional<UUIDFilter> optionalPlansAbonnementId() {
        return Optional.ofNullable(plansAbonnementId);
    }

    public UUIDFilter plansAbonnementId() {
        if (plansAbonnementId == null) {
            setPlansAbonnementId(new UUIDFilter());
        }
        return plansAbonnementId;
    }

    public void setPlansAbonnementId(UUIDFilter plansAbonnementId) {
        this.plansAbonnementId = plansAbonnementId;
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
        final AbonnementCriteria that = (AbonnementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateDebut, that.dateDebut) &&
            Objects.equals(dateFin, that.dateFin) &&
            Objects.equals(statutAbonnement, that.statutAbonnement) &&
            Objects.equals(prix, that.prix) &&
            Objects.equals(deleteAt, that.deleteAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(structureId, that.structureId) &&
            Objects.equals(plansAbonnementId, that.plansAbonnementId) &&
            Objects.equals(paiementId, that.paiementId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dateDebut,
            dateFin,
            statutAbonnement,
            prix,
            deleteAt,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            structureId,
            plansAbonnementId,
            paiementId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbonnementCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalDateDebut().map(f -> "dateDebut=" + f + ", ").orElse("") +
            optionalDateFin().map(f -> "dateFin=" + f + ", ").orElse("") +
            optionalStatutAbonnement().map(f -> "statutAbonnement=" + f + ", ").orElse("") +
            optionalPrix().map(f -> "prix=" + f + ", ").orElse("") +
            optionalDeleteAt().map(f -> "deleteAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalStructureId().map(f -> "structureId=" + f + ", ").orElse("") +
            optionalPlansAbonnementId().map(f -> "plansAbonnementId=" + f + ", ").orElse("") +
            optionalPaiementId().map(f -> "paiementId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
