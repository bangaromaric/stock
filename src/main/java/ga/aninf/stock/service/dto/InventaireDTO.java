package ga.aninf.stock.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ga.aninf.stock.domain.Inventaire} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventaireDTO implements Serializable {

    private UUID id;

    @NotNull
    private Long quantite;

    @NotNull
    private Instant inventaireDate;

    private Instant deleteAt;

    @NotNull
    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @NotNull
    private EntrepotDTO entrepot;

    @NotNull
    private ProduitDTO produit;

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

    public Instant getInventaireDate() {
        return inventaireDate;
    }

    public void setInventaireDate(Instant inventaireDate) {
        this.inventaireDate = inventaireDate;
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

    public EntrepotDTO getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(EntrepotDTO entrepot) {
        this.entrepot = entrepot;
    }

    public ProduitDTO getProduit() {
        return produit;
    }

    public void setProduit(ProduitDTO produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventaireDTO)) {
            return false;
        }

        InventaireDTO inventaireDTO = (InventaireDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inventaireDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventaireDTO{" +
            "id='" + getId() + "'" +
            ", quantite=" + getQuantite() +
            ", inventaireDate='" + getInventaireDate() + "'" +
            ", deleteAt='" + getDeleteAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", entrepot=" + getEntrepot() +
            ", produit=" + getProduit() +
            "}";
    }
}
