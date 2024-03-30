package ga.aninf.stock.service.criteria;

import ga.aninf.stock.domain.enumeration.TypeMouvement;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ga.aninf.stock.domain.MouvementStock} entity. This class is used
 * in {@link ga.aninf.stock.web.rest.MouvementStockResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /mouvement-stocks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MouvementStockCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TypeMouvement
     */
    public static class TypeMouvementFilter extends Filter<TypeMouvement> {

        public TypeMouvementFilter() {}

        public TypeMouvementFilter(TypeMouvementFilter filter) {
            super(filter);
        }

        @Override
        public TypeMouvementFilter copy() {
            return new TypeMouvementFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private LongFilter quantite;

    private TypeMouvementFilter typeMouvement;

    private InstantFilter transactionDate;

    private InstantFilter deleteAt;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private UUIDFilter produitId;

    private UUIDFilter entrepotId;

    private Boolean distinct;

    public MouvementStockCriteria() {}

    public MouvementStockCriteria(MouvementStockCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.quantite = other.optionalQuantite().map(LongFilter::copy).orElse(null);
        this.typeMouvement = other.optionalTypeMouvement().map(TypeMouvementFilter::copy).orElse(null);
        this.transactionDate = other.optionalTransactionDate().map(InstantFilter::copy).orElse(null);
        this.deleteAt = other.optionalDeleteAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.produitId = other.optionalProduitId().map(UUIDFilter::copy).orElse(null);
        this.entrepotId = other.optionalEntrepotId().map(UUIDFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public MouvementStockCriteria copy() {
        return new MouvementStockCriteria(this);
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

    public TypeMouvementFilter getTypeMouvement() {
        return typeMouvement;
    }

    public Optional<TypeMouvementFilter> optionalTypeMouvement() {
        return Optional.ofNullable(typeMouvement);
    }

    public TypeMouvementFilter typeMouvement() {
        if (typeMouvement == null) {
            setTypeMouvement(new TypeMouvementFilter());
        }
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvementFilter typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public InstantFilter getTransactionDate() {
        return transactionDate;
    }

    public Optional<InstantFilter> optionalTransactionDate() {
        return Optional.ofNullable(transactionDate);
    }

    public InstantFilter transactionDate() {
        if (transactionDate == null) {
            setTransactionDate(new InstantFilter());
        }
        return transactionDate;
    }

    public void setTransactionDate(InstantFilter transactionDate) {
        this.transactionDate = transactionDate;
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

    public UUIDFilter getEntrepotId() {
        return entrepotId;
    }

    public Optional<UUIDFilter> optionalEntrepotId() {
        return Optional.ofNullable(entrepotId);
    }

    public UUIDFilter entrepotId() {
        if (entrepotId == null) {
            setEntrepotId(new UUIDFilter());
        }
        return entrepotId;
    }

    public void setEntrepotId(UUIDFilter entrepotId) {
        this.entrepotId = entrepotId;
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
        final MouvementStockCriteria that = (MouvementStockCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(quantite, that.quantite) &&
            Objects.equals(typeMouvement, that.typeMouvement) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(deleteAt, that.deleteAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(produitId, that.produitId) &&
            Objects.equals(entrepotId, that.entrepotId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            quantite,
            typeMouvement,
            transactionDate,
            deleteAt,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            produitId,
            entrepotId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementStockCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalQuantite().map(f -> "quantite=" + f + ", ").orElse("") +
            optionalTypeMouvement().map(f -> "typeMouvement=" + f + ", ").orElse("") +
            optionalTransactionDate().map(f -> "transactionDate=" + f + ", ").orElse("") +
            optionalDeleteAt().map(f -> "deleteAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalProduitId().map(f -> "produitId=" + f + ", ").orElse("") +
            optionalEntrepotId().map(f -> "entrepotId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
