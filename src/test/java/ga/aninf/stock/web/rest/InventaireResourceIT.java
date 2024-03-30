package ga.aninf.stock.web.rest;

import static ga.aninf.stock.domain.InventaireAsserts.*;
import static ga.aninf.stock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import ga.aninf.stock.IntegrationTest;
import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.domain.Inventaire;
import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.repository.InventaireRepository;
import ga.aninf.stock.service.InventaireService;
import ga.aninf.stock.service.dto.InventaireDTO;
import ga.aninf.stock.service.mapper.InventaireMapper;
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
 * Integration tests for the {@link InventaireResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InventaireResourceIT {

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;
    private static final Long SMALLER_QUANTITE = 1L - 1L;

    private static final Instant DEFAULT_INVENTAIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INVENTAIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/inventaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private InventaireRepository inventaireRepository;

    @Mock
    private InventaireRepository inventaireRepositoryMock;

    @Autowired
    private InventaireMapper inventaireMapper;

    @Mock
    private InventaireService inventaireServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventaireMockMvc;

    private Inventaire inventaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventaire createEntity(EntityManager em) {
        Inventaire inventaire = new Inventaire()
            .quantite(DEFAULT_QUANTITE)
            .inventaireDate(DEFAULT_INVENTAIRE_DATE)
            .deleteAt(DEFAULT_DELETE_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        // Add required entity
        Entrepot entrepot;
        if (TestUtil.findAll(em, Entrepot.class).isEmpty()) {
            entrepot = EntrepotResourceIT.createEntity(em);
            em.persist(entrepot);
            em.flush();
        } else {
            entrepot = TestUtil.findAll(em, Entrepot.class).get(0);
        }
        inventaire.setEntrepot(entrepot);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        inventaire.setProduit(produit);
        return inventaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventaire createUpdatedEntity(EntityManager em) {
        Inventaire inventaire = new Inventaire()
            .quantite(UPDATED_QUANTITE)
            .inventaireDate(UPDATED_INVENTAIRE_DATE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        // Add required entity
        Entrepot entrepot;
        if (TestUtil.findAll(em, Entrepot.class).isEmpty()) {
            entrepot = EntrepotResourceIT.createUpdatedEntity(em);
            em.persist(entrepot);
            em.flush();
        } else {
            entrepot = TestUtil.findAll(em, Entrepot.class).get(0);
        }
        inventaire.setEntrepot(entrepot);
        // Add required entity
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            produit = ProduitResourceIT.createUpdatedEntity(em);
            em.persist(produit);
            em.flush();
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        inventaire.setProduit(produit);
        return inventaire;
    }

    @BeforeEach
    public void initTest() {
        inventaire = createEntity(em);
    }

    @Test
    @Transactional
    void createInventaire() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Inventaire
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);
        var returnedInventaireDTO = om.readValue(
            restInventaireMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventaireDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            InventaireDTO.class
        );

        // Validate the Inventaire in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedInventaire = inventaireMapper.toEntity(returnedInventaireDTO);
        assertInventaireUpdatableFieldsEquals(returnedInventaire, getPersistedInventaire(returnedInventaire));
    }

    @Test
    @Transactional
    void createInventaireWithExistingId() throws Exception {
        // Create the Inventaire with an existing ID
        inventaireRepository.saveAndFlush(inventaire);
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventaire in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantiteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventaire.setQuantite(null);

        // Create the Inventaire, which fails.
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        restInventaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventaireDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInventaireDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventaire.setInventaireDate(null);

        // Create the Inventaire, which fails.
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        restInventaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventaireDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        inventaire.setCreatedBy(null);

        // Create the Inventaire, which fails.
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        restInventaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventaireDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInventaires() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList
        restInventaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventaire.getId().toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].inventaireDate").value(hasItem(DEFAULT_INVENTAIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInventairesWithEagerRelationshipsIsEnabled() throws Exception {
        when(inventaireServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInventaireMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(inventaireServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInventairesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(inventaireServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInventaireMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(inventaireRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInventaire() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get the inventaire
        restInventaireMockMvc
            .perform(get(ENTITY_API_URL_ID, inventaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventaire.getId().toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.inventaireDate").value(DEFAULT_INVENTAIRE_DATE.toString()))
            .andExpect(jsonPath("$.deleteAt").value(DEFAULT_DELETE_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getInventairesByIdFiltering() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        UUID id = inventaire.getId();

        defaultInventaireFiltering("id.equals=" + id, "id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllInventairesByQuantiteIsEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where quantite equals to
        defaultInventaireFiltering("quantite.equals=" + DEFAULT_QUANTITE, "quantite.equals=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void getAllInventairesByQuantiteIsInShouldWork() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where quantite in
        defaultInventaireFiltering("quantite.in=" + DEFAULT_QUANTITE + "," + UPDATED_QUANTITE, "quantite.in=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void getAllInventairesByQuantiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where quantite is not null
        defaultInventaireFiltering("quantite.specified=true", "quantite.specified=false");
    }

    @Test
    @Transactional
    void getAllInventairesByQuantiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where quantite is greater than or equal to
        defaultInventaireFiltering("quantite.greaterThanOrEqual=" + DEFAULT_QUANTITE, "quantite.greaterThanOrEqual=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void getAllInventairesByQuantiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where quantite is less than or equal to
        defaultInventaireFiltering("quantite.lessThanOrEqual=" + DEFAULT_QUANTITE, "quantite.lessThanOrEqual=" + SMALLER_QUANTITE);
    }

    @Test
    @Transactional
    void getAllInventairesByQuantiteIsLessThanSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where quantite is less than
        defaultInventaireFiltering("quantite.lessThan=" + UPDATED_QUANTITE, "quantite.lessThan=" + DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    void getAllInventairesByQuantiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where quantite is greater than
        defaultInventaireFiltering("quantite.greaterThan=" + SMALLER_QUANTITE, "quantite.greaterThan=" + DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    void getAllInventairesByInventaireDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where inventaireDate equals to
        defaultInventaireFiltering("inventaireDate.equals=" + DEFAULT_INVENTAIRE_DATE, "inventaireDate.equals=" + UPDATED_INVENTAIRE_DATE);
    }

    @Test
    @Transactional
    void getAllInventairesByInventaireDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where inventaireDate in
        defaultInventaireFiltering(
            "inventaireDate.in=" + DEFAULT_INVENTAIRE_DATE + "," + UPDATED_INVENTAIRE_DATE,
            "inventaireDate.in=" + UPDATED_INVENTAIRE_DATE
        );
    }

    @Test
    @Transactional
    void getAllInventairesByInventaireDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where inventaireDate is not null
        defaultInventaireFiltering("inventaireDate.specified=true", "inventaireDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInventairesByDeleteAtIsEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where deleteAt equals to
        defaultInventaireFiltering("deleteAt.equals=" + DEFAULT_DELETE_AT, "deleteAt.equals=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllInventairesByDeleteAtIsInShouldWork() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where deleteAt in
        defaultInventaireFiltering("deleteAt.in=" + DEFAULT_DELETE_AT + "," + UPDATED_DELETE_AT, "deleteAt.in=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllInventairesByDeleteAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where deleteAt is not null
        defaultInventaireFiltering("deleteAt.specified=true", "deleteAt.specified=false");
    }

    @Test
    @Transactional
    void getAllInventairesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where createdBy equals to
        defaultInventaireFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllInventairesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where createdBy in
        defaultInventaireFiltering("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY, "createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllInventairesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where createdBy is not null
        defaultInventaireFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInventairesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where createdBy contains
        defaultInventaireFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllInventairesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where createdBy does not contain
        defaultInventaireFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllInventairesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where createdDate equals to
        defaultInventaireFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllInventairesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where createdDate in
        defaultInventaireFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllInventairesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where createdDate is not null
        defaultInventaireFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInventairesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where lastModifiedBy equals to
        defaultInventaireFiltering(
            "lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllInventairesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where lastModifiedBy in
        defaultInventaireFiltering(
            "lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllInventairesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where lastModifiedBy is not null
        defaultInventaireFiltering("lastModifiedBy.specified=true", "lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInventairesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where lastModifiedBy contains
        defaultInventaireFiltering(
            "lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllInventairesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where lastModifiedBy does not contain
        defaultInventaireFiltering(
            "lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllInventairesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where lastModifiedDate equals to
        defaultInventaireFiltering(
            "lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE,
            "lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllInventairesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where lastModifiedDate in
        defaultInventaireFiltering(
            "lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE,
            "lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllInventairesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        // Get all the inventaireList where lastModifiedDate is not null
        defaultInventaireFiltering("lastModifiedDate.specified=true", "lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInventairesByEntrepotIsEqualToSomething() throws Exception {
        Entrepot entrepot;
        if (TestUtil.findAll(em, Entrepot.class).isEmpty()) {
            inventaireRepository.saveAndFlush(inventaire);
            entrepot = EntrepotResourceIT.createEntity(em);
        } else {
            entrepot = TestUtil.findAll(em, Entrepot.class).get(0);
        }
        em.persist(entrepot);
        em.flush();
        inventaire.setEntrepot(entrepot);
        inventaireRepository.saveAndFlush(inventaire);
        UUID entrepotId = entrepot.getId();
        // Get all the inventaireList where entrepot equals to entrepotId
        defaultInventaireShouldBeFound("entrepotId.equals=" + entrepotId);

        // Get all the inventaireList where entrepot equals to UUID.randomUUID()
        defaultInventaireShouldNotBeFound("entrepotId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllInventairesByProduitIsEqualToSomething() throws Exception {
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            inventaireRepository.saveAndFlush(inventaire);
            produit = ProduitResourceIT.createEntity(em);
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        em.persist(produit);
        em.flush();
        inventaire.setProduit(produit);
        inventaireRepository.saveAndFlush(inventaire);
        UUID produitId = produit.getId();
        // Get all the inventaireList where produit equals to produitId
        defaultInventaireShouldBeFound("produitId.equals=" + produitId);

        // Get all the inventaireList where produit equals to UUID.randomUUID()
        defaultInventaireShouldNotBeFound("produitId.equals=" + UUID.randomUUID());
    }

    private void defaultInventaireFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultInventaireShouldBeFound(shouldBeFound);
        defaultInventaireShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventaireShouldBeFound(String filter) throws Exception {
        restInventaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventaire.getId().toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].inventaireDate").value(hasItem(DEFAULT_INVENTAIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restInventaireMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventaireShouldNotBeFound(String filter) throws Exception {
        restInventaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventaireMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInventaire() throws Exception {
        // Get the inventaire
        restInventaireMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInventaire() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventaire
        Inventaire updatedInventaire = inventaireRepository.findById(inventaire.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedInventaire are not directly saved in db
        em.detach(updatedInventaire);
        updatedInventaire
            .quantite(UPDATED_QUANTITE)
            .inventaireDate(UPDATED_INVENTAIRE_DATE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        InventaireDTO inventaireDTO = inventaireMapper.toDto(updatedInventaire);

        restInventaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventaireDTO))
            )
            .andExpect(status().isOk());

        // Validate the Inventaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedInventaireToMatchAllProperties(updatedInventaire);
    }

    @Test
    @Transactional
    void putNonExistingInventaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventaire.setId(UUID.randomUUID());

        // Create the Inventaire
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inventaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInventaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventaire.setId(UUID.randomUUID());

        // Create the Inventaire
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(inventaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInventaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventaire.setId(UUID.randomUUID());

        // Create the Inventaire
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(inventaireDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInventaireWithPatch() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventaire using partial update
        Inventaire partialUpdatedInventaire = new Inventaire();
        partialUpdatedInventaire.setId(inventaire.getId());

        partialUpdatedInventaire.createdDate(UPDATED_CREATED_DATE).lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restInventaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventaire))
            )
            .andExpect(status().isOk());

        // Validate the Inventaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventaireUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedInventaire, inventaire),
            getPersistedInventaire(inventaire)
        );
    }

    @Test
    @Transactional
    void fullUpdateInventaireWithPatch() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the inventaire using partial update
        Inventaire partialUpdatedInventaire = new Inventaire();
        partialUpdatedInventaire.setId(inventaire.getId());

        partialUpdatedInventaire
            .quantite(UPDATED_QUANTITE)
            .inventaireDate(UPDATED_INVENTAIRE_DATE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restInventaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInventaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedInventaire))
            )
            .andExpect(status().isOk());

        // Validate the Inventaire in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertInventaireUpdatableFieldsEquals(partialUpdatedInventaire, getPersistedInventaire(partialUpdatedInventaire));
    }

    @Test
    @Transactional
    void patchNonExistingInventaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventaire.setId(UUID.randomUUID());

        // Create the Inventaire
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inventaireDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInventaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventaire.setId(UUID.randomUUID());

        // Create the Inventaire
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(inventaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inventaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInventaire() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        inventaire.setId(UUID.randomUUID());

        // Create the Inventaire
        InventaireDTO inventaireDTO = inventaireMapper.toDto(inventaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInventaireMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(inventaireDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inventaire in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInventaire() throws Exception {
        // Initialize the database
        inventaireRepository.saveAndFlush(inventaire);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the inventaire
        restInventaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, inventaire.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return inventaireRepository.count();
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

    protected Inventaire getPersistedInventaire(Inventaire inventaire) {
        return inventaireRepository.findById(inventaire.getId()).orElseThrow();
    }

    protected void assertPersistedInventaireToMatchAllProperties(Inventaire expectedInventaire) {
        assertInventaireAllPropertiesEquals(expectedInventaire, getPersistedInventaire(expectedInventaire));
    }

    protected void assertPersistedInventaireToMatchUpdatableProperties(Inventaire expectedInventaire) {
        assertInventaireAllUpdatablePropertiesEquals(expectedInventaire, getPersistedInventaire(expectedInventaire));
    }
}
