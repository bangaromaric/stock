package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Abonnement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Abonnement entity.
 */
@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, UUID>, JpaSpecificationExecutor<Abonnement> {
    default Optional<Abonnement> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Abonnement> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Abonnement> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select abonnement from Abonnement abonnement left join fetch abonnement.structure left join fetch abonnement.plansAbonnement left join fetch abonnement.paiement",
        countQuery = "select count(abonnement) from Abonnement abonnement"
    )
    Page<Abonnement> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select abonnement from Abonnement abonnement left join fetch abonnement.structure left join fetch abonnement.plansAbonnement left join fetch abonnement.paiement"
    )
    List<Abonnement> findAllWithToOneRelationships();

    @Query(
        "select abonnement from Abonnement abonnement left join fetch abonnement.structure left join fetch abonnement.plansAbonnement left join fetch abonnement.paiement where abonnement.id =:id"
    )
    Optional<Abonnement> findOneWithToOneRelationships(@Param("id") UUID id);
}
