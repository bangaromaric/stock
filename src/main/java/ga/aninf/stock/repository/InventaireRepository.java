package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Inventaire;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Inventaire entity.
 */
@Repository
public interface InventaireRepository extends JpaRepository<Inventaire, UUID>, JpaSpecificationExecutor<Inventaire> {
    default Optional<Inventaire> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Inventaire> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Inventaire> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select inventaire from Inventaire inventaire left join fetch inventaire.entrepot left join fetch inventaire.produit",
        countQuery = "select count(inventaire) from Inventaire inventaire"
    )
    Page<Inventaire> findAllWithToOneRelationships(Pageable pageable);

    @Query("select inventaire from Inventaire inventaire left join fetch inventaire.entrepot left join fetch inventaire.produit")
    List<Inventaire> findAllWithToOneRelationships();

    @Query(
        "select inventaire from Inventaire inventaire left join fetch inventaire.entrepot left join fetch inventaire.produit where inventaire.id =:id"
    )
    Optional<Inventaire> findOneWithToOneRelationships(@Param("id") UUID id);
}
