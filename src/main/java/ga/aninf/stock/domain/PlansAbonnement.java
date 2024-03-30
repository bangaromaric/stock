package ga.aninf.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlansAbonnement.
 */
@Entity
@Table(name = "plans_abonnement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlansAbonnement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "prix", precision = 21, scale = 2, nullable = false)
    private BigDecimal prix;

    @Column(name = "duree")
    private String duree;

    @Lob
    @Column(name = "avantage")
    private String avantage;

    @Column(name = "delete_at")
    private Instant deleteAt;

    @NotNull
    @Size(max = 50)
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Size(max = 50)
    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plansAbonnement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "structure", "plansAbonnement", "paiement" }, allowSetters = true)
    private Set<Abonnement> abonnements = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plansAbonnement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "plansAbonnement", "abonnements" }, allowSetters = true)
    private Set<Paiement> paiements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public PlansAbonnement id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public PlansAbonnement libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public PlansAbonnement description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrix() {
        return this.prix;
    }

    public PlansAbonnement prix(BigDecimal prix) {
        this.setPrix(prix);
        return this;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public String getDuree() {
        return this.duree;
    }

    public PlansAbonnement duree(String duree) {
        this.setDuree(duree);
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getAvantage() {
        return this.avantage;
    }

    public PlansAbonnement avantage(String avantage) {
        this.setAvantage(avantage);
        return this;
    }

    public void setAvantage(String avantage) {
        this.avantage = avantage;
    }

    public Instant getDeleteAt() {
        return this.deleteAt;
    }

    public PlansAbonnement deleteAt(Instant deleteAt) {
        this.setDeleteAt(deleteAt);
        return this;
    }

    public void setDeleteAt(Instant deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public PlansAbonnement createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public PlansAbonnement createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public PlansAbonnement lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public PlansAbonnement lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<Abonnement> getAbonnements() {
        return this.abonnements;
    }

    public void setAbonnements(Set<Abonnement> abonnements) {
        if (this.abonnements != null) {
            this.abonnements.forEach(i -> i.setPlansAbonnement(null));
        }
        if (abonnements != null) {
            abonnements.forEach(i -> i.setPlansAbonnement(this));
        }
        this.abonnements = abonnements;
    }

    public PlansAbonnement abonnements(Set<Abonnement> abonnements) {
        this.setAbonnements(abonnements);
        return this;
    }

    public PlansAbonnement addAbonnement(Abonnement abonnement) {
        this.abonnements.add(abonnement);
        abonnement.setPlansAbonnement(this);
        return this;
    }

    public PlansAbonnement removeAbonnement(Abonnement abonnement) {
        this.abonnements.remove(abonnement);
        abonnement.setPlansAbonnement(null);
        return this;
    }

    public Set<Paiement> getPaiements() {
        return this.paiements;
    }

    public void setPaiements(Set<Paiement> paiements) {
        if (this.paiements != null) {
            this.paiements.forEach(i -> i.setPlansAbonnement(null));
        }
        if (paiements != null) {
            paiements.forEach(i -> i.setPlansAbonnement(this));
        }
        this.paiements = paiements;
    }

    public PlansAbonnement paiements(Set<Paiement> paiements) {
        this.setPaiements(paiements);
        return this;
    }

    public PlansAbonnement addPaiement(Paiement paiement) {
        this.paiements.add(paiement);
        paiement.setPlansAbonnement(this);
        return this;
    }

    public PlansAbonnement removePaiement(Paiement paiement) {
        this.paiements.remove(paiement);
        paiement.setPlansAbonnement(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlansAbonnement)) {
            return false;
        }
        return getId() != null && getId().equals(((PlansAbonnement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlansAbonnement{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", prix=" + getPrix() +
            ", duree='" + getDuree() + "'" +
            ", avantage='" + getAvantage() + "'" +
            ", deleteAt='" + getDeleteAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
