package ga.aninf.stock.web.rest;

import ga.aninf.stock.repository.PlansAbonnementRepository;
import ga.aninf.stock.service.PlansAbonnementQueryService;
import ga.aninf.stock.service.PlansAbonnementService;
import ga.aninf.stock.service.criteria.PlansAbonnementCriteria;
import ga.aninf.stock.service.dto.PlansAbonnementDTO;
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
 * REST controller for managing {@link ga.aninf.stock.domain.PlansAbonnement}.
 */
@RestController
@RequestMapping("/api/plans-abonnements")
public class PlansAbonnementResource {

    private final Logger log = LoggerFactory.getLogger(PlansAbonnementResource.class);

    private static final String ENTITY_NAME = "plansAbonnement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlansAbonnementService plansAbonnementService;

    private final PlansAbonnementRepository plansAbonnementRepository;

    private final PlansAbonnementQueryService plansAbonnementQueryService;

    public PlansAbonnementResource(
        PlansAbonnementService plansAbonnementService,
        PlansAbonnementRepository plansAbonnementRepository,
        PlansAbonnementQueryService plansAbonnementQueryService
    ) {
        this.plansAbonnementService = plansAbonnementService;
        this.plansAbonnementRepository = plansAbonnementRepository;
        this.plansAbonnementQueryService = plansAbonnementQueryService;
    }

    /**
     * {@code POST  /plans-abonnements} : Create a new plansAbonnement.
     *
     * @param plansAbonnementDTO the plansAbonnementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plansAbonnementDTO, or with status {@code 400 (Bad Request)} if the plansAbonnement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlansAbonnementDTO> createPlansAbonnement(@Valid @RequestBody PlansAbonnementDTO plansAbonnementDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlansAbonnement : {}", plansAbonnementDTO);
        if (plansAbonnementDTO.getId() != null) {
            throw new BadRequestAlertException("A new plansAbonnement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        plansAbonnementDTO = plansAbonnementService.save(plansAbonnementDTO);
        return ResponseEntity.created(new URI("/api/plans-abonnements/" + plansAbonnementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, plansAbonnementDTO.getId().toString()))
            .body(plansAbonnementDTO);
    }

    /**
     * {@code PUT  /plans-abonnements/:id} : Updates an existing plansAbonnement.
     *
     * @param id the id of the plansAbonnementDTO to save.
     * @param plansAbonnementDTO the plansAbonnementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plansAbonnementDTO,
     * or with status {@code 400 (Bad Request)} if the plansAbonnementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plansAbonnementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlansAbonnementDTO> updatePlansAbonnement(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody PlansAbonnementDTO plansAbonnementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlansAbonnement : {}, {}", id, plansAbonnementDTO);
        if (plansAbonnementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plansAbonnementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plansAbonnementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        plansAbonnementDTO = plansAbonnementService.update(plansAbonnementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plansAbonnementDTO.getId().toString()))
            .body(plansAbonnementDTO);
    }

    /**
     * {@code PATCH  /plans-abonnements/:id} : Partial updates given fields of an existing plansAbonnement, field will ignore if it is null
     *
     * @param id the id of the plansAbonnementDTO to save.
     * @param plansAbonnementDTO the plansAbonnementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plansAbonnementDTO,
     * or with status {@code 400 (Bad Request)} if the plansAbonnementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plansAbonnementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plansAbonnementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlansAbonnementDTO> partialUpdatePlansAbonnement(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody PlansAbonnementDTO plansAbonnementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlansAbonnement partially : {}, {}", id, plansAbonnementDTO);
        if (plansAbonnementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plansAbonnementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plansAbonnementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlansAbonnementDTO> result = plansAbonnementService.partialUpdate(plansAbonnementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plansAbonnementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plans-abonnements} : get all the plansAbonnements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plansAbonnements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PlansAbonnementDTO>> getAllPlansAbonnements(
        PlansAbonnementCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PlansAbonnements by criteria: {}", criteria);

        Page<PlansAbonnementDTO> page = plansAbonnementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plans-abonnements/count} : count all the plansAbonnements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPlansAbonnements(PlansAbonnementCriteria criteria) {
        log.debug("REST request to count PlansAbonnements by criteria: {}", criteria);
        return ResponseEntity.ok().body(plansAbonnementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plans-abonnements/:id} : get the "id" plansAbonnement.
     *
     * @param id the id of the plansAbonnementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plansAbonnementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlansAbonnementDTO> getPlansAbonnement(@PathVariable("id") UUID id) {
        log.debug("REST request to get PlansAbonnement : {}", id);
        Optional<PlansAbonnementDTO> plansAbonnementDTO = plansAbonnementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plansAbonnementDTO);
    }

    /**
     * {@code DELETE  /plans-abonnements/:id} : delete the "id" plansAbonnement.
     *
     * @param id the id of the plansAbonnementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlansAbonnement(@PathVariable("id") UUID id) {
        log.debug("REST request to delete PlansAbonnement : {}", id);
        plansAbonnementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
