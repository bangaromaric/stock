package ga.aninf.stock.web.rest;

import static ga.aninf.stock.domain.EntrepotAsserts.*;
import static ga.aninf.stock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import ga.aninf.stock.IntegrationTest;
import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.repository.EntrepotRepository;
import ga.aninf.stock.service.EntrepotService;
import ga.aninf.stock.service.dto.EntrepotDTO;
import ga.aninf.stock.service.mapper.EntrepotMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EntrepotResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EntrepotResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    private static final Integer DEFAULT_CAPACITE = 1;
    private static final Integer UPDATED_CAPACITE = 2;
    private static final Integer SMALLER_CAPACITE = 1 - 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DELETE_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETE_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/entrepots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EntrepotRepository entrepotRepository;

    @Mock
    private EntrepotRepository entrepotRepositoryMock;

    @Autowired
    private EntrepotMapper entrepotMapper;

    @Mock
    private EntrepotService entrepotServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntrepotMockMvc;

    private Entrepot entrepot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrepot createEntity(EntityManager em) {
        Entrepot entrepot = new Entrepot()
            .libelle(DEFAULT_LIBELLE)
            .slug(DEFAULT_SLUG)
            .capacite(DEFAULT_CAPACITE)
            .description(DEFAULT_DESCRIPTION)
            .deleteAt(DEFAULT_DELETE_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return entrepot;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrepot createUpdatedEntity(EntityManager em) {
        Entrepot entrepot = new Entrepot()
            .libelle(UPDATED_LIBELLE)
            .slug(UPDATED_SLUG)
            .capacite(UPDATED_CAPACITE)
            .description(UPDATED_DESCRIPTION)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return entrepot;
    }

    @BeforeEach
    public void initTest() {
        entrepot = createEntity(em);
    }

    @Test
    @Transactional
    void createEntrepot() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Entrepot
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);
        var returnedEntrepotDTO = om.readValue(
            restEntrepotMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrepotDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EntrepotDTO.class
        );

        // Validate the Entrepot in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEntrepot = entrepotMapper.toEntity(returnedEntrepotDTO);
        assertEntrepotUpdatableFieldsEquals(returnedEntrepot, getPersistedEntrepot(returnedEntrepot));
    }

    @Test
    @Transactional
    void createEntrepotWithExistingId() throws Exception {
        // Create the Entrepot with an existing ID
        entrepotRepository.saveAndFlush(entrepot);
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrepotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entrepot in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entrepot.setLibelle(null);

        // Create the Entrepot, which fails.
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        restEntrepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrepotDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSlugIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entrepot.setSlug(null);

        // Create the Entrepot, which fails.
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        restEntrepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrepotDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCapaciteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entrepot.setCapacite(null);

        // Create the Entrepot, which fails.
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        restEntrepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrepotDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entrepot.setCreatedBy(null);

        // Create the Entrepot, which fails.
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        restEntrepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrepotDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntrepots() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList
        restEntrepotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrepot.getId().toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntrepotsWithEagerRelationshipsIsEnabled() throws Exception {
        when(entrepotServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEntrepotMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(entrepotServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEntrepotsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(entrepotServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEntrepotMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(entrepotRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEntrepot() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get the entrepot
        restEntrepotMockMvc
            .perform(get(ENTITY_API_URL_ID, entrepot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entrepot.getId().toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG))
            .andExpect(jsonPath("$.capacite").value(DEFAULT_CAPACITE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.deleteAt").value(DEFAULT_DELETE_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getEntrepotsByIdFiltering() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        UUID id = entrepot.getId();

        defaultEntrepotFiltering("id.equals=" + id, "id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllEntrepotsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where libelle equals to
        defaultEntrepotFiltering("libelle.equals=" + DEFAULT_LIBELLE, "libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where libelle in
        defaultEntrepotFiltering("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE, "libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where libelle is not null
        defaultEntrepotFiltering("libelle.specified=true", "libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllEntrepotsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where libelle contains
        defaultEntrepotFiltering("libelle.contains=" + DEFAULT_LIBELLE, "libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where libelle does not contain
        defaultEntrepotFiltering("libelle.doesNotContain=" + UPDATED_LIBELLE, "libelle.doesNotContain=" + DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void getAllEntrepotsBySlugIsEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where slug equals to
        defaultEntrepotFiltering("slug.equals=" + DEFAULT_SLUG, "slug.equals=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    void getAllEntrepotsBySlugIsInShouldWork() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where slug in
        defaultEntrepotFiltering("slug.in=" + DEFAULT_SLUG + "," + UPDATED_SLUG, "slug.in=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    void getAllEntrepotsBySlugIsNullOrNotNull() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where slug is not null
        defaultEntrepotFiltering("slug.specified=true", "slug.specified=false");
    }

    @Test
    @Transactional
    void getAllEntrepotsBySlugContainsSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where slug contains
        defaultEntrepotFiltering("slug.contains=" + DEFAULT_SLUG, "slug.contains=" + UPDATED_SLUG);
    }

    @Test
    @Transactional
    void getAllEntrepotsBySlugNotContainsSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where slug does not contain
        defaultEntrepotFiltering("slug.doesNotContain=" + UPDATED_SLUG, "slug.doesNotContain=" + DEFAULT_SLUG);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCapaciteIsEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where capacite equals to
        defaultEntrepotFiltering("capacite.equals=" + DEFAULT_CAPACITE, "capacite.equals=" + UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCapaciteIsInShouldWork() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where capacite in
        defaultEntrepotFiltering("capacite.in=" + DEFAULT_CAPACITE + "," + UPDATED_CAPACITE, "capacite.in=" + UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCapaciteIsNullOrNotNull() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where capacite is not null
        defaultEntrepotFiltering("capacite.specified=true", "capacite.specified=false");
    }

    @Test
    @Transactional
    void getAllEntrepotsByCapaciteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where capacite is greater than or equal to
        defaultEntrepotFiltering("capacite.greaterThanOrEqual=" + DEFAULT_CAPACITE, "capacite.greaterThanOrEqual=" + UPDATED_CAPACITE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCapaciteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where capacite is less than or equal to
        defaultEntrepotFiltering("capacite.lessThanOrEqual=" + DEFAULT_CAPACITE, "capacite.lessThanOrEqual=" + SMALLER_CAPACITE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCapaciteIsLessThanSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where capacite is less than
        defaultEntrepotFiltering("capacite.lessThan=" + UPDATED_CAPACITE, "capacite.lessThan=" + DEFAULT_CAPACITE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCapaciteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where capacite is greater than
        defaultEntrepotFiltering("capacite.greaterThan=" + SMALLER_CAPACITE, "capacite.greaterThan=" + DEFAULT_CAPACITE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByDeleteAtIsEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where deleteAt equals to
        defaultEntrepotFiltering("deleteAt.equals=" + DEFAULT_DELETE_AT, "deleteAt.equals=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllEntrepotsByDeleteAtIsInShouldWork() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where deleteAt in
        defaultEntrepotFiltering("deleteAt.in=" + DEFAULT_DELETE_AT + "," + UPDATED_DELETE_AT, "deleteAt.in=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllEntrepotsByDeleteAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where deleteAt is not null
        defaultEntrepotFiltering("deleteAt.specified=true", "deleteAt.specified=false");
    }

    @Test
    @Transactional
    void getAllEntrepotsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where createdBy equals to
        defaultEntrepotFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where createdBy in
        defaultEntrepotFiltering("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY, "createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where createdBy is not null
        defaultEntrepotFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEntrepotsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where createdBy contains
        defaultEntrepotFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where createdBy does not contain
        defaultEntrepotFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where createdDate equals to
        defaultEntrepotFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllEntrepotsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where createdDate in
        defaultEntrepotFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllEntrepotsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where createdDate is not null
        defaultEntrepotFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEntrepotsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where lastModifiedBy equals to
        defaultEntrepotFiltering("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY, "lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEntrepotsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where lastModifiedBy in
        defaultEntrepotFiltering(
            "lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllEntrepotsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where lastModifiedBy is not null
        defaultEntrepotFiltering("lastModifiedBy.specified=true", "lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEntrepotsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where lastModifiedBy contains
        defaultEntrepotFiltering(
            "lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllEntrepotsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where lastModifiedBy does not contain
        defaultEntrepotFiltering(
            "lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllEntrepotsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where lastModifiedDate equals to
        defaultEntrepotFiltering(
            "lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE,
            "lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllEntrepotsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where lastModifiedDate in
        defaultEntrepotFiltering(
            "lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE,
            "lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllEntrepotsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        // Get all the entrepotList where lastModifiedDate is not null
        defaultEntrepotFiltering("lastModifiedDate.specified=true", "lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEntrepotsByStructureIsEqualToSomething() throws Exception {
        Structure structure;
        if (TestUtil.findAll(em, Structure.class).isEmpty()) {
            entrepotRepository.saveAndFlush(entrepot);
            structure = StructureResourceIT.createEntity(em);
        } else {
            structure = TestUtil.findAll(em, Structure.class).get(0);
        }
        em.persist(structure);
        em.flush();
        entrepot.setStructure(structure);
        entrepotRepository.saveAndFlush(entrepot);
        UUID structureId = structure.getId();
        // Get all the entrepotList where structure equals to structureId
        defaultEntrepotShouldBeFound("structureId.equals=" + structureId);

        // Get all the entrepotList where structure equals to UUID.randomUUID()
        defaultEntrepotShouldNotBeFound("structureId.equals=" + UUID.randomUUID());
    }

    private void defaultEntrepotFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultEntrepotShouldBeFound(shouldBeFound);
        defaultEntrepotShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEntrepotShouldBeFound(String filter) throws Exception {
        restEntrepotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrepot.getId().toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)))
            .andExpect(jsonPath("$.[*].capacite").value(hasItem(DEFAULT_CAPACITE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restEntrepotMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEntrepotShouldNotBeFound(String filter) throws Exception {
        restEntrepotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEntrepotMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEntrepot() throws Exception {
        // Get the entrepot
        restEntrepotMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntrepot() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entrepot
        Entrepot updatedEntrepot = entrepotRepository.findById(entrepot.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntrepot are not directly saved in db
        em.detach(updatedEntrepot);
        updatedEntrepot
            .libelle(UPDATED_LIBELLE)
            .slug(UPDATED_SLUG)
            .capacite(UPDATED_CAPACITE)
            .description(UPDATED_DESCRIPTION)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(updatedEntrepot);

        restEntrepotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entrepotDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entrepotDTO))
            )
            .andExpect(status().isOk());

        // Validate the Entrepot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEntrepotToMatchAllProperties(updatedEntrepot);
    }

    @Test
    @Transactional
    void putNonExistingEntrepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrepot.setId(UUID.randomUUID());

        // Create the Entrepot
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entrepotDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entrepotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrepot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntrepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrepot.setId(UUID.randomUUID());

        // Create the Entrepot
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrepotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrepot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntrepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrepot.setId(UUID.randomUUID());

        // Create the Entrepot
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepotMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrepotDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entrepot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntrepotWithPatch() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entrepot using partial update
        Entrepot partialUpdatedEntrepot = new Entrepot();
        partialUpdatedEntrepot.setId(entrepot.getId());

        partialUpdatedEntrepot.slug(UPDATED_SLUG).createdBy(UPDATED_CREATED_BY).lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restEntrepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntrepot.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntrepot))
            )
            .andExpect(status().isOk());

        // Validate the Entrepot in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntrepotUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedEntrepot, entrepot), getPersistedEntrepot(entrepot));
    }

    @Test
    @Transactional
    void fullUpdateEntrepotWithPatch() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entrepot using partial update
        Entrepot partialUpdatedEntrepot = new Entrepot();
        partialUpdatedEntrepot.setId(entrepot.getId());

        partialUpdatedEntrepot
            .libelle(UPDATED_LIBELLE)
            .slug(UPDATED_SLUG)
            .capacite(UPDATED_CAPACITE)
            .description(UPDATED_DESCRIPTION)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restEntrepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntrepot.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntrepot))
            )
            .andExpect(status().isOk());

        // Validate the Entrepot in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntrepotUpdatableFieldsEquals(partialUpdatedEntrepot, getPersistedEntrepot(partialUpdatedEntrepot));
    }

    @Test
    @Transactional
    void patchNonExistingEntrepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrepot.setId(UUID.randomUUID());

        // Create the Entrepot
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entrepotDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entrepotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrepot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntrepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrepot.setId(UUID.randomUUID());

        // Create the Entrepot
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entrepotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrepot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntrepot() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrepot.setId(UUID.randomUUID());

        // Create the Entrepot
        EntrepotDTO entrepotDTO = entrepotMapper.toDto(entrepot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepotMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(entrepotDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entrepot in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntrepot() throws Exception {
        // Initialize the database
        entrepotRepository.saveAndFlush(entrepot);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the entrepot
        restEntrepotMockMvc
            .perform(delete(ENTITY_API_URL_ID, entrepot.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return entrepotRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Entrepot getPersistedEntrepot(Entrepot entrepot) {
        return entrepotRepository.findById(entrepot.getId()).orElseThrow();
    }

    protected void assertPersistedEntrepotToMatchAllProperties(Entrepot expectedEntrepot) {
        assertEntrepotAllPropertiesEquals(expectedEntrepot, getPersistedEntrepot(expectedEntrepot));
    }

    protected void assertPersistedEntrepotToMatchUpdatableProperties(Entrepot expectedEntrepot) {
        assertEntrepotAllUpdatablePropertiesEquals(expectedEntrepot, getPersistedEntrepot(expectedEntrepot));
    }
}
