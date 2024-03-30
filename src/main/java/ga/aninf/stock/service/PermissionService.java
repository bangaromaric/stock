package ga.aninf.stock.service;

import ga.aninf.stock.domain.Permission;
import ga.aninf.stock.repository.PermissionRepository;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ga.aninf.stock.domain.Permission}.
 */
@Service
@Transactional
public class PermissionService {

    private final Logger log = LoggerFactory.getLogger(PermissionService.class);

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    /**
     * Save a permission.
     *
     * @param permission the entity to save.
     * @return the persisted entity.
     */
    public Permission save(Permission permission) {
        log.debug("Request to save Permission : {}", permission);
        return permissionRepository.save(permission);
    }

    /**
     * Update a permission.
     *
     * @param permission the entity to save.
     * @return the persisted entity.
     */
    public Permission update(Permission permission) {
        log.debug("Request to update Permission : {}", permission);
        return permissionRepository.save(permission);
    }

    /**
     * Partially update a permission.
     *
     * @param permission the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Permission> partialUpdate(Permission permission) {
        log.debug("Request to partially update Permission : {}", permission);

        return permissionRepository
            .findById(permission.getId())
            .map(existingPermission -> {
                if (permission.getRessource() != null) {
                    existingPermission.setRessource(permission.getRessource());
                }
                if (permission.getAction() != null) {
                    existingPermission.setAction(permission.getAction());
                }
                if (permission.getDeleteAt() != null) {
                    existingPermission.setDeleteAt(permission.getDeleteAt());
                }
                if (permission.getCreatedDate() != null) {
                    existingPermission.setCreatedDate(permission.getCreatedDate());
                }
                if (permission.getCreatedBy() != null) {
                    existingPermission.setCreatedBy(permission.getCreatedBy());
                }
                if (permission.getLastModifiedDate() != null) {
                    existingPermission.setLastModifiedDate(permission.getLastModifiedDate());
                }
                if (permission.getLastModifiedBy() != null) {
                    existingPermission.setLastModifiedBy(permission.getLastModifiedBy());
                }

                return existingPermission;
            })
            .map(permissionRepository::save);
    }

    /**
     * Get all the permissions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Permission> findAllWithEagerRelationships(Pageable pageable) {
        return permissionRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one permission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Permission> findOne(UUID id) {
        log.debug("Request to get Permission : {}", id);
        return permissionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the permission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Permission : {}", id);
        permissionRepository.deleteById(id);
    }
}
