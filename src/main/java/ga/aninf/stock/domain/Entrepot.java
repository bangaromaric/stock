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
 * A Entrepot.
 */
@Entity
@Table(name = "entrepot")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entrepot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;

    @NotNull
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @NotNull
    @Column(name = "capacite", nullable = false)
    private Integer capacite;

    @Lob
    @Column(name = "description")
    private String description;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "entrepots", "employes", "ventes", "abonnements" }, allowSetters = true)
    private Structure structure;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entrepot")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "entrepot" }, allowSetters = true)
    private Set<MouvementStock> mouvementStocks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entrepot")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entrepot", "produit" }, allowSetters = true)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "entrepot")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entrepot", "produit" }, allowSetters = true)
    private Set<Inventaire> inventaires = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Entrepot id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Entrepot libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getSlug() {
        return this.slug;
    }

    public Entrepot slug(String slug) {
        this.setSlug(slug);
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getCapacite() {
        return this.capacite;
    }

    public Entrepot capacite(Integer capacite) {
        this.setCapacite(capacite);
        return this;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public String getDescription() {
        return this.description;
    }

    public Entrepot description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDeleteAt() {
        return this.deleteAt;
    }

    public Entrepot deleteAt(Instant deleteAt) {
        this.setDeleteAt(deleteAt);
        return this;
    }

    public void setDeleteAt(Instant deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Entrepot createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Entrepot createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Entrepot lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Entrepot lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Structure getStructure() {
        return this.structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Entrepot structure(Structure structure) {
        this.setStructure(structure);
        return this;
    }

    public Set<MouvementStock> getMouvementStocks() {
        return this.mouvementStocks;
    }

    public void setMouvementStocks(Set<MouvementStock> mouvementStocks) {
        if (this.mouvementStocks != null) {
            this.mouvementStocks.forEach(i -> i.setEntrepot(null));
        }
        if (mouvementStocks != null) {
            mouvementStocks.forEach(i -> i.setEntrepot(this));
        }
        this.mouvementStocks = mouvementStocks;
    }

    public Entrepot mouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.setMouvementStocks(mouvementStocks);
        return this;
    }

    public Entrepot addMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.add(mouvementStock);
        mouvementStock.setEntrepot(this);
        return this;
    }

    public Entrepot removeMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.remove(mouvementStock);
        mouvementStock.setEntrepot(null);
        return this;
    }

    public Set<Stock> getStocks() {
        return this.stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        if (this.stocks != null) {
            this.stocks.forEach(i -> i.setEntrepot(null));
        }
        if (stocks != null) {
            stocks.forEach(i -> i.setEntrepot(this));
        }
        this.stocks = stocks;
    }

    public Entrepot stocks(Set<Stock> stocks) {
        this.setStocks(stocks);
        return this;
    }

    public Entrepot addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setEntrepot(this);
        return this;
    }

    public Entrepot removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setEntrepot(null);
        return this;
    }

    public Set<Inventaire> getInventaires() {
        return this.inventaires;
    }

    public void setInventaires(Set<Inventaire> inventaires) {
        if (this.inventaires != null) {
            this.inventaires.forEach(i -> i.setEntrepot(null));
        }
        if (inventaires != null) {
            inventaires.forEach(i -> i.setEntrepot(this));
        }
        this.inventaires = inventaires;
    }

    public Entrepot inventaires(Set<Inventaire> inventaires) {
        this.setInventaires(inventaires);
        return this;
    }

    public Entrepot addInventaire(Inventaire inventaire) {
        this.inventaires.add(inventaire);
        inventaire.setEntrepot(this);
        return this;
    }

    public Entrepot removeInventaire(Inventaire inventaire) {
        this.inventaires.remove(inventaire);
        inventaire.setEntrepot(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entrepot)) {
            return false;
        }
        return getId() != null && getId().equals(((Entrepot) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entrepot{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", slug='" + getSlug() + "'" +
            ", capacite=" + getCapacite() +
            ", description='" + getDescription() + "'" +
            ", deleteAt='" + getDeleteAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
