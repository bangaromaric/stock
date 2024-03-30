package ga.aninf.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Structure.
 */
@Entity
@Table(name = "structure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Structure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "telephone", nullable = false)
    private String telephone;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "adresse")
    private String adresse;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "structure", "mouvementStocks", "stocks", "inventaires" }, allowSetters = true)
    private Set<Entrepot> entrepots = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "internalUser", "structure" }, allowSetters = true)
    private Set<Employe> employes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "structure" }, allowSetters = true)
    private Set<Vente> ventes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "structure")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "structure", "plansAbonnement", "paiement" }, allowSetters = true)
    private Set<Abonnement> abonnements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Structure id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Structure libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Structure telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return this.email;
    }

    public Structure email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Structure adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Instant getDeleteAt() {
        return this.deleteAt;
    }

    public Structure deleteAt(Instant deleteAt) {
        this.setDeleteAt(deleteAt);
        return this;
    }

    public void setDeleteAt(Instant deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Structure createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Structure createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Structure lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Structure lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<Entrepot> getEntrepots() {
        return this.entrepots;
    }

    public void setEntrepots(Set<Entrepot> entrepots) {
        if (this.entrepots != null) {
            this.entrepots.forEach(i -> i.setStructure(null));
        }
        if (entrepots != null) {
            entrepots.forEach(i -> i.setStructure(this));
        }
        this.entrepots = entrepots;
    }

    public Structure entrepots(Set<Entrepot> entrepots) {
        this.setEntrepots(entrepots);
        return this;
    }

    public Structure addEntrepot(Entrepot entrepot) {
        this.entrepots.add(entrepot);
        entrepot.setStructure(this);
        return this;
    }

    public Structure removeEntrepot(Entrepot entrepot) {
        this.entrepots.remove(entrepot);
        entrepot.setStructure(null);
        return this;
    }

    public Set<Employe> getEmployes() {
        return this.employes;
    }

    public void setEmployes(Set<Employe> employes) {
        if (this.employes != null) {
            this.employes.forEach(i -> i.setStructure(null));
        }
        if (employes != null) {
            employes.forEach(i -> i.setStructure(this));
        }
        this.employes = employes;
    }

    public Structure employes(Set<Employe> employes) {
        this.setEmployes(employes);
        return this;
    }

    public Structure addEmploye(Employe employe) {
        this.employes.add(employe);
        employe.setStructure(this);
        return this;
    }

    public Structure removeEmploye(Employe employe) {
        this.employes.remove(employe);
        employe.setStructure(null);
        return this;
    }

    public Set<Vente> getVentes() {
        return this.ventes;
    }

    public void setVentes(Set<Vente> ventes) {
        if (this.ventes != null) {
            this.ventes.forEach(i -> i.setStructure(null));
        }
        if (ventes != null) {
            ventes.forEach(i -> i.setStructure(this));
        }
        this.ventes = ventes;
    }

    public Structure ventes(Set<Vente> ventes) {
        this.setVentes(ventes);
        return this;
    }

    public Structure addVente(Vente vente) {
        this.ventes.add(vente);
        vente.setStructure(this);
        return this;
    }

    public Structure removeVente(Vente vente) {
        this.ventes.remove(vente);
        vente.setStructure(null);
        return this;
    }

    public Set<Abonnement> getAbonnements() {
        return this.abonnements;
    }

    public void setAbonnements(Set<Abonnement> abonnements) {
        if (this.abonnements != null) {
            this.abonnements.forEach(i -> i.setStructure(null));
        }
        if (abonnements != null) {
            abonnements.forEach(i -> i.setStructure(this));
        }
        this.abonnements = abonnements;
    }

    public Structure abonnements(Set<Abonnement> abonnements) {
        this.setAbonnements(abonnements);
        return this;
    }

    public Structure addAbonnement(Abonnement abonnement) {
        this.abonnements.add(abonnement);
        abonnement.setStructure(this);
        return this;
    }

    public Structure removeAbonnement(Abonnement abonnement) {
        this.abonnements.remove(abonnement);
        abonnement.setStructure(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Structure)) {
            return false;
        }
        return getId() != null && getId().equals(((Structure) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Structure{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", email='" + getEmail() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", deleteAt='" + getDeleteAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
