package ga.aninf.stock.web.rest;

import ga.aninf.stock.repository.EntrepotRepository;
import ga.aninf.stock.service.EntrepotQueryService;
import ga.aninf.stock.service.EntrepotService;
import ga.aninf.stock.service.criteria.EntrepotCriteria;
import ga.aninf.stock.service.dto.EntrepotDTO;
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
 * REST controller for managing {@link ga.aninf.stock.domain.Entrepot}.
 */
@RestController
@RequestMapping("/api/entrepots")
public class EntrepotResource {

    private final Logger log = LoggerFactory.getLogger(EntrepotResource.class);

    private static final String ENTITY_NAME = "entrepot";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntrepotService entrepotService;

    private final EntrepotRepository entrepotRepository;

    private final EntrepotQueryService entrepotQueryService;

    public EntrepotResource(
        EntrepotService entrepotService,
        EntrepotRepository entrepotRepository,
        EntrepotQueryService entrepotQueryService
    ) {
        this.entrepotService = entrepotService;
        this.entrepotRepository = entrepotRepository;
        this.entrepotQueryService = entrepotQueryService;
    }

    /**
     * {@code POST  /entrepots} : Create a new entrepot.
     *
     * @param entrepotDTO the entrepotDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entrepotDTO, or with status {@code 400 (Bad Request)} if the entrepot has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EntrepotDTO> createEntrepot(@Valid @RequestBody EntrepotDTO entrepotDTO) throws URISyntaxException {
        log.debug("REST request to save Entrepot : {}", entrepotDTO);
        if (entrepotDTO.getId() != null) {
            throw new BadRequestAlertException("A new entrepot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        entrepotDTO = entrepotService.save(entrepotDTO);
        return ResponseEntity.created(new URI("/api/entrepots/" + entrepotDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, entrepotDTO.getId().toString()))
            .body(entrepotDTO);
    }

    /**
     * {@code PUT  /entrepots/:id} : Updates an existing entrepot.
     *
     * @param id the id of the entrepotDTO to save.
     * @param entrepotDTO the entrepotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entrepotDTO,
     * or with status {@code 400 (Bad Request)} if the entrepotDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entrepotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntrepotDTO> updateEntrepot(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody EntrepotDTO entrepotDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entrepot : {}, {}", id, entrepotDTO);
        if (entrepotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entrepotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        entrepotDTO = entrepotService.update(entrepotDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entrepotDTO.getId().toString()))
            .body(entrepotDTO);
    }

    /**
     * {@code PATCH  /entrepots/:id} : Partial updates given fields of an existing entrepot, field will ignore if it is null
     *
     * @param id the id of the entrepotDTO to save.
     * @param entrepotDTO the entrepotDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entrepotDTO,
     * or with status {@code 400 (Bad Request)} if the entrepotDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entrepotDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entrepotDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntrepotDTO> partialUpdateEntrepot(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody EntrepotDTO entrepotDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entrepot partially : {}, {}", id, entrepotDTO);
        if (entrepotDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entrepotDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entrepotRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntrepotDTO> result = entrepotService.partialUpdate(entrepotDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entrepotDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entrepots} : get all the entrepots.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entrepots in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EntrepotDTO>> getAllEntrepots(
        EntrepotCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Entrepots by criteria: {}", criteria);

        Page<EntrepotDTO> page = entrepotQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entrepots/count} : count all the entrepots.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEntrepots(EntrepotCriteria criteria) {
        log.debug("REST request to count Entrepots by criteria: {}", criteria);
        return ResponseEntity.ok().body(entrepotQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /entrepots/:id} : get the "id" entrepot.
     *
     * @param id the id of the entrepotDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entrepotDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntrepotDTO> getEntrepot(@PathVariable("id") UUID id) {
        log.debug("REST request to get Entrepot : {}", id);
        Optional<EntrepotDTO> entrepotDTO = entrepotService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entrepotDTO);
    }

    /**
     * {@code DELETE  /entrepots/:id} : delete the "id" entrepot.
     *
     * @param id the id of the entrepotDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrepot(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Entrepot : {}", id);
        entrepotService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
