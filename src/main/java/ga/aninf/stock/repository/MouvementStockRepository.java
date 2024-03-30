package ga.aninf.stock.repository;

import ga.aninf.stock.domain.MouvementStock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MouvementStock entity.
 */
@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, UUID>, JpaSpecificationExecutor<MouvementStock> {
    default Optional<MouvementStock> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<MouvementStock> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<MouvementStock> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select mouvementStock from MouvementStock mouvementStock left join fetch mouvementStock.produit left join fetch mouvementStock.entrepot",
        countQuery = "select count(mouvementStock) from MouvementStock mouvementStock"
    )
    Page<MouvementStock> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select mouvementStock from MouvementStock mouvementStock left join fetch mouvementStock.produit left join fetch mouvementStock.entrepot"
    )
    List<MouvementStock> findAllWithToOneRelationships();

    @Query(
        "select mouvementStock from MouvementStock mouvementStock left join fetch mouvementStock.produit left join fetch mouvementStock.entrepot where mouvementStock.id =:id"
    )
    Optional<MouvementStock> findOneWithToOneRelationships(@Param("id") UUID id);
}
