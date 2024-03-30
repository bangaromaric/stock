package ga.aninf.stock.web.rest;

import static ga.aninf.stock.domain.MouvementStockAsserts.*;
import static ga.aninf.stock.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import ga.aninf.stock.IntegrationTest;
import ga.aninf.stock.domain.Entrepot;
import ga.aninf.stock.domain.MouvementStock;
import ga.aninf.stock.domain.Produit;
import ga.aninf.stock.domain.enumeration.TypeMouvement;
import ga.aninf.stock.repository.MouvementStockRepository;
import ga.aninf.stock.service.MouvementStockService;
import ga.aninf.stock.service.dto.MouvementStockDTO;
import ga.aninf.stock.service.mapper.MouvementStockMapper;
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
 * Integration tests for the {@link MouvementStockResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MouvementStockResourceIT {

    private static final Long DEFAULT_QUANTITE = 1L;
    private static final Long UPDATED_QUANTITE = 2L;
    private static final Long SMALLER_QUANTITE = 1L - 1L;

    private static final TypeMouvement DEFAULT_TYPE_MOUVEMENT = TypeMouvement.ENTREE;
    private static final TypeMouvement UPDATED_TYPE_MOUVEMENT = TypeMouvement.SORTIE;

    private static final Instant DEFAULT_TRANSACTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSACTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/mouvement-stocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MouvementStockRepository mouvementStockRepository;

    @Mock
    private MouvementStockRepository mouvementStockRepositoryMock;

    @Autowired
    private MouvementStockMapper mouvementStockMapper;

    @Mock
    private MouvementStockService mouvementStockServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMouvementStockMockMvc;

    private MouvementStock mouvementStock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementStock createEntity(EntityManager em) {
        MouvementStock mouvementStock = new MouvementStock()
            .quantite(DEFAULT_QUANTITE)
            .typeMouvement(DEFAULT_TYPE_MOUVEMENT)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
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
        mouvementStock.setProduit(produit);
        // Add required entity
        Entrepot entrepot;
        if (TestUtil.findAll(em, Entrepot.class).isEmpty()) {
            entrepot = EntrepotResourceIT.createEntity(em);
            em.persist(entrepot);
            em.flush();
        } else {
            entrepot = TestUtil.findAll(em, Entrepot.class).get(0);
        }
        mouvementStock.setEntrepot(entrepot);
        return mouvementStock;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementStock createUpdatedEntity(EntityManager em) {
        MouvementStock mouvementStock = new MouvementStock()
            .quantite(UPDATED_QUANTITE)
            .typeMouvement(UPDATED_TYPE_MOUVEMENT)
            .transactionDate(UPDATED_TRANSACTION_DATE)
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
        mouvementStock.setProduit(produit);
        // Add required entity
        Entrepot entrepot;
        if (TestUtil.findAll(em, Entrepot.class).isEmpty()) {
            entrepot = EntrepotResourceIT.createUpdatedEntity(em);
            em.persist(entrepot);
            em.flush();
        } else {
            entrepot = TestUtil.findAll(em, Entrepot.class).get(0);
        }
        mouvementStock.setEntrepot(entrepot);
        return mouvementStock;
    }

    @BeforeEach
    public void initTest() {
        mouvementStock = createEntity(em);
    }

    @Test
    @Transactional
    void createMouvementStock() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);
        var returnedMouvementStockDTO = om.readValue(
            restMouvementStockMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementStockDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MouvementStockDTO.class
        );

        // Validate the MouvementStock in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMouvementStock = mouvementStockMapper.toEntity(returnedMouvementStockDTO);
        assertMouvementStockUpdatableFieldsEquals(returnedMouvementStock, getPersistedMouvementStock(returnedMouvementStock));
    }

    @Test
    @Transactional
    void createMouvementStockWithExistingId() throws Exception {
        // Create the MouvementStock with an existing ID
        mouvementStockRepository.saveAndFlush(mouvementStock);
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouvementStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkQuantiteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mouvementStock.setQuantite(null);

        // Create the MouvementStock, which fails.
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        restMouvementStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeMouvementIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mouvementStock.setTypeMouvement(null);

        // Create the MouvementStock, which fails.
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        restMouvementStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTransactionDateIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mouvementStock.setTransactionDate(null);

        // Create the MouvementStock, which fails.
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        restMouvementStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mouvementStock.setCreatedBy(null);

        // Create the MouvementStock, which fails.
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        restMouvementStockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementStockDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMouvementStocks() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList
        restMouvementStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementStock.getId().toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].typeMouvement").value(hasItem(DEFAULT_TYPE_MOUVEMENT.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMouvementStocksWithEagerRelationshipsIsEnabled() throws Exception {
        when(mouvementStockServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMouvementStockMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(mouvementStockServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMouvementStocksWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(mouvementStockServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMouvementStockMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(mouvementStockRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get the mouvementStock
        restMouvementStockMockMvc
            .perform(get(ENTITY_API_URL_ID, mouvementStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mouvementStock.getId().toString()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.typeMouvement").value(DEFAULT_TYPE_MOUVEMENT.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.deleteAt").value(DEFAULT_DELETE_AT.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getMouvementStocksByIdFiltering() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        UUID id = mouvementStock.getId();

        defaultMouvementStockFiltering("id.equals=" + id, "id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByQuantiteIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite equals to
        defaultMouvementStockFiltering("quantite.equals=" + DEFAULT_QUANTITE, "quantite.equals=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByQuantiteIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite in
        defaultMouvementStockFiltering("quantite.in=" + DEFAULT_QUANTITE + "," + UPDATED_QUANTITE, "quantite.in=" + UPDATED_QUANTITE);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByQuantiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is not null
        defaultMouvementStockFiltering("quantite.specified=true", "quantite.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementStocksByQuantiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is greater than or equal to
        defaultMouvementStockFiltering(
            "quantite.greaterThanOrEqual=" + DEFAULT_QUANTITE,
            "quantite.greaterThanOrEqual=" + UPDATED_QUANTITE
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByQuantiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is less than or equal to
        defaultMouvementStockFiltering("quantite.lessThanOrEqual=" + DEFAULT_QUANTITE, "quantite.lessThanOrEqual=" + SMALLER_QUANTITE);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByQuantiteIsLessThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is less than
        defaultMouvementStockFiltering("quantite.lessThan=" + UPDATED_QUANTITE, "quantite.lessThan=" + DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByQuantiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where quantite is greater than
        defaultMouvementStockFiltering("quantite.greaterThan=" + SMALLER_QUANTITE, "quantite.greaterThan=" + DEFAULT_QUANTITE);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByTypeMouvementIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where typeMouvement equals to
        defaultMouvementStockFiltering("typeMouvement.equals=" + DEFAULT_TYPE_MOUVEMENT, "typeMouvement.equals=" + UPDATED_TYPE_MOUVEMENT);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByTypeMouvementIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where typeMouvement in
        defaultMouvementStockFiltering(
            "typeMouvement.in=" + DEFAULT_TYPE_MOUVEMENT + "," + UPDATED_TYPE_MOUVEMENT,
            "typeMouvement.in=" + UPDATED_TYPE_MOUVEMENT
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByTypeMouvementIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where typeMouvement is not null
        defaultMouvementStockFiltering("typeMouvement.specified=true", "typeMouvement.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementStocksByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where transactionDate equals to
        defaultMouvementStockFiltering(
            "transactionDate.equals=" + DEFAULT_TRANSACTION_DATE,
            "transactionDate.equals=" + UPDATED_TRANSACTION_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where transactionDate in
        defaultMouvementStockFiltering(
            "transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE,
            "transactionDate.in=" + UPDATED_TRANSACTION_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where transactionDate is not null
        defaultMouvementStockFiltering("transactionDate.specified=true", "transactionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementStocksByDeleteAtIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where deleteAt equals to
        defaultMouvementStockFiltering("deleteAt.equals=" + DEFAULT_DELETE_AT, "deleteAt.equals=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByDeleteAtIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where deleteAt in
        defaultMouvementStockFiltering("deleteAt.in=" + DEFAULT_DELETE_AT + "," + UPDATED_DELETE_AT, "deleteAt.in=" + UPDATED_DELETE_AT);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByDeleteAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where deleteAt is not null
        defaultMouvementStockFiltering("deleteAt.specified=true", "deleteAt.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementStocksByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where createdBy equals to
        defaultMouvementStockFiltering("createdBy.equals=" + DEFAULT_CREATED_BY, "createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where createdBy in
        defaultMouvementStockFiltering(
            "createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY,
            "createdBy.in=" + UPDATED_CREATED_BY
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where createdBy is not null
        defaultMouvementStockFiltering("createdBy.specified=true", "createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementStocksByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where createdBy contains
        defaultMouvementStockFiltering("createdBy.contains=" + DEFAULT_CREATED_BY, "createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where createdBy does not contain
        defaultMouvementStockFiltering("createdBy.doesNotContain=" + UPDATED_CREATED_BY, "createdBy.doesNotContain=" + DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where createdDate equals to
        defaultMouvementStockFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllMouvementStocksByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where createdDate in
        defaultMouvementStockFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where createdDate is not null
        defaultMouvementStockFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementStocksByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where lastModifiedBy equals to
        defaultMouvementStockFiltering(
            "lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where lastModifiedBy in
        defaultMouvementStockFiltering(
            "lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where lastModifiedBy is not null
        defaultMouvementStockFiltering("lastModifiedBy.specified=true", "lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementStocksByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where lastModifiedBy contains
        defaultMouvementStockFiltering(
            "lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY,
            "lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where lastModifiedBy does not contain
        defaultMouvementStockFiltering(
            "lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY,
            "lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where lastModifiedDate equals to
        defaultMouvementStockFiltering(
            "lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE,
            "lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where lastModifiedDate in
        defaultMouvementStockFiltering(
            "lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE,
            "lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE
        );
    }

    @Test
    @Transactional
    void getAllMouvementStocksByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        // Get all the mouvementStockList where lastModifiedDate is not null
        defaultMouvementStockFiltering("lastModifiedDate.specified=true", "lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMouvementStocksByProduitIsEqualToSomething() throws Exception {
        Produit produit;
        if (TestUtil.findAll(em, Produit.class).isEmpty()) {
            mouvementStockRepository.saveAndFlush(mouvementStock);
            produit = ProduitResourceIT.createEntity(em);
        } else {
            produit = TestUtil.findAll(em, Produit.class).get(0);
        }
        em.persist(produit);
        em.flush();
        mouvementStock.setProduit(produit);
        mouvementStockRepository.saveAndFlush(mouvementStock);
        UUID produitId = produit.getId();
        // Get all the mouvementStockList where produit equals to produitId
        defaultMouvementStockShouldBeFound("produitId.equals=" + produitId);

        // Get all the mouvementStockList where produit equals to UUID.randomUUID()
        defaultMouvementStockShouldNotBeFound("produitId.equals=" + UUID.randomUUID());
    }

    @Test
    @Transactional
    void getAllMouvementStocksByEntrepotIsEqualToSomething() throws Exception {
        Entrepot entrepot;
        if (TestUtil.findAll(em, Entrepot.class).isEmpty()) {
            mouvementStockRepository.saveAndFlush(mouvementStock);
            entrepot = EntrepotResourceIT.createEntity(em);
        } else {
            entrepot = TestUtil.findAll(em, Entrepot.class).get(0);
        }
        em.persist(entrepot);
        em.flush();
        mouvementStock.setEntrepot(entrepot);
        mouvementStockRepository.saveAndFlush(mouvementStock);
        UUID entrepotId = entrepot.getId();
        // Get all the mouvementStockList where entrepot equals to entrepotId
        defaultMouvementStockShouldBeFound("entrepotId.equals=" + entrepotId);

        // Get all the mouvementStockList where entrepot equals to UUID.randomUUID()
        defaultMouvementStockShouldNotBeFound("entrepotId.equals=" + UUID.randomUUID());
    }

    private void defaultMouvementStockFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMouvementStockShouldBeFound(shouldBeFound);
        defaultMouvementStockShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMouvementStockShouldBeFound(String filter) throws Exception {
        restMouvementStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementStock.getId().toString())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].typeMouvement").value(hasItem(DEFAULT_TYPE_MOUVEMENT.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].deleteAt").value(hasItem(DEFAULT_DELETE_AT.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restMouvementStockMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMouvementStockShouldNotBeFound(String filter) throws Exception {
        restMouvementStockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMouvementStockMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMouvementStock() throws Exception {
        // Get the mouvementStock
        restMouvementStockMockMvc.perform(get(ENTITY_API_URL_ID, UUID.randomUUID().toString())).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mouvementStock
        MouvementStock updatedMouvementStock = mouvementStockRepository.findById(mouvementStock.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMouvementStock are not directly saved in db
        em.detach(updatedMouvementStock);
        updatedMouvementStock
            .quantite(UPDATED_QUANTITE)
            .typeMouvement(UPDATED_TYPE_MOUVEMENT)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(updatedMouvementStock);

        restMouvementStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mouvementStockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mouvementStockDTO))
            )
            .andExpect(status().isOk());

        // Validate the MouvementStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMouvementStockToMatchAllProperties(updatedMouvementStock);
    }

    @Test
    @Transactional
    void putNonExistingMouvementStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementStock.setId(UUID.randomUUID());

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mouvementStockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mouvementStockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMouvementStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementStock.setId(UUID.randomUUID());

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mouvementStockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMouvementStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementStock.setId(UUID.randomUUID());

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mouvementStockDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MouvementStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMouvementStockWithPatch() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mouvementStock using partial update
        MouvementStock partialUpdatedMouvementStock = new MouvementStock();
        partialUpdatedMouvementStock.setId(mouvementStock.getId());

        partialUpdatedMouvementStock
            .typeMouvement(UPDATED_TYPE_MOUVEMENT)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restMouvementStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMouvementStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMouvementStock))
            )
            .andExpect(status().isOk());

        // Validate the MouvementStock in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMouvementStockUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMouvementStock, mouvementStock),
            getPersistedMouvementStock(mouvementStock)
        );
    }

    @Test
    @Transactional
    void fullUpdateMouvementStockWithPatch() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mouvementStock using partial update
        MouvementStock partialUpdatedMouvementStock = new MouvementStock();
        partialUpdatedMouvementStock.setId(mouvementStock.getId());

        partialUpdatedMouvementStock
            .quantite(UPDATED_QUANTITE)
            .typeMouvement(UPDATED_TYPE_MOUVEMENT)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .deleteAt(UPDATED_DELETE_AT)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restMouvementStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMouvementStock.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMouvementStock))
            )
            .andExpect(status().isOk());

        // Validate the MouvementStock in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMouvementStockUpdatableFieldsEquals(partialUpdatedMouvementStock, getPersistedMouvementStock(partialUpdatedMouvementStock));
    }

    @Test
    @Transactional
    void patchNonExistingMouvementStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementStock.setId(UUID.randomUUID());

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mouvementStockDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mouvementStockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMouvementStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementStock.setId(UUID.randomUUID());

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mouvementStockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMouvementStock() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mouvementStock.setId(UUID.randomUUID());

        // Create the MouvementStock
        MouvementStockDTO mouvementStockDTO = mouvementStockMapper.toDto(mouvementStock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementStockMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mouvementStockDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MouvementStock in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMouvementStock() throws Exception {
        // Initialize the database
        mouvementStockRepository.saveAndFlush(mouvementStock);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mouvementStock
        restMouvementStockMockMvc
            .perform(delete(ENTITY_API_URL_ID, mouvementStock.getId().toString()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mouvementStockRepository.count();
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

    protected MouvementStock getPersistedMouvementStock(MouvementStock mouvementStock) {
        return mouvementStockRepository.findById(mouvementStock.getId()).orElseThrow();
    }

    protected void assertPersistedMouvementStockToMatchAllProperties(MouvementStock expectedMouvementStock) {
        assertMouvementStockAllPropertiesEquals(expectedMouvementStock, getPersistedMouvementStock(expectedMouvementStock));
    }

    protected void assertPersistedMouvementStockToMatchUpdatableProperties(MouvementStock expectedMouvementStock) {
        assertMouvementStockAllUpdatablePropertiesEquals(expectedMouvementStock, getPersistedMouvementStock(expectedMouvementStock));
    }
}
