package ga.aninf.stock.repository;

import ga.aninf.stock.domain.Permission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PermissionRepositoryWithBagRelationshipsImpl implements PermissionRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PERMISSIONS_PARAMETER = "permissions";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Permission> fetchBagRelationships(Optional<Permission> permission) {
        return permission.map(this::fetchAuthorities);
    }

    @Override
    public Page<Permission> fetchBagRelationships(Page<Permission> permissions) {
        return new PageImpl<>(fetchBagRelationships(permissions.getContent()), permissions.getPageable(), permissions.getTotalElements());
    }

    @Override
    public List<Permission> fetchBagRelationships(List<Permission> permissions) {
        return Optional.of(permissions).map(this::fetchAuthorities).orElse(Collections.emptyList());
    }

    Permission fetchAuthorities(Permission result) {
        return entityManager
            .createQuery(
                "select permission from Permission permission left join fetch permission.authorities where permission.id = :id",
                Permission.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Permission> fetchAuthorities(List<Permission> permissions) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, permissions.size()).forEach(index -> order.put(permissions.get(index).getId(), index));
        List<Permission> result = entityManager
            .createQuery(
                "select permission from Permission permission left join fetch permission.authorities where permission in :permissions",
                Permission.class
            )
            .setParameter(PERMISSIONS_PARAMETER, permissions)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
