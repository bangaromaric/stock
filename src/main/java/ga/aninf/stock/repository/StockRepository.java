package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Stock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Stock entity.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, UUID>, JpaSpecificationExecutor<Stock> {
    default Optional<Stock> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Stock> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Stock> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select stock from Stock stock left join fetch stock.entrepot left join fetch stock.produit",
        countQuery = "select count(stock) from Stock stock"
    )
    Page<Stock> findAllWithToOneRelationships(Pageable pageable);

    @Query("select stock from Stock stock left join fetch stock.entrepot left join fetch stock.produit")
    List<Stock> findAllWithToOneRelationships();

    @Query("select stock from Stock stock left join fetch stock.entrepot left join fetch stock.produit where stock.id =:id")
    Optional<Stock> findOneWithToOneRelationships(@Param("id") UUID id);
}
