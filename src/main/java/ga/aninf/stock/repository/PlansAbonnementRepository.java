package ga.aninf.stock.repository;

import ga.aninf.stock.domain.PlansAbonnement;
import java.util.UUID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlansAbonnement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlansAbonnementRepository extends JpaRepository<PlansAbonnement, UUID>, JpaSpecificationExecutor<PlansAbonnement> {}
