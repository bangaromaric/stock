package ga.aninf.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @NotNull
    @Column(name = "prix_unitaire", precision = 21, scale = 2, nullable = false)
    private BigDecimal prixUnitaire;

    @Column(name = "date_expiration")
    private LocalDate dateExpiration;

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
    @JsonIgnoreProperties(value = { "produits" }, allowSetters = true)
    private Categorie categorie;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "entrepot" }, allowSetters = true)
    private Set<MouvementStock> mouvementStocks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entrepot", "produit" }, allowSetters = true)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entrepot", "produit" }, allowSetters = true)
    private Set<Inventaire> inventaires = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "produit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "produit", "structure" }, allowSetters = true)
    private Set<Vente> ventes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public UUID getId() {
        return this.id;
    }

    public Produit id(UUID id) {
        this.setId(id);
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public Produit libelle(String libelle) {
        this.setLibelle(libelle);
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return this.description;
    }

    public Produit description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return this.slug;
    }

    public Produit slug(String slug) {
        this.setSlug(slug);
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public BigDecimal getPrixUnitaire() {
        return this.prixUnitaire;
    }

    public Produit prixUnitaire(BigDecimal prixUnitaire) {
        this.setPrixUnitaire(prixUnitaire);
        return this;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public LocalDate getDateExpiration() {
        return this.dateExpiration;
    }

    public Produit dateExpiration(LocalDate dateExpiration) {
        this.setDateExpiration(dateExpiration);
        return this;
    }

    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Instant getDeleteAt() {
        return this.deleteAt;
    }

    public Produit deleteAt(Instant deleteAt) {
        this.setDeleteAt(deleteAt);
        return this;
    }

    public void setDeleteAt(Instant deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Produit createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Produit createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Produit lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public Produit lastModifiedDate(Instant lastModifiedDate) {
        this.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Produit categorie(Categorie categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public Set<MouvementStock> getMouvementStocks() {
        return this.mouvementStocks;
    }

    public void setMouvementStocks(Set<MouvementStock> mouvementStocks) {
        if (this.mouvementStocks != null) {
            this.mouvementStocks.forEach(i -> i.setProduit(null));
        }
        if (mouvementStocks != null) {
            mouvementStocks.forEach(i -> i.setProduit(this));
        }
        this.mouvementStocks = mouvementStocks;
    }

    public Produit mouvementStocks(Set<MouvementStock> mouvementStocks) {
        this.setMouvementStocks(mouvementStocks);
        return this;
    }

    public Produit addMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.add(mouvementStock);
        mouvementStock.setProduit(this);
        return this;
    }

    public Produit removeMouvementStock(MouvementStock mouvementStock) {
        this.mouvementStocks.remove(mouvementStock);
        mouvementStock.setProduit(null);
        return this;
    }

    public Set<Stock> getStocks() {
        return this.stocks;
    }

    public void setStocks(Set<Stock> stocks) {
        if (this.stocks != null) {
            this.stocks.forEach(i -> i.setProduit(null));
        }
        if (stocks != null) {
            stocks.forEach(i -> i.setProduit(this));
        }
        this.stocks = stocks;
    }

    public Produit stocks(Set<Stock> stocks) {
        this.setStocks(stocks);
        return this;
    }

    public Produit addStock(Stock stock) {
        this.stocks.add(stock);
        stock.setProduit(this);
        return this;
    }

    public Produit removeStock(Stock stock) {
        this.stocks.remove(stock);
        stock.setProduit(null);
        return this;
    }

    public Set<Inventaire> getInventaires() {
        return this.inventaires;
    }

    public void setInventaires(Set<Inventaire> inventaires) {
        if (this.inventaires != null) {
            this.inventaires.forEach(i -> i.setProduit(null));
        }
        if (inventaires != null) {
            inventaires.forEach(i -> i.setProduit(this));
        }
        this.inventaires = inventaires;
    }

    public Produit inventaires(Set<Inventaire> inventaires) {
        this.setInventaires(inventaires);
        return this;
    }

    public Produit addInventaire(Inventaire inventaire) {
        this.inventaires.add(inventaire);
        inventaire.setProduit(this);
        return this;
    }

    public Produit removeInventaire(Inventaire inventaire) {
        this.inventaires.remove(inventaire);
        inventaire.setProduit(null);
        return this;
    }

    public Set<Vente> getVentes() {
        return this.ventes;
    }

    public void setVentes(Set<Vente> ventes) {
        if (this.ventes != null) {
            this.ventes.forEach(i -> i.setProduit(null));
        }
        if (ventes != null) {
            ventes.forEach(i -> i.setProduit(this));
        }
        this.ventes = ventes;
    }

    public Produit ventes(Set<Vente> ventes) {
        this.setVentes(ventes);
        return this;
    }

    public Produit addVente(Vente vente) {
        this.ventes.add(vente);
        vente.setProduit(this);
        return this;
    }

    public Produit removeVente(Vente vente) {
        this.ventes.remove(vente);
        vente.setProduit(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return getId() != null && getId().equals(((Produit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", description='" + getDescription() + "'" +
            ", slug='" + getSlug() + "'" +
            ", prixUnitaire=" + getPrixUnitaire() +
            ", dateExpiration='" + getDateExpiration() + "'" +
            ", deleteAt='" + getDeleteAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            "}";
    }
}
