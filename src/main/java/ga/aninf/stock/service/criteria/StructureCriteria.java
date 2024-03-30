package ga.aninf.stock.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link ga.aninf.stock.domain.Structure} entity. This class is used
 * in {@link ga.aninf.stock.web.rest.StructureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /structures?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StructureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private UUIDFilter id;

    private StringFilter libelle;

    private StringFilter telephone;

    private StringFilter email;

    private StringFilter adresse;

    private InstantFilter deleteAt;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private StringFilter lastModifiedBy;

    private InstantFilter lastModifiedDate;

    private UUIDFilter entrepotId;

    private UUIDFilter employeId;

    private UUIDFilter venteId;

    private UUIDFilter abonnementId;

    private Boolean distinct;

    public StructureCriteria() {}

    public StructureCriteria(StructureCriteria other) {
        this.id = other.optionalId().map(UUIDFilter::copy).orElse(null);
        this.libelle = other.optionalLibelle().map(StringFilter::copy).orElse(null);
        this.telephone = other.optionalTelephone().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.adresse = other.optionalAdresse().map(StringFilter::copy).orElse(null);
        this.deleteAt = other.optionalDeleteAt().map(InstantFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(StringFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.lastModifiedBy = other.optionalLastModifiedBy().map(StringFilter::copy).orElse(null);
        this.lastModifiedDate = other.optionalLastModifiedDate().map(InstantFilter::copy).orElse(null);
        this.entrepotId = other.optionalEntrepotId().map(UUIDFilter::copy).orElse(null);
        this.employeId = other.optionalEmployeId().map(UUIDFilter::copy).orElse(null);
        this.venteId = other.optionalVenteId().map(UUIDFilter::copy).orElse(null);
        this.abonnementId = other.optionalAbonnementId().map(UUIDFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public StructureCriteria copy() {
        return new StructureCriteria(this);
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

    public StringFilter getTelephone() {
        return telephone;
    }

    public Optional<StringFilter> optionalTelephone() {
        return Optional.ofNullable(telephone);
    }

    public StringFilter telephone() {
        if (telephone == null) {
            setTelephone(new StringFilter());
        }
        return telephone;
    }

    public void setTelephone(StringFilter telephone) {
        this.telephone = telephone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public Optional<StringFilter> optionalAdresse() {
        return Optional.ofNullable(adresse);
    }

    public StringFilter adresse() {
        if (adresse == null) {
            setAdresse(new StringFilter());
        }
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
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

    public UUIDFilter getEmployeId() {
        return employeId;
    }

    public Optional<UUIDFilter> optionalEmployeId() {
        return Optional.ofNullable(employeId);
    }

    public UUIDFilter employeId() {
        if (employeId == null) {
            setEmployeId(new UUIDFilter());
        }
        return employeId;
    }

    public void setEmployeId(UUIDFilter employeId) {
        this.employeId = employeId;
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
        final StructureCriteria that = (StructureCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(libelle, that.libelle) &&
            Objects.equals(telephone, that.telephone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(deleteAt, that.deleteAt) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(entrepotId, that.entrepotId) &&
            Objects.equals(employeId, that.employeId) &&
            Objects.equals(venteId, that.venteId) &&
            Objects.equals(abonnementId, that.abonnementId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            libelle,
            telephone,
            email,
            adresse,
            deleteAt,
            createdBy,
            createdDate,
            lastModifiedBy,
            lastModifiedDate,
            entrepotId,
            employeId,
            venteId,
            abonnementId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StructureCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLibelle().map(f -> "libelle=" + f + ", ").orElse("") +
            optionalTelephone().map(f -> "telephone=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalAdresse().map(f -> "adresse=" + f + ", ").orElse("") +
            optionalDeleteAt().map(f -> "deleteAt=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalLastModifiedBy().map(f -> "lastModifiedBy=" + f + ", ").orElse("") +
            optionalLastModifiedDate().map(f -> "lastModifiedDate=" + f + ", ").orElse("") +
            optionalEntrepotId().map(f -> "entrepotId=" + f + ", ").orElse("") +
            optionalEmployeId().map(f -> "employeId=" + f + ", ").orElse("") +
            optionalVenteId().map(f -> "venteId=" + f + ", ").orElse("") +
            optionalAbonnementId().map(f -> "abonnementId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
