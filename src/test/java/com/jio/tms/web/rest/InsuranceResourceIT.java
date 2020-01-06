package com.jio.tms.web.rest;

import com.jio.tms.JioTptApplicationApp;
import com.jio.tms.domain.Insurance;
import com.jio.tms.repository.InsuranceRepository;
import com.jio.tms.repository.search.InsuranceSearchRepository;
import com.jio.tms.service.InsuranceService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link InsuranceResource} REST controller.
 */
@SpringBootTest(classes = JioTptApplicationApp.class)
public class InsuranceResourceIT {

    private static final String DEFAULT_PROVIDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROVIDER_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ISSUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ISSUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_EXPIRY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPIRY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_POLICY_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POLICY_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_POLICY_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POLICY_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_COVERAGE_STATEMENT = "AAAAAAAAAA";
    private static final String UPDATED_COVERAGE_STATEMENT = "BBBBBBBBBB";

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private InsuranceService insuranceService;

    /**
     * This repository is mocked in the com.jio.tms.repository.search test package.
     *
     * @see com.jio.tms.repository.search.InsuranceSearchRepositoryMockConfiguration
     */
    @Autowired
    private InsuranceSearchRepository mockInsuranceSearchRepository;

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

    private MockMvc restInsuranceMockMvc;

    private Insurance insurance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InsuranceResource insuranceResource = new InsuranceResource(insuranceService);
        this.restInsuranceMockMvc = MockMvcBuilders.standaloneSetup(insuranceResource)
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
    public static Insurance createEntity(EntityManager em) {
        Insurance insurance = new Insurance()
            .providerName(DEFAULT_PROVIDER_NAME)
            .issueDate(DEFAULT_ISSUE_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .policyDocument(DEFAULT_POLICY_DOCUMENT)
            .policyDocumentContentType(DEFAULT_POLICY_DOCUMENT_CONTENT_TYPE)
            .coverageStatement(DEFAULT_COVERAGE_STATEMENT);
        return insurance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Insurance createUpdatedEntity(EntityManager em) {
        Insurance insurance = new Insurance()
            .providerName(UPDATED_PROVIDER_NAME)
            .issueDate(UPDATED_ISSUE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .policyDocument(UPDATED_POLICY_DOCUMENT)
            .policyDocumentContentType(UPDATED_POLICY_DOCUMENT_CONTENT_TYPE)
            .coverageStatement(UPDATED_COVERAGE_STATEMENT);
        return insurance;
    }

    @BeforeEach
    public void initTest() {
        insurance = createEntity(em);
    }

    @Test
    @Transactional
    public void createInsurance() throws Exception {
        int databaseSizeBeforeCreate = insuranceRepository.findAll().size();

        // Create the Insurance
        restInsuranceMockMvc.perform(post("/api/insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurance)))
            .andExpect(status().isCreated());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeCreate + 1);
        Insurance testInsurance = insuranceList.get(insuranceList.size() - 1);
        assertThat(testInsurance.getProviderName()).isEqualTo(DEFAULT_PROVIDER_NAME);
        assertThat(testInsurance.getIssueDate()).isEqualTo(DEFAULT_ISSUE_DATE);
        assertThat(testInsurance.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testInsurance.getPolicyDocument()).isEqualTo(DEFAULT_POLICY_DOCUMENT);
        assertThat(testInsurance.getPolicyDocumentContentType()).isEqualTo(DEFAULT_POLICY_DOCUMENT_CONTENT_TYPE);
        assertThat(testInsurance.getCoverageStatement()).isEqualTo(DEFAULT_COVERAGE_STATEMENT);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(1)).save(testInsurance);
    }

    @Test
    @Transactional
    public void createInsuranceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = insuranceRepository.findAll().size();

        // Create the Insurance with an existing ID
        insurance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInsuranceMockMvc.perform(post("/api/insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurance)))
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(0)).save(insurance);
    }


    @Test
    @Transactional
    public void getAllInsurances() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        // Get all the insuranceList
        restInsuranceMockMvc.perform(get("/api/insurances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].providerName").value(hasItem(DEFAULT_PROVIDER_NAME)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].policyDocumentContentType").value(hasItem(DEFAULT_POLICY_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].policyDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_POLICY_DOCUMENT))))
            .andExpect(jsonPath("$.[*].coverageStatement").value(hasItem(DEFAULT_COVERAGE_STATEMENT)));
    }
    
    @Test
    @Transactional
    public void getInsurance() throws Exception {
        // Initialize the database
        insuranceRepository.saveAndFlush(insurance);

        // Get the insurance
        restInsuranceMockMvc.perform(get("/api/insurances/{id}", insurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(insurance.getId().intValue()))
            .andExpect(jsonPath("$.providerName").value(DEFAULT_PROVIDER_NAME))
            .andExpect(jsonPath("$.issueDate").value(DEFAULT_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.policyDocumentContentType").value(DEFAULT_POLICY_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.policyDocument").value(Base64Utils.encodeToString(DEFAULT_POLICY_DOCUMENT)))
            .andExpect(jsonPath("$.coverageStatement").value(DEFAULT_COVERAGE_STATEMENT));
    }

    @Test
    @Transactional
    public void getNonExistingInsurance() throws Exception {
        // Get the insurance
        restInsuranceMockMvc.perform(get("/api/insurances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInsurance() throws Exception {
        // Initialize the database
        insuranceService.save(insurance);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockInsuranceSearchRepository);

        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();

        // Update the insurance
        Insurance updatedInsurance = insuranceRepository.findById(insurance.getId()).get();
        // Disconnect from session so that the updates on updatedInsurance are not directly saved in db
        em.detach(updatedInsurance);
        updatedInsurance
            .providerName(UPDATED_PROVIDER_NAME)
            .issueDate(UPDATED_ISSUE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .policyDocument(UPDATED_POLICY_DOCUMENT)
            .policyDocumentContentType(UPDATED_POLICY_DOCUMENT_CONTENT_TYPE)
            .coverageStatement(UPDATED_COVERAGE_STATEMENT);

        restInsuranceMockMvc.perform(put("/api/insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInsurance)))
            .andExpect(status().isOk());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);
        Insurance testInsurance = insuranceList.get(insuranceList.size() - 1);
        assertThat(testInsurance.getProviderName()).isEqualTo(UPDATED_PROVIDER_NAME);
        assertThat(testInsurance.getIssueDate()).isEqualTo(UPDATED_ISSUE_DATE);
        assertThat(testInsurance.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testInsurance.getPolicyDocument()).isEqualTo(UPDATED_POLICY_DOCUMENT);
        assertThat(testInsurance.getPolicyDocumentContentType()).isEqualTo(UPDATED_POLICY_DOCUMENT_CONTENT_TYPE);
        assertThat(testInsurance.getCoverageStatement()).isEqualTo(UPDATED_COVERAGE_STATEMENT);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(1)).save(testInsurance);
    }

    @Test
    @Transactional
    public void updateNonExistingInsurance() throws Exception {
        int databaseSizeBeforeUpdate = insuranceRepository.findAll().size();

        // Create the Insurance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInsuranceMockMvc.perform(put("/api/insurances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(insurance)))
            .andExpect(status().isBadRequest());

        // Validate the Insurance in the database
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(0)).save(insurance);
    }

    @Test
    @Transactional
    public void deleteInsurance() throws Exception {
        // Initialize the database
        insuranceService.save(insurance);

        int databaseSizeBeforeDelete = insuranceRepository.findAll().size();

        // Delete the insurance
        restInsuranceMockMvc.perform(delete("/api/insurances/{id}", insurance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Insurance> insuranceList = insuranceRepository.findAll();
        assertThat(insuranceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Insurance in Elasticsearch
        verify(mockInsuranceSearchRepository, times(1)).deleteById(insurance.getId());
    }

    @Test
    @Transactional
    public void searchInsurance() throws Exception {
        // Initialize the database
        insuranceService.save(insurance);
        when(mockInsuranceSearchRepository.search(queryStringQuery("id:" + insurance.getId())))
            .thenReturn(Collections.singletonList(insurance));
        // Search the insurance
        restInsuranceMockMvc.perform(get("/api/_search/insurances?query=id:" + insurance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(insurance.getId().intValue())))
            .andExpect(jsonPath("$.[*].providerName").value(hasItem(DEFAULT_PROVIDER_NAME)))
            .andExpect(jsonPath("$.[*].issueDate").value(hasItem(DEFAULT_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].policyDocumentContentType").value(hasItem(DEFAULT_POLICY_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].policyDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_POLICY_DOCUMENT))))
            .andExpect(jsonPath("$.[*].coverageStatement").value(hasItem(DEFAULT_COVERAGE_STATEMENT)));
    }
}
