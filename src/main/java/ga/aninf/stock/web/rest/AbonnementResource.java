package ga.aninf.stock.web.rest;

import ga.aninf.stock.repository.AbonnementRepository;
import ga.aninf.stock.service.AbonnementQueryService;
import ga.aninf.stock.service.AbonnementService;
import ga.aninf.stock.service.criteria.AbonnementCriteria;
import ga.aninf.stock.service.dto.AbonnementDTO;
import ga.aninf.stock.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ga.aninf.stock.domain.Abonnement}.
 */
@RestController
@RequestMapping("/api/abonnements")
public class AbonnementResource {

    private final Logger log = LoggerFactory.getLogger(AbonnementResource.class);

    private static final String ENTITY_NAME = "abonnement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AbonnementService abonnementService;

    private final AbonnementRepository abonnementRepository;

    private final AbonnementQueryService abonnementQueryService;

    public AbonnementResource(
        AbonnementService abonnementService,
        AbonnementRepository abonnementRepository,
        AbonnementQueryService abonnementQueryService
    ) {
        this.abonnementService = abonnementService;
        this.abonnementRepository = abonnementRepository;
        this.abonnementQueryService = abonnementQueryService;
    }

    /**
     * {@code POST  /abonnements} : Create a new abonnement.
     *
     * @param abonnementDTO the abonnementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new abonnementDTO, or with status {@code 400 (Bad Request)} if the abonnement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AbonnementDTO> createAbonnement(@Valid @RequestBody AbonnementDTO abonnementDTO) throws URISyntaxException {
        log.debug("REST request to save Abonnement : {}", abonnementDTO);
        if (abonnementDTO.getId() != null) {
            throw new BadRequestAlertException("A new abonnement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        abonnementDTO = abonnementService.save(abonnementDTO);
        return ResponseEntity.created(new URI("/api/abonnements/" + abonnementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, abonnementDTO.getId().toString()))
            .body(abonnementDTO);
    }

    /**
     * {@code PUT  /abonnements/:id} : Updates an existing abonnement.
     *
     * @param id the id of the abonnementDTO to save.
     * @param abonnementDTO the abonnementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abonnementDTO,
     * or with status {@code 400 (Bad Request)} if the abonnementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the abonnementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AbonnementDTO> updateAbonnement(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody AbonnementDTO abonnementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Abonnement : {}, {}", id, abonnementDTO);
        if (abonnementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abonnementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abonnementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        abonnementDTO = abonnementService.update(abonnementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abonnementDTO.getId().toString()))
            .body(abonnementDTO);
    }

    /**
     * {@code PATCH  /abonnements/:id} : Partial updates given fields of an existing abonnement, field will ignore if it is null
     *
     * @param id the id of the abonnementDTO to save.
     * @param abonnementDTO the abonnementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated abonnementDTO,
     * or with status {@code 400 (Bad Request)} if the abonnementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the abonnementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the abonnementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AbonnementDTO> partialUpdateAbonnement(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody AbonnementDTO abonnementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Abonnement partially : {}, {}", id, abonnementDTO);
        if (abonnementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, abonnementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!abonnementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AbonnementDTO> result = abonnementService.partialUpdate(abonnementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, abonnementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /abonnements} : get all the abonnements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of abonnements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AbonnementDTO>> getAllAbonnements(
        AbonnementCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Abonnements by criteria: {}", criteria);

        Page<AbonnementDTO> page = abonnementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /abonnements/count} : count all the abonnements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAbonnements(AbonnementCriteria criteria) {
        log.debug("REST request to count Abonnements by criteria: {}", criteria);
        return ResponseEntity.ok().body(abonnementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /abonnements/:id} : get the "id" abonnement.
     *
     * @param id the id of the abonnementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the abonnementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AbonnementDTO> getAbonnement(@PathVariable("id") UUID id) {
        log.debug("REST request to get Abonnement : {}", id);
        Optional<AbonnementDTO> abonnementDTO = abonnementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(abonnementDTO);
    }

    /**
     * {@code DELETE  /abonnements/:id} : delete the "id" abonnement.
     *
     * @param id the id of the abonnementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbonnement(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Abonnement : {}", id);
        abonnementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
