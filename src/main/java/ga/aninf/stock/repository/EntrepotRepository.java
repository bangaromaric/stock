package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Entrepot;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Entrepot entity.
 */
@Repository
public interface EntrepotRepository extends JpaRepository<Entrepot, UUID>, JpaSpecificationExecutor<Entrepot> {
    default Optional<Entrepot> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Entrepot> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Entrepot> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select entrepot from Entrepot entrepot left join fetch entrepot.structure",
        countQuery = "select count(entrepot) from Entrepot entrepot"
    )
    Page<Entrepot> findAllWithToOneRelationships(Pageable pageable);

    @Query("select entrepot from Entrepot entrepot left join fetch entrepot.structure")
    List<Entrepot> findAllWithToOneRelationships();

    @Query("select entrepot from Entrepot entrepot left join fetch entrepot.structure where entrepot.id =:id")
    Optional<Entrepot> findOneWithToOneRelationships(@Param("id") UUID id);
}
