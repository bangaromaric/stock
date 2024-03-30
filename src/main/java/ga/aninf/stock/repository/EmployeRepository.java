package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Employe;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Employe entity.
 */
@Repository
public interface EmployeRepository extends JpaRepository<Employe, UUID>, JpaSpecificationExecutor<Employe> {
    default Optional<Employe> findOneWithEagerRelationships(UUID id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Employe> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Employe> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select employe from Employe employe left join fetch employe.internalUser left join fetch employe.structure",
        countQuery = "select count(employe) from Employe employe"
    )
    Page<Employe> findAllWithToOneRelationships(Pageable pageable);

    @Query("select employe from Employe employe left join fetch employe.internalUser left join fetch employe.structure")
    List<Employe> findAllWithToOneRelationships();

    @Query(
        "select employe from Employe employe left join fetch employe.internalUser left join fetch employe.structure where employe.id =:id"
    )
    Optional<Employe> findOneWithToOneRelationships(@Param("id") UUID id);
}
