package ga.aninf.stock.web.rest;

import static ga.aninf.stock.domain.VenteAsserts.*;
import static ga.aninf.stock.web.rest.TestUtil.createUpdateProxyForBean;
import static ga.aninf.stock.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import ga.aninf.stock.IntegrationTest;
import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.domain.Structure;
import ga.aninf.stock.domain.Vente;
import ga.aninf.stock.repository.VenteRepository;
import ga.aninf.stock.service.VenteService;
import ga.aninf.stock.service.dto.VenteDTO;
import ga.aninf.stock.service.mapper.VenteMapper;
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
 * Integration tests for the {@link VenteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VenteResourceIT {

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;
    private static final Long SMALLER_QUANTITE = 1L - 1L;

    private static final BigDecimal DEFAULT_MONTANT = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTANT = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONTANT = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_VENTE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VENTE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/ventes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private VenteRepository venteRepository;

    @Mock
    private VenteRepository venteRepositoryMock;

    @Autowired
    private VenteMapper venteMapper;

    @Mock
    private VenteService venteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVenteMockMvc;

    private Vente vente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vente createEntity(EntityManager em) {
        Vente vente = new Vente()
            .quantite(DEFAULT_QUANTITE)
            .montant(DEFAULT_MONTANT)
            .venteDate(DEFAULT_VENTE_DATE)
            .deleteAt(DEFAULT_DELETE_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        vente.setProduit(produit);
        // Add required entity
        Structure structure;
        if (TestUtil.findAll(em, Structure.class).isEmpty()) {
            structure = StructureResourceIT.createEntity(em);
            em.persist(structure);
            em.flush();
        } else {
            structure = TestUtil.findAll(em, Structure.class).get(0);
        }
        vente.setStructure(structure);
        return vente;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vente createUpdatedEntity(EntityManager em) {
        Vente vente = new Vente()
            .quantite(UPDATED_QUANTITE)
            .montant(UPDATED_MONTANT)
            .venteDate(UPDATED_VENTE_DATE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createUpdatedEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        vente.setProduit(produit);
        // Add required entity
        Structure structure;
        if (TestUtil.findAll(em, Structure.class).isEmpty()) {
            structure = StructureResourceIT.createUpdatedEntity(em);
            em.persist(structure);
            em.flush();
        } else {
            structure = TestUtil.findAll(em, Structure.class).get(0);
        }
        vente.setStructure(structure);
        return vente;
    }

    @BeforeEach
    public void initTest() {
        vente = createEntity(em);
    }

    @Test
    @Transactional
    void createVente() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Vente
        VenteDTO venteDTO = venteMapper.toDto(vente);
        var returnedVenteDTO = om.readValue(
            restVenteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            VenteDTO.class
        );

        // Validate the Vente in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedVente = venteMapper.toEntity(returnedVenteDTO);
        assertVenteUpdatableFieldsEquals(returnedVente, getPersistedVente(returnedVente));
    }

    @Test
    @Transactional
    void createVenteWithExistingId() throws Exception {
        // Create the Vente with an existing ID
        venteRepository.saveAndFlush(vente);
        VenteDTO venteDTO = venteMapper.toDto(vente);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vente in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantiteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vente.setQuantite(null);

        // Create the Vente, which fails.
        VenteDTO venteDTO = venteMapper.toDto(vente);

        restVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontantIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vente.setMontant(null);

        // Create the Vente, which fails.
        VenteDTO venteDTO = venteMapper.toDto(vente);

        restVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVenteDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vente.setVenteDate(null);

        // Create the Vente, which fails.
        VenteDTO venteDTO = venteMapper.toDto(vente);

        restVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        vente.setCreatedBy(null);

        // Create the Vente, which fails.
        VenteDTO venteDTO = venteMapper.toDto(vente);

        restVenteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVentes() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList
        restVenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vente.getId().toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(sameNumber(DEFAULT_MONTANT))))
            .andExpect(jsonPath("$.[*].venteDate").value(hasItem(DEFAULT_VENTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVentesWithEagerRelationshipsIsEnabled() throws Exception {
        when(venteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(venteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVentesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(venteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVenteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(venteRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getVente() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get the vente
        restVenteMockMvc
            .perform(get(ENTITY_API_URL_ID, vente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vente.getId().toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.montant").value(sameNumber(DEFAULT_MONTANT)))
            .andExpect(jsonPath("$.venteDate").value(DEFAULT_VENTE_DATE.toString()))
            .andExpect(jsonPath("$.deleteAt").value(DEFAULT_DELETE_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getVentesByIdFiltering() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        UUID id = vente.getId();

        defaultVenteFiltering("id.equals=" + id, "id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllVentesByQuantiteIsEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where quantite equals to
        defaultVenteFiltering("quantite.equals=" + DEFAULT_QUANTITE, "quantite.equals=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void getAllVentesByQuantiteIsInShouldWork() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where quantite in
        defaultVenteFiltering("quantite.in=" + DEFAULT_QUANTITE + "," + UPDATED_QUANTITE, "quantite.in=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void getAllVentesByQuantiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where quantite is not null
        defaultVenteFiltering("quantite.specified=true", "quantite.specified=false");
    }

    @Test
    @Transactional
    void getAllVentesByQuantiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where quantite is greater than or equal to
        defaultVenteFiltering("quantite.greaterThanOrEqual=" + DEFAULT_QUANTITE, "quantite.greaterThanOrEqual=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void getAllVentesByQuantiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where quantite is less than or equal to
        defaultVenteFiltering("quantite.lessThanOrEqual=" + DEFAULT_QUANTITE, "quantite.lessThanOrEqual=" + SMALLER_QUANTITE);
    }

    @Test
    @Transactional
    void getAllVentesByQuantiteIsLessThanSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where quantite is less than
        defaultVenteFiltering("quantite.lessThan=" + UPDATED_QUANTITE, "quantite.lessThan=" + DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    void getAllVentesByQuantiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where quantite is greater than
        defaultVenteFiltering("quantite.greaterThan=" + SMALLER_QUANTITE, "quantite.greaterThan=" + DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    void getAllVentesByMontantIsEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where montant equals to
        defaultVenteFiltering("montant.equals=" + DEFAULT_MONTANT, "montant.equals=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void getAllVentesByMontantIsInShouldWork() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where montant in
        defaultVenteFiltering("montant.in=" + DEFAULT_MONTANT + "," + UPDATED_MONTANT, "montant.in=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void getAllVentesByMontantIsNullOrNotNull() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where montant is not null
        defaultVenteFiltering("montant.specified=true", "montant.specified=false");
    }

    @Test
    @Transactional
    void getAllVentesByMontantIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where montant is greater than or equal to
        defaultVenteFiltering("montant.greaterThanOrEqual=" + DEFAULT_MONTANT, "montant.greaterThanOrEqual=" + UPDATED_MONTANT);
    }

    @Test
    @Transactional
    void getAllVentesByMontantIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where montant is less than or equal to
        defaultVenteFiltering("montant.lessThanOrEqual=" + DEFAULT_MONTANT, "montant.lessThanOrEqual=" + SMALLER_MONTANT);
    }

    @Test
    @Transactional
    void getAllVentesByMontantIsLessThanSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where montant is less than
        defaultVenteFiltering("montant.lessThan=" + UPDATED_MONTANT, "montant.lessThan=" + DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    void getAllVentesByMontantIsGreaterThanSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where montant is greater than
        defaultVenteFiltering("montant.greaterThan=" + SMALLER_MONTANT, "montant.greaterThan=" + DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    void getAllVentesByVenteDateIsEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where venteDate equals to
        defaultVenteFiltering("venteDate.equals=" + DEFAULT_VENTE_DATE, "venteDate.equals=" + UPDATED_VENTE_DATE);
    }

    @Test
    @Transactional
    void getAllVentesByVenteDateIsInShouldWork() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where venteDate in
        defaultVenteFiltering("venteDate.in=" + DEFAULT_VENTE_DATE + "," + UPDATED_VENTE_DATE, "venteDate.in=" + UPDATED_VENTE_DATE);
    }

    @Test
    @Transactional
    void getAllVentesByVenteDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where venteDate is not null
        defaultVenteFiltering("venteDate.specified=true", "venteDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVentesByDeleteAtIsEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where deleteAt equals to
        defaultVenteFiltering("deleteAt.equals=" + DEFAULT_DELETE_AT, "deleteAt.equals=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllVentesByDeleteAtIsInShouldWork() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where deleteAt in
        defaultVenteFiltering("deleteAt.in=" + DEFAULT_DELETE_AT + "," + UPDATED_DELETE_AT, "deleteAt.in=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllVentesByDeleteAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where deleteAt is not null
        defaultVenteFiltering("deleteAt.specified=true", "deleteAt.specified=false");
    }

    @Test
    @Transactional
    void getAllVentesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where createdBy equals to
        defaultVenteFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVentesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where createdBy in
        defaultVenteFiltering("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY, "createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVentesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where createdBy is not null
        defaultVenteFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVentesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where createdBy contains
        defaultVenteFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVentesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where createdBy does not contain
        defaultVenteFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVentesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where createdDate equals to
        defaultVenteFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllVentesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where createdDate in
        defaultVenteFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllVentesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where createdDate is not null
        defaultVenteFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVentesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where lastModifiedBy equals to
        defaultVenteFiltering("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY, "lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVentesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where lastModifiedBy in
        defaultVenteFiltering(
            "lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllVentesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where lastModifiedBy is not null
        defaultVenteFiltering("lastModifiedBy.specified=true", "lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVentesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where lastModifiedBy contains
        defaultVenteFiltering("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY, "lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVentesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where lastModifiedBy does not contain
        defaultVenteFiltering(
            "lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllVentesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where lastModifiedDate equals to
        defaultVenteFiltering(
            "lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE,
            "lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllVentesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where lastModifiedDate in
        defaultVenteFiltering(
            "lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE,
            "lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllVentesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        // Get all the venteList where lastModifiedDate is not null
        defaultVenteFiltering("lastModifiedDate.specified=true", "lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVentesByProduitIsEqualToSomething() throws Exception {
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            venteRepository.saveAndFlush(vente);
            produit = ProduitResourceIT.createEntity(em);
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        em.persist(produit);
        em.flush();
        vente.setProduit(produit);
        venteRepository.saveAndFlush(vente);
        UUID produitId = produit.getId();
        // Get all the venteList where produit equals to produitId
        defaultVenteShouldBeFound("produitId.equals=" + produitId);

        // Get all the venteList where produit equals to UUID.randomUUID()
        defaultVenteShouldNotBeFound("produitId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllVentesByStructureIsEqualToSomething() throws Exception {
        Structure structure;
        if (TestUtil.findAll(em, Structure.class).isEmpty()) {
            venteRepository.saveAndFlush(vente);
            structure = StructureResourceIT.createEntity(em);
        } else {
            structure = TestUtil.findAll(em, Structure.class).get(0);
        }
        em.persist(structure);
        em.flush();
        vente.setStructure(structure);
        venteRepository.saveAndFlush(vente);
        UUID structureId = structure.getId();
        // Get all the venteList where structure equals to structureId
        defaultVenteShouldBeFound("structureId.equals=" + structureId);

        // Get all the venteList where structure equals to UUID.randomUUID()
        defaultVenteShouldNotBeFound("structureId.equals=" + UUID.randomUUID());
    }

    private void defaultVenteFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultVenteShouldBeFound(shouldBeFound);
        defaultVenteShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVenteShouldBeFound(String filter) throws Exception {
        restVenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vente.getId().toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(sameNumber(DEFAULT_MONTANT))))
            .andExpect(jsonPath("$.[*].venteDate").value(hasItem(DEFAULT_VENTE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restVenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVenteShouldNotBeFound(String filter) throws Exception {
        restVenteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVenteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVente() throws Exception {
        // Get the vente
        restVenteMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVente() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vente
        Vente updatedVente = venteRepository.findById(vente.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedVente are not directly saved in db
        em.detach(updatedVente);
        updatedVente
            .quantite(UPDATED_QUANTITE)
            .montant(UPDATED_MONTANT)
            .venteDate(UPDATED_VENTE_DATE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        VenteDTO venteDTO = venteMapper.toDto(updatedVente);

        restVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, venteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedVenteToMatchAllProperties(updatedVente);
    }

    @Test
    @Transactional
    void putNonExistingVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vente.setId(UUID.randomUUID());

        // Create the Vente
        VenteDTO venteDTO = venteMapper.toDto(vente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, venteDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vente.setId(UUID.randomUUID());

        // Create the Vente
        VenteDTO venteDTO = venteMapper.toDto(vente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vente.setId(UUID.randomUUID());

        // Create the Vente
        VenteDTO venteDTO = venteMapper.toDto(vente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(venteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVenteWithPatch() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vente using partial update
        Vente partialUpdatedVente = new Vente();
        partialUpdatedVente.setId(vente.getId());

        partialUpdatedVente
            .montant(UPDATED_MONTANT)
            .deleteAt(UPDATED_DELETE_AT)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVente))
            )
            .andExpect(status().isOk());

        // Validate the Vente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVenteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedVente, vente), getPersistedVente(vente));
    }

    @Test
    @Transactional
    void fullUpdateVenteWithPatch() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the vente using partial update
        Vente partialUpdatedVente = new Vente();
        partialUpdatedVente.setId(vente.getId());

        partialUpdatedVente
            .quantite(UPDATED_QUANTITE)
            .montant(UPDATED_MONTANT)
            .venteDate(UPDATED_VENTE_DATE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVente.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedVente))
            )
            .andExpect(status().isOk());

        // Validate the Vente in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertVenteUpdatableFieldsEquals(partialUpdatedVente, getPersistedVente(partialUpdatedVente));
    }

    @Test
    @Transactional
    void patchNonExistingVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vente.setId(UUID.randomUUID());

        // Create the Vente
        VenteDTO venteDTO = venteMapper.toDto(vente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, venteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(venteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vente.setId(UUID.randomUUID());

        // Create the Vente
        VenteDTO venteDTO = venteMapper.toDto(vente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(venteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVente() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        vente.setId(UUID.randomUUID());

        // Create the Vente
        VenteDTO venteDTO = venteMapper.toDto(vente);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVenteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(venteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vente in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVente() throws Exception {
        // Initialize the database
        venteRepository.saveAndFlush(vente);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the vente
        restVenteMockMvc
            .perform(delete(ENTITY_API_URL_ID, vente.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return venteRepository.count();
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

    protected Vente getPersistedVente(Vente vente) {
        return venteRepository.findById(vente.getId()).orElseThrow();
    }

    protected void assertPersistedVenteToMatchAllProperties(Vente expectedVente) {
        assertVenteAllPropertiesEquals(expectedVente, getPersistedVente(expectedVente));
    }

    protected void assertPersistedVenteToMatchUpdatableProperties(Vente expectedVente) {
        assertVenteAllUpdatablePropertiesEquals(expectedVente, getPersistedVente(expectedVente));
    }
}
