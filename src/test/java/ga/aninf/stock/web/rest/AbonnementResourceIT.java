package ga.aninf.stock.web.rest;

import static ga.aninf.stock.domain.AbonnementAsserts.*;
import static ga.aninf.stock.web.rest.TestUtil.createUpdateProxyForBean;
import static ga.aninf.stock.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import ga.aninf.stock.IntegrationTest;
import ga.aninf.stock.domain.Abonnement;
import ga.aninf.stock.domain.Paiement;
import ga.aninf.stock.domain.PlansAbonnement;
import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.domain.enumeration.StatutAbonnement;
import ga.aninf.stock.repository.AbonnementRepository;
import ga.aninf.stock.service.AbonnementService;
import ga.aninf.stock.service.dto.AbonnementDTO;
import ga.aninf.stock.service.mapper.AbonnementMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link AbonnementResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AbonnementResourceIT {

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatutAbonnement DEFAULT_STATUT_ABONNEMENT = StatutAbonnement.ACTIF;
    private static final StatutAbonnement UPDATED_STATUT_ABONNEMENT = StatutAbonnement.EXPIRE;

    private static final BigDecimal DEFAULT_PRIX = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRIX = new BigDecimal(1 - 1);

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

    private static final String ENTITY_API_URL = "/api/abonnements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Mock
    private AbonnementRepository abonnementRepositoryMock;

    @Autowired
    private AbonnementMapper abonnementMapper;

    @Mock
    private AbonnementService abonnementServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAbonnementMockMvc;

    private Abonnement abonnement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Abonnement createEntity(EntityManager em) {
        Abonnement abonnement = new Abonnement()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .statutAbonnement(DEFAULT_STATUT_ABONNEMENT)
            .prix(DEFAULT_PRIX)
            .deleteAt(DEFAULT_DELETE_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        // Add required entity
        Structure structure;
        if (TestUtil.findAll(em, Structure.class).isEmpty()) {
            structure = StructureResourceIT.createEntity(em);
            em.persist(structure);
            em.flush();
        } else {
            structure = TestUtil.findAll(em, Structure.class).get(0);
        }
        abonnement.setStructure(structure);
        // Add required entity
        PlansAbonnement plansAbonnement;
        if (TestUtil.findAll(em, PlansAbonnement.class).isEmpty()) {
            plansAbonnement = PlansAbonnementResourceIT.createEntity(em);
            em.persist(plansAbonnement);
            em.flush();
        } else {
            plansAbonnement = TestUtil.findAll(em, PlansAbonnement.class).get(0);
        }
        abonnement.setPlansAbonnement(plansAbonnement);
        // Add required entity
        Paiement paiement;
        if (TestUtil.findAll(em, Paiement.class).isEmpty()) {
            paiement = PaiementResourceIT.createEntity(em);
            em.persist(paiement);
            em.flush();
        } else {
            paiement = TestUtil.findAll(em, Paiement.class).get(0);
        }
        abonnement.setPaiement(paiement);
        return abonnement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Abonnement createUpdatedEntity(EntityManager em) {
        Abonnement abonnement = new Abonnement()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .statutAbonnement(UPDATED_STATUT_ABONNEMENT)
            .prix(UPDATED_PRIX)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        // Add required entity
        Structure structure;
        if (TestUtil.findAll(em, Structure.class).isEmpty()) {
            structure = StructureResourceIT.createUpdatedEntity(em);
            em.persist(structure);
            em.flush();
        } else {
            structure = TestUtil.findAll(em, Structure.class).get(0);
        }
        abonnement.setStructure(structure);
        // Add required entity
        PlansAbonnement plansAbonnement;
        if (TestUtil.findAll(em, PlansAbonnement.class).isEmpty()) {
            plansAbonnement = PlansAbonnementResourceIT.createUpdatedEntity(em);
            em.persist(plansAbonnement);
            em.flush();
        } else {
            plansAbonnement = TestUtil.findAll(em, PlansAbonnement.class).get(0);
        }
        abonnement.setPlansAbonnement(plansAbonnement);
        // Add required entity
        Paiement paiement;
        if (TestUtil.findAll(em, Paiement.class).isEmpty()) {
            paiement = PaiementResourceIT.createUpdatedEntity(em);
            em.persist(paiement);
            em.flush();
        } else {
            paiement = TestUtil.findAll(em, Paiement.class).get(0);
        }
        abonnement.setPaiement(paiement);
        return abonnement;
    }

    @BeforeEach
    public void initTest() {
        abonnement = createEntity(em);
    }

    @Test
    @Transactional
    void createAbonnement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Abonnement
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);
        var returnedAbonnementDTO = om.readValue(
            restAbonnementMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonnementDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AbonnementDTO.class
        );

        // Validate the Abonnement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAbonnement = abonnementMapper.toEntity(returnedAbonnementDTO);
        assertAbonnementUpdatableFieldsEquals(returnedAbonnement, getPersistedAbonnement(returnedAbonnement));
    }

    @Test
    @Transactional
    void createAbonnementWithExistingId() throws Exception {
        // Create the Abonnement with an existing ID
        abonnementRepository.saveAndFlush(abonnement);
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonnementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Abonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateDebutIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        abonnement.setDateDebut(null);

        // Create the Abonnement, which fails.
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        restAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonnementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateFinIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        abonnement.setDateFin(null);

        // Create the Abonnement, which fails.
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        restAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonnementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatutAbonnementIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        abonnement.setStatutAbonnement(null);

        // Create the Abonnement, which fails.
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        restAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonnementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        abonnement.setPrix(null);

        // Create the Abonnement, which fails.
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        restAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonnementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        abonnement.setCreatedBy(null);

        // Create the Abonnement, which fails.
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        restAbonnementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonnementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAbonnements() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList
        restAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abonnement.getId().toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].statutAbonnement").value(hasItem(DEFAULT_STATUT_ABONNEMENT.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(sameNumber(DEFAULT_PRIX))))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAbonnementsWithEagerRelationshipsIsEnabled() throws Exception {
        when(abonnementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAbonnementMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(abonnementServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllAbonnementsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(abonnementServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restAbonnementMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(abonnementRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getAbonnement() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get the abonnement
        restAbonnementMockMvc
            .perform(get(ENTITY_API_URL_ID, abonnement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(abonnement.getId().toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.statutAbonnement").value(DEFAULT_STATUT_ABONNEMENT.toString()))
            .andExpect(jsonPath("$.prix").value(sameNumber(DEFAULT_PRIX)))
            .andExpect(jsonPath("$.deleteAt").value(DEFAULT_DELETE_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getAbonnementsByIdFiltering() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        UUID id = abonnement.getId();

        defaultAbonnementFiltering("id.equals=" + id, "id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllAbonnementsByDateDebutIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where dateDebut equals to
        defaultAbonnementFiltering("dateDebut.equals=" + DEFAULT_DATE_DEBUT, "dateDebut.equals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    void getAllAbonnementsByDateDebutIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where dateDebut in
        defaultAbonnementFiltering("dateDebut.in=" + DEFAULT_DATE_DEBUT + "," + UPDATED_DATE_DEBUT, "dateDebut.in=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    void getAllAbonnementsByDateDebutIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where dateDebut is not null
        defaultAbonnementFiltering("dateDebut.specified=true", "dateDebut.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByDateFinIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where dateFin equals to
        defaultAbonnementFiltering("dateFin.equals=" + DEFAULT_DATE_FIN, "dateFin.equals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void getAllAbonnementsByDateFinIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where dateFin in
        defaultAbonnementFiltering("dateFin.in=" + DEFAULT_DATE_FIN + "," + UPDATED_DATE_FIN, "dateFin.in=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    void getAllAbonnementsByDateFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where dateFin is not null
        defaultAbonnementFiltering("dateFin.specified=true", "dateFin.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByStatutAbonnementIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where statutAbonnement equals to
        defaultAbonnementFiltering(
            "statutAbonnement.equals=" + DEFAULT_STATUT_ABONNEMENT,
            "statutAbonnement.equals=" + UPDATED_STATUT_ABONNEMENT
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByStatutAbonnementIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where statutAbonnement in
        defaultAbonnementFiltering(
            "statutAbonnement.in=" + DEFAULT_STATUT_ABONNEMENT + "," + UPDATED_STATUT_ABONNEMENT,
            "statutAbonnement.in=" + UPDATED_STATUT_ABONNEMENT
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByStatutAbonnementIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where statutAbonnement is not null
        defaultAbonnementFiltering("statutAbonnement.specified=true", "statutAbonnement.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByPrixIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where prix equals to
        defaultAbonnementFiltering("prix.equals=" + DEFAULT_PRIX, "prix.equals=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    void getAllAbonnementsByPrixIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where prix in
        defaultAbonnementFiltering("prix.in=" + DEFAULT_PRIX + "," + UPDATED_PRIX, "prix.in=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    void getAllAbonnementsByPrixIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where prix is not null
        defaultAbonnementFiltering("prix.specified=true", "prix.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByPrixIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where prix is greater than or equal to
        defaultAbonnementFiltering("prix.greaterThanOrEqual=" + DEFAULT_PRIX, "prix.greaterThanOrEqual=" + UPDATED_PRIX);
    }

    @Test
    @Transactional
    void getAllAbonnementsByPrixIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where prix is less than or equal to
        defaultAbonnementFiltering("prix.lessThanOrEqual=" + DEFAULT_PRIX, "prix.lessThanOrEqual=" + SMALLER_PRIX);
    }

    @Test
    @Transactional
    void getAllAbonnementsByPrixIsLessThanSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where prix is less than
        defaultAbonnementFiltering("prix.lessThan=" + UPDATED_PRIX, "prix.lessThan=" + DEFAULT_PRIX);
    }

    @Test
    @Transactional
    void getAllAbonnementsByPrixIsGreaterThanSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where prix is greater than
        defaultAbonnementFiltering("prix.greaterThan=" + SMALLER_PRIX, "prix.greaterThan=" + DEFAULT_PRIX);
    }

    @Test
    @Transactional
    void getAllAbonnementsByDeleteAtIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where deleteAt equals to
        defaultAbonnementFiltering("deleteAt.equals=" + DEFAULT_DELETE_AT, "deleteAt.equals=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllAbonnementsByDeleteAtIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where deleteAt in
        defaultAbonnementFiltering("deleteAt.in=" + DEFAULT_DELETE_AT + "," + UPDATED_DELETE_AT, "deleteAt.in=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllAbonnementsByDeleteAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where deleteAt is not null
        defaultAbonnementFiltering("deleteAt.specified=true", "deleteAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where createdBy equals to
        defaultAbonnementFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAbonnementsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where createdBy in
        defaultAbonnementFiltering("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY, "createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAbonnementsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where createdBy is not null
        defaultAbonnementFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where createdBy contains
        defaultAbonnementFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAbonnementsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where createdBy does not contain
        defaultAbonnementFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAbonnementsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where createdDate equals to
        defaultAbonnementFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllAbonnementsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where createdDate in
        defaultAbonnementFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where createdDate is not null
        defaultAbonnementFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where lastModifiedBy equals to
        defaultAbonnementFiltering(
            "lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where lastModifiedBy in
        defaultAbonnementFiltering(
            "lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where lastModifiedBy is not null
        defaultAbonnementFiltering("lastModifiedBy.specified=true", "lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where lastModifiedBy contains
        defaultAbonnementFiltering(
            "lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where lastModifiedBy does not contain
        defaultAbonnementFiltering(
            "lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where lastModifiedDate equals to
        defaultAbonnementFiltering(
            "lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE,
            "lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where lastModifiedDate in
        defaultAbonnementFiltering(
            "lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE,
            "lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllAbonnementsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        // Get all the abonnementList where lastModifiedDate is not null
        defaultAbonnementFiltering("lastModifiedDate.specified=true", "lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAbonnementsByStructureIsEqualToSomething() throws Exception {
        Structure structure;
        if (TestUtil.findAll(em, Structure.class).isEmpty()) {
            abonnementRepository.saveAndFlush(abonnement);
            structure = StructureResourceIT.createEntity(em);
        } else {
            structure = TestUtil.findAll(em, Structure.class).get(0);
        }
        em.persist(structure);
        em.flush();
        abonnement.setStructure(structure);
        abonnementRepository.saveAndFlush(abonnement);
        UUID structureId = structure.getId();
        // Get all the abonnementList where structure equals to structureId
        defaultAbonnementShouldBeFound("structureId.equals=" + structureId);

        // Get all the abonnementList where structure equals to UUID.randomUUID()
        defaultAbonnementShouldNotBeFound("structureId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllAbonnementsByPlansAbonnementIsEqualToSomething() throws Exception {
        PlansAbonnement plansAbonnement;
        if (TestUtil.findAll(em, PlansAbonnement.class).isEmpty()) {
            abonnementRepository.saveAndFlush(abonnement);
            plansAbonnement = PlansAbonnementResourceIT.createEntity(em);
        } else {
            plansAbonnement = TestUtil.findAll(em, PlansAbonnement.class).get(0);
        }
        em.persist(plansAbonnement);
        em.flush();
        abonnement.setPlansAbonnement(plansAbonnement);
        abonnementRepository.saveAndFlush(abonnement);
        UUID plansAbonnementId = plansAbonnement.getId();
        // Get all the abonnementList where plansAbonnement equals to plansAbonnementId
        defaultAbonnementShouldBeFound("plansAbonnementId.equals=" + plansAbonnementId);

        // Get all the abonnementList where plansAbonnement equals to UUID.randomUUID()
        defaultAbonnementShouldNotBeFound("plansAbonnementId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllAbonnementsByPaiementIsEqualToSomething() throws Exception {
        Paiement paiement;
        if (TestUtil.findAll(em, Paiement.class).isEmpty()) {
            abonnementRepository.saveAndFlush(abonnement);
            paiement = PaiementResourceIT.createEntity(em);
        } else {
            paiement = TestUtil.findAll(em, Paiement.class).get(0);
        }
        em.persist(paiement);
        em.flush();
        abonnement.setPaiement(paiement);
        abonnementRepository.saveAndFlush(abonnement);
        UUID paiementId = paiement.getId();
        // Get all the abonnementList where paiement equals to paiementId
        defaultAbonnementShouldBeFound("paiementId.equals=" + paiementId);

        // Get all the abonnementList where paiement equals to UUID.randomUUID()
        defaultAbonnementShouldNotBeFound("paiementId.equals=" + UUID.randomUUID());
    }

    private void defaultAbonnementFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultAbonnementShouldBeFound(shouldBeFound);
        defaultAbonnementShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAbonnementShouldBeFound(String filter) throws Exception {
        restAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abonnement.getId().toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].statutAbonnement").value(hasItem(DEFAULT_STATUT_ABONNEMENT.toString())))
            .andExpect(jsonPath("$.[*].prix").value(hasItem(sameNumber(DEFAULT_PRIX))))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAbonnementShouldNotBeFound(String filter) throws Exception {
        restAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAbonnementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAbonnement() throws Exception {
        // Get the abonnement
        restAbonnementMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAbonnement() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the abonnement
        Abonnement updatedAbonnement = abonnementRepository.findById(abonnement.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAbonnement are not directly saved in db
        em.detach(updatedAbonnement);
        updatedAbonnement
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .statutAbonnement(UPDATED_STATUT_ABONNEMENT)
            .prix(UPDATED_PRIX)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(updatedAbonnement);

        restAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, abonnementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(abonnementDTO))
            )
            .andExpect(status().isOk());

        // Validate the Abonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAbonnementToMatchAllProperties(updatedAbonnement);
    }

    @Test
    @Transactional
    void putNonExistingAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonnement.setId(UUID.randomUUID());

        // Create the Abonnement
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, abonnementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(abonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Abonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonnement.setId(UUID.randomUUID());

        // Create the Abonnement
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbonnementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(abonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Abonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonnement.setId(UUID.randomUUID());

        // Create the Abonnement
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbonnementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(abonnementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Abonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAbonnementWithPatch() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the abonnement using partial update
        Abonnement partialUpdatedAbonnement = new Abonnement();
        partialUpdatedAbonnement.setId(abonnement.getId());

        partialUpdatedAbonnement
            .prix(UPDATED_PRIX)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbonnement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAbonnement))
            )
            .andExpect(status().isOk());

        // Validate the Abonnement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAbonnementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAbonnement, abonnement),
            getPersistedAbonnement(abonnement)
        );
    }

    @Test
    @Transactional
    void fullUpdateAbonnementWithPatch() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the abonnement using partial update
        Abonnement partialUpdatedAbonnement = new Abonnement();
        partialUpdatedAbonnement.setId(abonnement.getId());

        partialUpdatedAbonnement
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .statutAbonnement(UPDATED_STATUT_ABONNEMENT)
            .prix(UPDATED_PRIX)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAbonnement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAbonnement))
            )
            .andExpect(status().isOk());

        // Validate the Abonnement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAbonnementUpdatableFieldsEquals(partialUpdatedAbonnement, getPersistedAbonnement(partialUpdatedAbonnement));
    }

    @Test
    @Transactional
    void patchNonExistingAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonnement.setId(UUID.randomUUID());

        // Create the Abonnement
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, abonnementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(abonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Abonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonnement.setId(UUID.randomUUID());

        // Create the Abonnement
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbonnementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(abonnementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Abonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAbonnement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        abonnement.setId(UUID.randomUUID());

        // Create the Abonnement
        AbonnementDTO abonnementDTO = abonnementMapper.toDto(abonnement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAbonnementMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(abonnementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Abonnement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAbonnement() throws Exception {
        // Initialize the database
        abonnementRepository.saveAndFlush(abonnement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the abonnement
        restAbonnementMockMvc
            .perform(delete(ENTITY_API_URL_ID, abonnement.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return abonnementRepository.count();
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

    protected Abonnement getPersistedAbonnement(Abonnement abonnement) {
        return abonnementRepository.findById(abonnement.getId()).orElseThrow();
    }

    protected void assertPersistedAbonnementToMatchAllProperties(Abonnement expectedAbonnement) {
        assertAbonnementAllPropertiesEquals(expectedAbonnement, getPersistedAbonnement(expectedAbonnement));
    }

    protected void assertPersistedAbonnementToMatchUpdatableProperties(Abonnement expectedAbonnement) {
        assertAbonnementAllUpdatablePropertiesEquals(expectedAbonnement, getPersistedAbonnement(expectedAbonnement));
    }
}
