package ga.aninf.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ga.aninf.stock.domain.enumeration.MethodePaiement;
import ga.aninf.stock.domain.enumeration.StatutPaiement;
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
 * A Paiement.
 */
@Entity
@Table(name = "paiement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "montant", precision = 21, scale = 2, nullable = false)
    private BigDecimal montant;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "methode_paiement", nullable = false)
    private MethodePaiement methodePaiement;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "statut_paiement", nullable = false)
    private StatutPaiement statutPaiement;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "abonnements", "paiements" }, allowSetters = true)
    private PlansAbonnement plansAbonnement;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paiement")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "structure", "plansAbonnement", "paiement" }, allowSetters = true)
    private Set<Abonnement> abonnements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Paiement id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return this.montant;
    }

    public Paiement montant(BigDecimal montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public MethodePaiement getMethodePaiement() {
        return this.methodePaiement;
    }

    public Paiement methodePaiement(MethodePaiement methodePaiement) {
        this.setMethodePaiement(methodePaiement);
        return this;
    }

    public void setMethodePaiement(MethodePaiement methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public StatutPaiement getStatutPaiement() {
        return this.statutPaiement;
    }

    public Paiement statutPaiement(StatutPaiement statutPaiement) {
        this.setStatutPaiement(statutPaiement);
        return this;
    }

    public void setStatutPaiement(StatutPaiement statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public Instant getDeleteAt() {
        return this.deleteAt;
    }

    public Paiement deleteAt(Instant deleteAt) {
        this.setDeleteAt(deleteAt);
        return this;
    }

    public void setDeleteAt(Instant deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Paiement createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Paiement createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Paiement lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Paiement lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public PlansAbonnement getPlansAbonnement() {
        return this.plansAbonnement;
    }

    public void setPlansAbonnement(PlansAbonnement plansAbonnement) {
        this.plansAbonnement = plansAbonnement;
    }

    public Paiement plansAbonnement(PlansAbonnement plansAbonnement) {
        this.setPlansAbonnement(plansAbonnement);
        return this;
    }

    public Set<Abonnement> getAbonnements() {
        return this.abonnements;
    }

    public void setAbonnements(Set<Abonnement> abonnements) {
        if (this.abonnements != null) {
            this.abonnements.forEach(i -> i.setPaiement(null));
        }
        if (abonnements != null) {
            abonnements.forEach(i -> i.setPaiement(this));
        }
        this.abonnements = abonnements;
    }

    public Paiement abonnements(Set<Abonnement> abonnements) {
        this.setAbonnements(abonnements);
        return this;
    }

    public Paiement addAbonnement(Abonnement abonnement) {
        this.abonnements.add(abonnement);
        abonnement.setPaiement(this);
        return this;
    }

    public Paiement removeAbonnement(Abonnement abonnement) {
        this.abonnements.remove(abonnement);
        abonnement.setPaiement(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return getId() != null && getId().equals(((Paiement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", montant=" + getMontant() +
            ", methodePaiement='" + getMethodePaiement() + "'" +
            ", statutPaiement='" + getStatutPaiement() + "'" +
            ", deleteAt='" + getDeleteAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
