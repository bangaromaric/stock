package ga.aninf.stock.web.rest;

import ga.aninf.stock.repository.StructureRepository;
import ga.aninf.stock.service.StructureQueryService;
import ga.aninf.stock.service.StructureService;
import ga.aninf.stock.service.criteria.StructureCriteria;
import ga.aninf.stock.service.dto.StructureDTO;
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
 * REST controller for managing {@link ga.aninf.stock.domain.Structure}.
 */
@RestController
@RequestMapping("/api/structures")
public class StructureResource {

    private final Logger log = LoggerFactory.getLogger(StructureResource.class);

    private static final String ENTITY_NAME = "structure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StructureService structureService;

    private final StructureRepository structureRepository;

    private final StructureQueryService structureQueryService;

    public StructureResource(
        StructureService structureService,
        StructureRepository structureRepository,
        StructureQueryService structureQueryService
    ) {
        this.structureService = structureService;
        this.structureRepository = structureRepository;
        this.structureQueryService = structureQueryService;
    }

    /**
     * {@code POST  /structures} : Create a new structure.
     *
     * @param structureDTO the structureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new structureDTO, or with status {@code 400 (Bad Request)} if the structure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<StructureDTO> createStructure(@Valid @RequestBody StructureDTO structureDTO) throws URISyntaxException {
        log.debug("REST request to save Structure : {}", structureDTO);
        if (structureDTO.getId() != null) {
            throw new BadRequestAlertException("A new structure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        structureDTO = structureService.save(structureDTO);
        return ResponseEntity.created(new URI("/api/structures/" + structureDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, structureDTO.getId().toString()))
            .body(structureDTO);
    }

    /**
     * {@code PUT  /structures/:id} : Updates an existing structure.
     *
     * @param id the id of the structureDTO to save.
     * @param structureDTO the structureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated structureDTO,
     * or with status {@code 400 (Bad Request)} if the structureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the structureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StructureDTO> updateStructure(
        @PathVariable(value = "id", required = false) final UUID id,
        @Valid @RequestBody StructureDTO structureDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Structure : {}, {}", id, structureDTO);
        if (structureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, structureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!structureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        structureDTO = structureService.update(structureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, structureDTO.getId().toString()))
            .body(structureDTO);
    }

    /**
     * {@code PATCH  /structures/:id} : Partial updates given fields of an existing structure, field will ignore if it is null
     *
     * @param id the id of the structureDTO to save.
     * @param structureDTO the structureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated structureDTO,
     * or with status {@code 400 (Bad Request)} if the structureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the structureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the structureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StructureDTO> partialUpdateStructure(
        @PathVariable(value = "id", required = false) final UUID id,
        @NotNull @RequestBody StructureDTO structureDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Structure partially : {}, {}", id, structureDTO);
        if (structureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, structureDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!structureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StructureDTO> result = structureService.partialUpdate(structureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, structureDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /structures} : get all the structures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of structures in body.
     */
    @GetMapping("")
    public ResponseEntity<List<StructureDTO>> getAllStructures(
        StructureCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Structures by criteria: {}", criteria);

        Page<StructureDTO> page = structureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /structures/count} : count all the structures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countStructures(StructureCriteria criteria) {
        log.debug("REST request to count Structures by criteria: {}", criteria);
        return ResponseEntity.ok().body(structureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /structures/:id} : get the "id" structure.
     *
     * @param id the id of the structureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the structureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StructureDTO> getStructure(@PathVariable("id") UUID id) {
        log.debug("REST request to get Structure : {}", id);
        Optional<StructureDTO> structureDTO = structureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(structureDTO);
    }

    /**
     * {@code DELETE  /structures/:id} : delete the "id" structure.
     *
     * @param id the id of the structureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStructure(@PathVariable("id") UUID id) {
        log.debug("REST request to delete Structure : {}", id);
        structureService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
