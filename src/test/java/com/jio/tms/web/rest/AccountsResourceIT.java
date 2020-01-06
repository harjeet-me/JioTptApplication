package com.jio.tms.web.rest;

import com.jio.tms.JioTptApplicationApp;
import com.jio.tms.domain.Accounts;
import com.jio.tms.repository.AccountsRepository;
import com.jio.tms.repository.search.AccountsSearchRepository;
import com.jio.tms.service.AccountsService;
import com.jio.tms.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.jio.tms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AccountsResource} REST controller.
 */
@SpringBootTest(classes = JioTptApplicationApp.class)
public class AccountsResourceIT {

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final Double DEFAULT_OVER_30 = 1D;
    private static final Double UPDATED_OVER_30 = 2D;

    private static final Double DEFAULT_OVER_60 = 1D;
    private static final Double UPDATED_OVER_60 = 2D;

    private static final Double DEFAULT_OVER_90 = 1D;
    private static final Double UPDATED_OVER_90 = 2D;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsService accountsService;

    /**
     * This repository is mocked in the com.jio.tms.repository.search test package.
     *
     * @see com.jio.tms.repository.search.AccountsSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccountsSearchRepository mockAccountsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAccountsMockMvc;

    private Accounts accounts;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountsResource accountsResource = new AccountsResource(accountsService);
        this.restAccountsMockMvc = MockMvcBuilders.standaloneSetup(accountsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .balance(DEFAULT_BALANCE)
            .over30(DEFAULT_OVER_30)
            .over60(DEFAULT_OVER_60)
            .over90(DEFAULT_OVER_90);
        return accounts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createUpdatedEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .balance(UPDATED_BALANCE)
            .over30(UPDATED_OVER_30)
            .over60(UPDATED_OVER_60)
            .over90(UPDATED_OVER_90);
        return accounts;
    }

    @BeforeEach
    public void initTest() {
        accounts = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccounts() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();

        // Create the Accounts
        restAccountsMockMvc.perform(post("/api/accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isCreated());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate + 1);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testAccounts.getOver30()).isEqualTo(DEFAULT_OVER_30);
        assertThat(testAccounts.getOver60()).isEqualTo(DEFAULT_OVER_60);
        assertThat(testAccounts.getOver90()).isEqualTo(DEFAULT_OVER_90);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(1)).save(testAccounts);
    }

    @Test
    @Transactional
    public void createAccountsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();

        // Create the Accounts with an existing ID
        accounts.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc.perform(post("/api/accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }


    @Test
    @Transactional
    public void getAllAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList
        restAccountsMockMvc.perform(get("/api/accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].over30").value(hasItem(DEFAULT_OVER_30.doubleValue())))
            .andExpect(jsonPath("$.[*].over60").value(hasItem(DEFAULT_OVER_60.doubleValue())))
            .andExpect(jsonPath("$.[*].over90").value(hasItem(DEFAULT_OVER_90.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get the accounts
        restAccountsMockMvc.perform(get("/api/accounts/{id}", accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accounts.getId().intValue()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.over30").value(DEFAULT_OVER_30.doubleValue()))
            .andExpect(jsonPath("$.over60").value(DEFAULT_OVER_60.doubleValue()))
            .andExpect(jsonPath("$.over90").value(DEFAULT_OVER_90.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAccounts() throws Exception {
        // Get the accounts
        restAccountsMockMvc.perform(get("/api/accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccounts() throws Exception {
        // Initialize the database
        accountsService.save(accounts);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAccountsSearchRepository);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts
        Accounts updatedAccounts = accountsRepository.findById(accounts.getId()).get();
        // Disconnect from session so that the updates on updatedAccounts are not directly saved in db
        em.detach(updatedAccounts);
        updatedAccounts
            .balance(UPDATED_BALANCE)
            .over30(UPDATED_OVER_30)
            .over60(UPDATED_OVER_60)
            .over90(UPDATED_OVER_90);

        restAccountsMockMvc.perform(put("/api/accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccounts)))
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testAccounts.getOver30()).isEqualTo(UPDATED_OVER_30);
        assertThat(testAccounts.getOver60()).isEqualTo(UPDATED_OVER_60);
        assertThat(testAccounts.getOver90()).isEqualTo(UPDATED_OVER_90);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(1)).save(testAccounts);
    }

    @Test
    @Transactional
    public void updateNonExistingAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Create the Accounts

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc.perform(put("/api/accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accounts)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }

    @Test
    @Transactional
    public void deleteAccounts() throws Exception {
        // Initialize the database
        accountsService.save(accounts);

        int databaseSizeBeforeDelete = accountsRepository.findAll().size();

        // Delete the accounts
        restAccountsMockMvc.perform(delete("/api/accounts/{id}", accounts.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(1)).deleteById(accounts.getId());
    }

    @Test
    @Transactional
    public void searchAccounts() throws Exception {
        // Initialize the database
        accountsService.save(accounts);
        when(mockAccountsSearchRepository.search(queryStringQuery("id:" + accounts.getId())))
            .thenReturn(Collections.singletonList(accounts));
        // Search the accounts
        restAccountsMockMvc.perform(get("/api/_search/accounts?query=id:" + accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].over30").value(hasItem(DEFAULT_OVER_30.doubleValue())))
            .andExpect(jsonPath("$.[*].over60").value(hasItem(DEFAULT_OVER_60.doubleValue())))
            .andExpect(jsonPath("$.[*].over90").value(hasItem(DEFAULT_OVER_90.doubleValue())));
    }
}
