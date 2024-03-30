package ga.aninf.stock.web.rest;

import static ga.aninf.stock.domain.PlansAbonnementAsserts.*;
import static ga.aninf.stock.web.rest.TestUtil.createUpdateProxyForBean;
import static ga.aninf.stock.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import ga.aninf.stock.IntegrationTest;
import ga.aninf.stock.domain.PlansAbonnement;
import ga.aninf.stock.repository.PlansAbonnementRepository;
import ga.aninf.stock.service.dto.PlansAbonnementDTO;
import ga.aninf.stock.service.mapper.PlansAbonnementMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlansAbonnementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlansAbonnementResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRIX = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRIX = new BigDecimal(1 - 1);

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    private static final String DEFAULT_AVANTAGE = "AAAAAAAAAA";
    private static final String UPDATED_AVANTAGE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/plans-abonnements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlansAbonnementRepository plansAbonnementRepository;

    @Autowired
    private PlansAbonnementMapper plansAbonnementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlansAbonnementMockMvc;

    private PlansAbonnement plansAbonnement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlansAbonnement createEntity(EntityManager em) {
        PlansAbonnement plansAbonnement = new PlansAbonnement()
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .prix(DEFAULT_PRIX)
            .duree(DEFAULT_DUREE)
            .avantage(DEFAULT_AVANTAGE)
            .deleteAt(DEFAULT_DELETE_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return plansAbonnement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlansAbonnement createUpdatedEntity(EntityManager em) {
        PlansAbonnement plansAbonnement = new PlansAbonnement()
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .duree(UPDATED_DUREE)
            .avantage(UPDATED_AVANTAGE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return plansAbonnement;
    }

    @BeforeEach
    public void initTest() {
        plansAbonnement = createEntity(em);
    }

    @Test
    @Transactional
    void createPlansAbonnement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlansAbonnement
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);
        var returnedPlansAbonnementDTO = om.readValue(
            restPlansAbonnementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plansAbonnementDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlansAbonnementDTO.class
        );

        // Validate the PlansAbonnement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPlansAbonnement = plansAbonnementMapper.toEntity(returnedPlansAbonnementDTO);
        assertPlansAbonnementUpdatableFieldsEquals(returnedPlansAbonnement, getPersistedPlansAbonnement(returnedPlansAbonnement));
    }

    @Test
    @Transactional
    void createPlansAbonnementWithExistingId() throws Exception {
        // Create the PlansAbonnement with an existing ID
        plansAbonnementRepository.saveAndFlush(plansAbonnement);
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlansAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plansAbonnementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlansAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        plansAbonnement.setLibelle(null);

        // Create the PlansAbonnement, which fails.
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        restPlansAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plansAbonnementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        plansAbonnement.setPrix(null);

        // Create the PlansAbonnement, which fails.
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        restPlansAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plansAbonnementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        plansAbonnement.setCreatedBy(null);

        // Create the PlansAbonnement, which fails.
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        restPlansAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plansAbonnementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlansAbonnements() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList
        restPlansAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plansAbonnement.getId().toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(sameNumber(DEFAULT_PRIX))))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].avantage").value(hasItem(DEFAULT_AVANTAGE.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPlansAbonnement() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get the plansAbonnement
        restPlansAbonnementMockMvc
            .perform(get(ENTITY_API_URL_ID, plansAbonnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plansAbonnement.getId().toString()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.prix").value(sameNumber(DEFAULT_PRIX)))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.avantage").value(DEFAULT_AVANTAGE.toString()))
            .andExpect(jsonPath("$.deleteAt").value(DEFAULT_DELETE_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getPlansAbonnementsByIdFiltering() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        UUID id = plansAbonnement.getId();

        defaultPlansAbonnementFiltering("id.equals=" + id, "id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where libelle equals to
        defaultPlansAbonnementFiltering("libelle.equals=" + DEFAULT_LIBELLE, "libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where libelle in
        defaultPlansAbonnementFiltering("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE, "libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where libelle is not null
        defaultPlansAbonnementFiltering("libelle.specified=true", "libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where libelle contains
        defaultPlansAbonnementFiltering("libelle.contains=" + DEFAULT_LIBELLE, "libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where libelle does not contain
        defaultPlansAbonnementFiltering("libelle.doesNotContain=" + UPDATED_LIBELLE, "libelle.doesNotContain=" + DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where prix equals to
        defaultPlansAbonnementFiltering("prix.equals=" + DEFAULT_PRIX, "prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where prix in
        defaultPlansAbonnementFiltering("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX, "prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where prix is not null
        defaultPlansAbonnementFiltering("prix.specified=true", "prix.specified=false");
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where prix is greater than or equal to
        defaultPlansAbonnementFiltering("prix.greaterThanOrEqual=" + DEFAULT_PRIX, "prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where prix is less than or equal to
        defaultPlansAbonnementFiltering("prix.lessThanOrEqual=" + DEFAULT_PRIX, "prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where prix is less than
        defaultPlansAbonnementFiltering("prix.lessThan=" + UPDATED_PRIX, "prix.lessThan=" + DEFAULT_PRIX);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where prix is greater than
        defaultPlansAbonnementFiltering("prix.greaterThan=" + SMALLER_PRIX, "prix.greaterThan=" + DEFAULT_PRIX);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByDureeIsEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where duree equals to
        defaultPlansAbonnementFiltering("duree.equals=" + DEFAULT_DUREE, "duree.equals=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByDureeIsInShouldWork() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where duree in
        defaultPlansAbonnementFiltering("duree.in=" + DEFAULT_DUREE + "," + UPDATED_DUREE, "duree.in=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByDureeIsNullOrNotNull() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where duree is not null
        defaultPlansAbonnementFiltering("duree.specified=true", "duree.specified=false");
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByDureeContainsSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where duree contains
        defaultPlansAbonnementFiltering("duree.contains=" + DEFAULT_DUREE, "duree.contains=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByDureeNotContainsSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where duree does not contain
        defaultPlansAbonnementFiltering("duree.doesNotContain=" + UPDATED_DUREE, "duree.doesNotContain=" + DEFAULT_DUREE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByDeleteAtIsEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where deleteAt equals to
        defaultPlansAbonnementFiltering("deleteAt.equals=" + DEFAULT_DELETE_AT, "deleteAt.equals=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByDeleteAtIsInShouldWork() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where deleteAt in
        defaultPlansAbonnementFiltering("deleteAt.in=" + DEFAULT_DELETE_AT + "," + UPDATED_DELETE_AT, "deleteAt.in=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByDeleteAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where deleteAt is not null
        defaultPlansAbonnementFiltering("deleteAt.specified=true", "deleteAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where createdBy equals to
        defaultPlansAbonnementFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where createdBy in
        defaultPlansAbonnementFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where createdBy is not null
        defaultPlansAbonnementFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where createdBy contains
        defaultPlansAbonnementFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where createdBy does not contain
        defaultPlansAbonnementFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where createdDate equals to
        defaultPlansAbonnementFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where createdDate in
        defaultPlansAbonnementFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where createdDate is not null
        defaultPlansAbonnementFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where lastModifiedBy equals to
        defaultPlansAbonnementFiltering(
            "lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where lastModifiedBy in
        defaultPlansAbonnementFiltering(
            "lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where lastModifiedBy is not null
        defaultPlansAbonnementFiltering("lastModifiedBy.specified=true", "lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where lastModifiedBy contains
        defaultPlansAbonnementFiltering(
            "lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where lastModifiedBy does not contain
        defaultPlansAbonnementFiltering(
            "lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where lastModifiedDate equals to
        defaultPlansAbonnementFiltering(
            "lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE,
            "lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where lastModifiedDate in
        defaultPlansAbonnementFiltering(
            "lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE,
            "lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllPlansAbonnementsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        // Get all the plansAbonnementList where lastModifiedDate is not null
        defaultPlansAbonnementFiltering("lastModifiedDate.specified=true", "lastModifiedDate.specified=false");
    }

    private void defaultPlansAbonnementFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultPlansAbonnementShouldBeFound(shouldBeFound);
        defaultPlansAbonnementShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPlansAbonnementShouldBeFound(String filter) throws Exception {
        restPlansAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plansAbonnement.getId().toString())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(sameNumber(DEFAULT_PRIX))))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].avantage").value(hasItem(DEFAULT_AVANTAGE.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restPlansAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPlansAbonnementShouldNotBeFound(String filter) throws Exception {
        restPlansAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPlansAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPlansAbonnement() throws Exception {
        // Get the plansAbonnement
        restPlansAbonnementMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlansAbonnement() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plansAbonnement
        PlansAbonnement updatedPlansAbonnement = plansAbonnementRepository.findById(plansAbonnement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlansAbonnement are not directly saved in db
        em.detach(updatedPlansAbonnement);
        updatedPlansAbonnement
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .duree(UPDATED_DUREE)
            .avantage(UPDATED_AVANTAGE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(updatedPlansAbonnement);

        restPlansAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plansAbonnementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plansAbonnementDTO))
            )
            .andExpect(status().isOk());

        // Validate the PlansAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlansAbonnementToMatchAllProperties(updatedPlansAbonnement);
    }

    @Test
    @Transactional
    void putNonExistingPlansAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plansAbonnement.setId(UUID.randomUUID());

        // Create the PlansAbonnement
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlansAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plansAbonnementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plansAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlansAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlansAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plansAbonnement.setId(UUID.randomUUID());

        // Create the PlansAbonnement
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlansAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plansAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlansAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlansAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plansAbonnement.setId(UUID.randomUUID());

        // Create the PlansAbonnement
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlansAbonnementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plansAbonnementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlansAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlansAbonnementWithPatch() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plansAbonnement using partial update
        PlansAbonnement partialUpdatedPlansAbonnement = new PlansAbonnement();
        partialUpdatedPlansAbonnement.setId(plansAbonnement.getId());

        partialUpdatedPlansAbonnement
            .libelle(UPDATED_LIBELLE)
            .avantage(UPDATED_AVANTAGE)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restPlansAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlansAbonnement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlansAbonnement))
            )
            .andExpect(status().isOk());

        // Validate the PlansAbonnement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlansAbonnementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlansAbonnement, plansAbonnement),
            getPersistedPlansAbonnement(plansAbonnement)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlansAbonnementWithPatch() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plansAbonnement using partial update
        PlansAbonnement partialUpdatedPlansAbonnement = new PlansAbonnement();
        partialUpdatedPlansAbonnement.setId(plansAbonnement.getId());

        partialUpdatedPlansAbonnement
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .prix(UPDATED_PRIX)
            .duree(UPDATED_DUREE)
            .avantage(UPDATED_AVANTAGE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restPlansAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlansAbonnement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlansAbonnement))
            )
            .andExpect(status().isOk());

        // Validate the PlansAbonnement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlansAbonnementUpdatableFieldsEquals(
            partialUpdatedPlansAbonnement,
            getPersistedPlansAbonnement(partialUpdatedPlansAbonnement)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPlansAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plansAbonnement.setId(UUID.randomUUID());

        // Create the PlansAbonnement
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlansAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plansAbonnementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(plansAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlansAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlansAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plansAbonnement.setId(UUID.randomUUID());

        // Create the PlansAbonnement
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlansAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(plansAbonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlansAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlansAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plansAbonnement.setId(UUID.randomUUID());

        // Create the PlansAbonnement
        PlansAbonnementDTO plansAbonnementDTO = plansAbonnementMapper.toDto(plansAbonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlansAbonnementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(plansAbonnementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlansAbonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlansAbonnement() throws Exception {
        // Initialize the database
        plansAbonnementRepository.saveAndFlush(plansAbonnement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the plansAbonnement
        restPlansAbonnementMockMvc
            .perform(delete(ENTITY_API_URL_ID, plansAbonnement.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return plansAbonnementRepository.count();
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

    protected PlansAbonnement getPersistedPlansAbonnement(PlansAbonnement plansAbonnement) {
        return plansAbonnementRepository.findById(plansAbonnement.getId()).orElseThrow();
    }

    protected void assertPersistedPlansAbonnementToMatchAllProperties(PlansAbonnement expectedPlansAbonnement) {
        assertPlansAbonnementAllPropertiesEquals(expectedPlansAbonnement, getPersistedPlansAbonnement(expectedPlansAbonnement));
    }

    protected void assertPersistedPlansAbonnementToMatchUpdatableProperties(PlansAbonnement expectedPlansAbonnement) {
        assertPlansAbonnementAllUpdatablePropertiesEquals(expectedPlansAbonnement, getPersistedPlansAbonnement(expectedPlansAbonnement));
    }
}
