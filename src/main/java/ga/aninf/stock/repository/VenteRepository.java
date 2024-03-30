package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Vente;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vente entity.
 */
@Repository
public interface VenteRepository extends JpaRepository<Vente, UUID>, JpaSpecificationExecutor<Vente> {
    default Optional<Vente> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Vente> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Vente> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select vente from Vente vente left join fetch vente.produit left join fetch vente.structure",
        countQuery = "select count(vente) from Vente vente"
    )
    Page<Vente> findAllWithToOneRelationships(Pageable pageable);

    @Query("select vente from Vente vente left join fetch vente.produit left join fetch vente.structure")
    List<Vente> findAllWithToOneRelationships();

    @Query("select vente from Vente vente left join fetch vente.produit left join fetch vente.structure where vente.id =:id")
    Optional<Vente> findOneWithToOneRelationships(@Param("id") UUID id);
}
