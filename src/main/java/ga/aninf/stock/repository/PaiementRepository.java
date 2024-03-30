package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Paiement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Paiement entity.
 */
@Repository
public interface PaiementRepository extends JpaRepository<Paiement, UUID>, JpaSpecificationExecutor<Paiement> {
    default Optional<Paiement> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Paiement> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Paiement> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select paiement from Paiement paiement left join fetch paiement.plansAbonnement",
        countQuery = "select count(paiement) from Paiement paiement"
    )
    Page<Paiement> findAllWithToOneRelationships(Pageable pageable);

    @Query("select paiement from Paiement paiement left join fetch paiement.plansAbonnement")
    List<Paiement> findAllWithToOneRelationships();

    @Query("select paiement from Paiement paiement left join fetch paiement.plansAbonnement where paiement.id =:id")
    Optional<Paiement> findOneWithToOneRelationships(@Param("id") UUID id);
}
