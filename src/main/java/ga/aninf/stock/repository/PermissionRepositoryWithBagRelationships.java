package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Permission;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PermissionRepositoryWithBagRelationships {
    Optional<Permission> fetchBagRelationships(Optional<Permission> permission);

    List<Permission> fetchBagRelationships(List<Permission> permissions);

    Page<Permission> fetchBagRelationships(Page<Permission> permissions);
}
