package ga.aninf.stock.service.dto;

import ga.aninf.stock.domain.enumeration.StatutAbonnement;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link ga.aninf.stock.domain.Abonnement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AbonnementDTO implements Serializable {

    private UUID id;

    @NotNull
    private Instant dateDebut;

    @NotNull
    private Instant dateFin;

    @NotNull
    private StatutAbonnement statutAbonnement;

    @NotNull
    private BigDecimal prix;

    private Instant deleteAt;

    @NotNull
    @Size(max = 50)
    private String createdBy;

    private Instant createdDate;

    @Size(max = 50)
    private String lastModifiedBy;

    private Instant lastModifiedDate;

    @NotNull
    private StructureDTO structure;

    @NotNull
    private PlansAbonnementDTO plansAbonnement;

    @NotNull
    private PaiementDTO paiement;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public StatutAbonnement getStatutAbonnement() {
        return statutAbonnement;
    }

    public void setStatutAbonnement(StatutAbonnement statutAbonnement) {
        this.statutAbonnement = statutAbonnement;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
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

    public StructureDTO getStructure() {
        return structure;
    }

    public void setStructure(StructureDTO structure) {
        this.structure = structure;
    }

    public PlansAbonnementDTO getPlansAbonnement() {
        return plansAbonnement;
    }

    public void setPlansAbonnement(PlansAbonnementDTO plansAbonnement) {
        this.plansAbonnement = plansAbonnement;
    }

    public PaiementDTO getPaiement() {
        return paiement;
    }

    public void setPaiement(PaiementDTO paiement) {
        this.paiement = paiement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbonnementDTO)) {
            return false;
        }

        AbonnementDTO abonnementDTO = (AbonnementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, abonnementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AbonnementDTO{" +
            "id='" + getId() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", statutAbonnement='" + getStatutAbonnement() + "'" +
            ", prix=" + getPrix() +
            ", deleteAt='" + getDeleteAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", structure=" + getStructure() +
            ", plansAbonnement=" + getPlansAbonnement() +
            ", paiement=" + getPaiement() +
            "}";
    }
}
