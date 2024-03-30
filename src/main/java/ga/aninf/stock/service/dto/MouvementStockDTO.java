package ga.aninf.stock.service.dto;

import ga.aninf.stock.domain.enumeration.TypeMouvement;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ga.aninf.stock.domain.MouvementStock} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MouvementStockDTO implements Serializable {

    private UUID id;

    @NotNull
    private Long quantite;

    @NotNull
    private TypeMouvement typeMouvement;

    @NotNull
    private Instant transactionDate;

    private Instant deleteAt;

    @NotNull
    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @NotNull
    private ProduitDTO produit;

    @NotNull
    private EntrepotDTO entrepot;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public TypeMouvement getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvement typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Instant getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Instant deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    public EntrepotDTO getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(EntrepotDTO entrepot) {
        this.entrepot = entrepot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MouvementStockDTO)) {
            return false;
        }

        MouvementStockDTO mouvementStockDTO = (MouvementStockDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mouvementStockDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MouvementStockDTO{" +
            "id='" + getId() + "'" +
            ", quantite=" + getQuantite() +
            ", typeMouvement='" + getTypeMouvement() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", deleteAt='" + getDeleteAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", produit=" + getProduit() +
            ", entrepot=" + getEntrepot() +
            "}";
    }
}
