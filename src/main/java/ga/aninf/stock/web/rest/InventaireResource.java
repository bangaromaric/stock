package ga.aninf.stock.web.rest;

import ga.aninf.stock.repository.InventaireRepository;
import ga.aninf.stock.service.InventaireQueryService;
import ga.aninf.stock.service.InventaireService;
import ga.aninf.stock.service.criteria.InventaireCriteria;
import ga.aninf.stock.service.dto.InventaireDTO;
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
 * REST controller for managing {@link ga.aninf.stock.domain.Inventaire}.
 */
@RestController
@RequestMapping("/api/inventaires")
public class InventaireResource {

    private final Logger log = LoggerFactory.getLogger(InventaireResource.class);

    private static final String ENTITY_NAME = "inventaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InventaireService inventaireService;

    private final InventaireRepository inventaireRepository;

    private final InventaireQueryService inventaireQueryService;

    public InventaireResource(
        InventaireService inventaireService,
        InventaireRepository inventaireRepository,
        InventaireQueryService inventaireQueryService
    ) {
        this.inventaireService = inventaireService;
        this.inventaireRepository = inventaireRepository;
        this.inventaireQueryService = inventaireQueryService;
    }

    /**
     * {@code POST  /inventaires} : Create a new inventaire.
     *
     * @param inventaireDTO the inventaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inventaireDTO, or with status {@code 400 (Bad Request)} if the inventaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<InventaireDTO> createInventaire(@Valid @RequestBody InventaireDTO inventaireDTO) throws URISyntaxException {
        log.debug("REST request to save Inventaire : {}", inventaireDTO);
        if (inventaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new inventaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        inventaireDTO = inventaireService.save(inventaireDTO);
        return ResponseEntity.created(new URI("/api/inventaires/" + inventaireDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, inventaireDTO.getId().toString()))
            .body(inventaireDTO);
    }

    /**
     * {@code PUT  /inventaires/:id} : Updates an existing inventaire.
     *
     * @param id the id of the inventaireDTO to save.
     * @param inventaireDTO the inventaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventaireDTO,
     * or with status {@code 400 (Bad Request)} if the inventaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inventaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InventaireDTO> updateInventaire(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody InventaireDTO inventaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Inventaire : {}, {}", id, inventaireDTO);
        if (inventaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        inventaireDTO = inventaireService.update(inventaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventaireDTO.getId().toString()))
            .body(inventaireDTO);
    }

    /**
     * {@code PATCH  /inventaires/:id} : Partial updates given fields of an existing inventaire, field will ignore if it is null
     *
     * @param id the id of the inventaireDTO to save.
     * @param inventaireDTO the inventaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inventaireDTO,
     * or with status {@code 400 (Bad Request)} if the inventaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inventaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inventaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InventaireDTO> partialUpdateInventaire(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody InventaireDTO inventaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inventaire partially : {}, {}", id, inventaireDTO);
        if (inventaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inventaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inventaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InventaireDTO> result = inventaireService.partialUpdate(inventaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inventaireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inventaires} : get all the inventaires.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inventaires in body.
     */
    @GetMapping("")
    public ResponseEntity<List<InventaireDTO>> getAllInventaires(
        InventaireCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Inventaires by criteria: {}", criteria);

        Page<InventaireDTO> page = inventaireQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inventaires/count} : count all the inventaires.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countInventaires(InventaireCriteria criteria) {
        log.debug("REST request to count Inventaires by criteria: {}", criteria);
        return ResponseEntity.ok().body(inventaireQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /inventaires/:id} : get the "id" inventaire.
     *
     * @param id the id of the inventaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inventaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InventaireDTO> getInventaire(@PathVariable("id") UUID id) {
        log.debug("REST request to get Inventaire : {}", id);
        Optional<InventaireDTO> inventaireDTO = inventaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inventaireDTO);
    }

    /**
     * {@code DELETE  /inventaires/:id} : delete the "id" inventaire.
     *
     * @param id the id of the inventaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventaire(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Inventaire : {}", id);
        inventaireService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
