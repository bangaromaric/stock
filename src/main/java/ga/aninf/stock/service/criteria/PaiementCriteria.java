package ga.aninf.stock.service.criteria;

import ga.aninf.stock.domain.enumeration.MethodePaiement;
import ga.aninf.stock.domain.enumeration.StatutPaiement;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ga.aninf.stock.domain.Paiement} entity. This class is used
 * in {@link ga.aninf.stock.web.rest.PaiementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /paiements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaiementCriteria implements Serializable, Criteria {

    /**
     * Class for filtering MethodePaiement
     */
    public static class MethodePaiementFilter extends Filter<MethodePaiement> {

        public MethodePaiementFilter() {}

        public MethodePaiementFilter(MethodePaiementFilter filter) {
            super(filter);
        }

        @Override
        public MethodePaiementFilter copy() {
            return new MethodePaiementFilter(this);
        }
    }

    /**
     * Class for filtering StatutPaiement
     */
    public static class StatutPaiementFilter extends Filter<StatutPaiement> {

        public StatutPaiementFilter() {}

        public StatutPaiementFilter(StatutPaiementFilter filter) {
            super(filter);
        }

        @Override
        public StatutPaiementFilter copy() {
            return new StatutPaiementFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private BigDecimalFilter montant;

    private MethodePaiementFilter methodePaiement;

    private StatutPaiementFilter statutPaiement;

    private InstantFilter deleteAt;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private UUIDFilter plansAbonnementId;

    private UUIDFilter abonnementId;

    private Boolean distinct;

    public PaiementCriteria() {}

    public PaiementCriteria(PaiementCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.montant = other.optionalMontant().map(BigDecimalFilter::copy).orElse(null);
        this.methodePaiement = other.optionalMethodePaiement().map(MethodePaiementFilter::copy).orElse(null);
        this.statutPaiement = other.optionalStatutPaiement().map(StatutPaiementFilter::copy).orElse(null);
        this.deleteAt = other.optionalDeleteAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.plansAbonnementId = other.optionalPlansAbonnementId().map(UUIDFilter::copy).orElse(null);
        this.abonnementId = other.optionalAbonnementId().map(UUIDFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PaiementCriteria copy() {
        return new PaiementCriteria(this);
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

    public MethodePaiementFilter getMethodePaiement() {
        return methodePaiement;
    }

    public Optional<MethodePaiementFilter> optionalMethodePaiement() {
        return Optional.ofNullable(methodePaiement);
    }

    public MethodePaiementFilter methodePaiement() {
        if (methodePaiement == null) {
            setMethodePaiement(new MethodePaiementFilter());
        }
        return methodePaiement;
    }

    public void setMethodePaiement(MethodePaiementFilter methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public StatutPaiementFilter getStatutPaiement() {
        return statutPaiement;
    }

    public Optional<StatutPaiementFilter> optionalStatutPaiement() {
        return Optional.ofNullable(statutPaiement);
    }

    public StatutPaiementFilter statutPaiement() {
        if (statutPaiement == null) {
            setStatutPaiement(new StatutPaiementFilter());
        }
        return statutPaiement;
    }

    public void setStatutPaiement(StatutPaiementFilter statutPaiement) {
        this.statutPaiement = statutPaiement;
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
        final PaiementCriteria that = (PaiementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(montant, that.montant) &&
            Objects.equals(methodePaiement, that.methodePaiement) &&
            Objects.equals(statutPaiement, that.statutPaiement) &&
            Objects.equals(deleteAt, that.deleteAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(plansAbonnementId, that.plansAbonnementId) &&
            Objects.equals(abonnementId, that.abonnementId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            montant,
            methodePaiement,
            statutPaiement,
            deleteAt,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            plansAbonnementId,
            abonnementId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalMontant().map(f -> "montant=" + f + ", ").orElse("") +
            optionalMethodePaiement().map(f -> "methodePaiement=" + f + ", ").orElse("") +
            optionalStatutPaiement().map(f -> "statutPaiement=" + f + ", ").orElse("") +
            optionalDeleteAt().map(f -> "deleteAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalPlansAbonnementId().map(f -> "plansAbonnementId=" + f + ", ").orElse("") +
            optionalAbonnementId().map(f -> "abonnementId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
