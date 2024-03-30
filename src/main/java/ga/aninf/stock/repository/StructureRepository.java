package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Structure;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Structure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StructureRepository extends JpaRepository<Structure, UUID>, JpaSpecificationExecutor<Structure> {}
