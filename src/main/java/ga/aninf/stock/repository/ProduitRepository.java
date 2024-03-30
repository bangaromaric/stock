package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Produit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Produit entity.
 */
@Repository
public interface ProduitRepository extends JpaRepository<Produit, UUID>, JpaSpecificationExecutor<Produit> {
    default Optional<Produit> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Produit> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Produit> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select produit from Produit produit left join fetch produit.categorie",
        countQuery = "select count(produit) from Produit produit"
    )
    Page<Produit> findAllWithToOneRelationships(Pageable pageable);

    @Query("select produit from Produit produit left join fetch produit.categorie")
    List<Produit> findAllWithToOneRelationships();

    @Query("select produit from Produit produit left join fetch produit.categorie where produit.id =:id")
    Optional<Produit> findOneWithToOneRelationships(@Param("id") UUID id);
}
