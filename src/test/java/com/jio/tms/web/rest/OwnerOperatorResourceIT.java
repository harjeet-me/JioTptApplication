package com.jio.tms.web.rest;

import com.jio.tms.JioTptApplicationApp;
import com.jio.tms.domain.OwnerOperator;
import com.jio.tms.repository.OwnerOperatorRepository;
import com.jio.tms.repository.search.OwnerOperatorSearchRepository;
import com.jio.tms.service.OwnerOperatorService;
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
import java.util.Collections;
import java.util.List;

import static com.jio.tms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jio.tms.domain.enumeration.Designation;
import com.jio.tms.domain.enumeration.CountryEnum;
import com.jio.tms.domain.enumeration.ToggleStatus;
import com.jio.tms.domain.enumeration.CURRENCY;
/**
 * Integration tests for the {@link OwnerOperatorResource} REST controller.
 */
@SpringBootTest(classes = JioTptApplicationApp.class)
public class OwnerOperatorResourceIT {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Designation DEFAULT_CONTACT_DESIGNATION = Designation.MANAGER;
    private static final Designation UPDATED_CONTACT_DESIGNATION = Designation.ACCOUNTANT;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final CountryEnum DEFAULT_COUNTRY = CountryEnum.USA;
    private static final CountryEnum UPDATED_COUNTRY = CountryEnum.CANADA;

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DOT = "AAAAAAAAAA";
    private static final String UPDATED_DOT = "BBBBBBBBBB";

    private static final Long DEFAULT_MC = 1L;
    private static final Long UPDATED_MC = 2L;

    private static final ToggleStatus DEFAULT_PROFILE_STATUS = ToggleStatus.ACTIVE;
    private static final ToggleStatus UPDATED_PROFILE_STATUS = ToggleStatus.INACTIVE;

    private static final CURRENCY DEFAULT_PREFFRED_CURRENCY = CURRENCY.USD;
    private static final CURRENCY UPDATED_PREFFRED_CURRENCY = CURRENCY.CAD;

    private static final byte[] DEFAULT_CONTRACT_DOC = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTRACT_DOC = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTRACT_DOC_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTRACT_DOC_CONTENT_TYPE = "image/png";

    @Autowired
    private OwnerOperatorRepository ownerOperatorRepository;

    @Autowired
    private OwnerOperatorService ownerOperatorService;

    /**
     * This repository is mocked in the com.jio.tms.repository.search test package.
     *
     * @see com.jio.tms.repository.search.OwnerOperatorSearchRepositoryMockConfiguration
     */
    @Autowired
    private OwnerOperatorSearchRepository mockOwnerOperatorSearchRepository;

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

    private MockMvc restOwnerOperatorMockMvc;

    private OwnerOperator ownerOperator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OwnerOperatorResource ownerOperatorResource = new OwnerOperatorResource(ownerOperatorService);
        this.restOwnerOperatorMockMvc = MockMvcBuilders.standaloneSetup(ownerOperatorResource)
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
    public static OwnerOperator createEntity(EntityManager em) {
        OwnerOperator ownerOperator = new OwnerOperator()
            .company(DEFAULT_COMPANY)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .contactDesignation(DEFAULT_CONTACT_DESIGNATION)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .address(DEFAULT_ADDRESS)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .country(DEFAULT_COUNTRY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .dot(DEFAULT_DOT)
            .mc(DEFAULT_MC)
            .profileStatus(DEFAULT_PROFILE_STATUS)
            .preffredCurrency(DEFAULT_PREFFRED_CURRENCY)
            .contractDoc(DEFAULT_CONTRACT_DOC)
            .contractDocContentType(DEFAULT_CONTRACT_DOC_CONTENT_TYPE);
        return ownerOperator;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OwnerOperator createUpdatedEntity(EntityManager em) {
        OwnerOperator ownerOperator = new OwnerOperator()
            .company(UPDATED_COMPANY)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .contactDesignation(UPDATED_CONTACT_DESIGNATION)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postalCode(UPDATED_POSTAL_CODE)
            .dot(UPDATED_DOT)
            .mc(UPDATED_MC)
            .profileStatus(UPDATED_PROFILE_STATUS)
            .preffredCurrency(UPDATED_PREFFRED_CURRENCY)
            .contractDoc(UPDATED_CONTRACT_DOC)
            .contractDocContentType(UPDATED_CONTRACT_DOC_CONTENT_TYPE);
        return ownerOperator;
    }

    @BeforeEach
    public void initTest() {
        ownerOperator = createEntity(em);
    }

    @Test
    @Transactional
    public void createOwnerOperator() throws Exception {
        int databaseSizeBeforeCreate = ownerOperatorRepository.findAll().size();

        // Create the OwnerOperator
        restOwnerOperatorMockMvc.perform(post("/api/owner-operators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerOperator)))
            .andExpect(status().isCreated());

        // Validate the OwnerOperator in the database
        List<OwnerOperator> ownerOperatorList = ownerOperatorRepository.findAll();
        assertThat(ownerOperatorList).hasSize(databaseSizeBeforeCreate + 1);
        OwnerOperator testOwnerOperator = ownerOperatorList.get(ownerOperatorList.size() - 1);
        assertThat(testOwnerOperator.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testOwnerOperator.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testOwnerOperator.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testOwnerOperator.getContactDesignation()).isEqualTo(DEFAULT_CONTACT_DESIGNATION);
        assertThat(testOwnerOperator.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOwnerOperator.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testOwnerOperator.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOwnerOperator.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testOwnerOperator.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testOwnerOperator.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testOwnerOperator.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testOwnerOperator.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testOwnerOperator.getDot()).isEqualTo(DEFAULT_DOT);
        assertThat(testOwnerOperator.getMc()).isEqualTo(DEFAULT_MC);
        assertThat(testOwnerOperator.getProfileStatus()).isEqualTo(DEFAULT_PROFILE_STATUS);
        assertThat(testOwnerOperator.getPreffredCurrency()).isEqualTo(DEFAULT_PREFFRED_CURRENCY);
        assertThat(testOwnerOperator.getContractDoc()).isEqualTo(DEFAULT_CONTRACT_DOC);
        assertThat(testOwnerOperator.getContractDocContentType()).isEqualTo(DEFAULT_CONTRACT_DOC_CONTENT_TYPE);

        // Validate the OwnerOperator in Elasticsearch
        verify(mockOwnerOperatorSearchRepository, times(1)).save(testOwnerOperator);
    }

    @Test
    @Transactional
    public void createOwnerOperatorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ownerOperatorRepository.findAll().size();

        // Create the OwnerOperator with an existing ID
        ownerOperator.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOwnerOperatorMockMvc.perform(post("/api/owner-operators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerOperator)))
            .andExpect(status().isBadRequest());

        // Validate the OwnerOperator in the database
        List<OwnerOperator> ownerOperatorList = ownerOperatorRepository.findAll();
        assertThat(ownerOperatorList).hasSize(databaseSizeBeforeCreate);

        // Validate the OwnerOperator in Elasticsearch
        verify(mockOwnerOperatorSearchRepository, times(0)).save(ownerOperator);
    }


    @Test
    @Transactional
    public void getAllOwnerOperators() throws Exception {
        // Initialize the database
        ownerOperatorRepository.saveAndFlush(ownerOperator);

        // Get all the ownerOperatorList
        restOwnerOperatorMockMvc.perform(get("/api/owner-operators?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerOperator.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].contactDesignation").value(hasItem(DEFAULT_CONTACT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].dot").value(hasItem(DEFAULT_DOT)))
            .andExpect(jsonPath("$.[*].mc").value(hasItem(DEFAULT_MC.intValue())))
            .andExpect(jsonPath("$.[*].profileStatus").value(hasItem(DEFAULT_PROFILE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].preffredCurrency").value(hasItem(DEFAULT_PREFFRED_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].contractDocContentType").value(hasItem(DEFAULT_CONTRACT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contractDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTRACT_DOC))));
    }
    
    @Test
    @Transactional
    public void getOwnerOperator() throws Exception {
        // Initialize the database
        ownerOperatorRepository.saveAndFlush(ownerOperator);

        // Get the ownerOperator
        restOwnerOperatorMockMvc.perform(get("/api/owner-operators/{id}", ownerOperator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ownerOperator.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.contactDesignation").value(DEFAULT_CONTACT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.dot").value(DEFAULT_DOT))
            .andExpect(jsonPath("$.mc").value(DEFAULT_MC.intValue()))
            .andExpect(jsonPath("$.profileStatus").value(DEFAULT_PROFILE_STATUS.toString()))
            .andExpect(jsonPath("$.preffredCurrency").value(DEFAULT_PREFFRED_CURRENCY.toString()))
            .andExpect(jsonPath("$.contractDocContentType").value(DEFAULT_CONTRACT_DOC_CONTENT_TYPE))
            .andExpect(jsonPath("$.contractDoc").value(Base64Utils.encodeToString(DEFAULT_CONTRACT_DOC)));
    }

    @Test
    @Transactional
    public void getNonExistingOwnerOperator() throws Exception {
        // Get the ownerOperator
        restOwnerOperatorMockMvc.perform(get("/api/owner-operators/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOwnerOperator() throws Exception {
        // Initialize the database
        ownerOperatorService.save(ownerOperator);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockOwnerOperatorSearchRepository);

        int databaseSizeBeforeUpdate = ownerOperatorRepository.findAll().size();

        // Update the ownerOperator
        OwnerOperator updatedOwnerOperator = ownerOperatorRepository.findById(ownerOperator.getId()).get();
        // Disconnect from session so that the updates on updatedOwnerOperator are not directly saved in db
        em.detach(updatedOwnerOperator);
        updatedOwnerOperator
            .company(UPDATED_COMPANY)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .contactDesignation(UPDATED_CONTACT_DESIGNATION)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .address(UPDATED_ADDRESS)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .country(UPDATED_COUNTRY)
            .postalCode(UPDATED_POSTAL_CODE)
            .dot(UPDATED_DOT)
            .mc(UPDATED_MC)
            .profileStatus(UPDATED_PROFILE_STATUS)
            .preffredCurrency(UPDATED_PREFFRED_CURRENCY)
            .contractDoc(UPDATED_CONTRACT_DOC)
            .contractDocContentType(UPDATED_CONTRACT_DOC_CONTENT_TYPE);

        restOwnerOperatorMockMvc.perform(put("/api/owner-operators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOwnerOperator)))
            .andExpect(status().isOk());

        // Validate the OwnerOperator in the database
        List<OwnerOperator> ownerOperatorList = ownerOperatorRepository.findAll();
        assertThat(ownerOperatorList).hasSize(databaseSizeBeforeUpdate);
        OwnerOperator testOwnerOperator = ownerOperatorList.get(ownerOperatorList.size() - 1);
        assertThat(testOwnerOperator.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testOwnerOperator.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testOwnerOperator.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testOwnerOperator.getContactDesignation()).isEqualTo(UPDATED_CONTACT_DESIGNATION);
        assertThat(testOwnerOperator.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOwnerOperator.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testOwnerOperator.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOwnerOperator.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testOwnerOperator.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testOwnerOperator.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testOwnerOperator.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testOwnerOperator.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testOwnerOperator.getDot()).isEqualTo(UPDATED_DOT);
        assertThat(testOwnerOperator.getMc()).isEqualTo(UPDATED_MC);
        assertThat(testOwnerOperator.getProfileStatus()).isEqualTo(UPDATED_PROFILE_STATUS);
        assertThat(testOwnerOperator.getPreffredCurrency()).isEqualTo(UPDATED_PREFFRED_CURRENCY);
        assertThat(testOwnerOperator.getContractDoc()).isEqualTo(UPDATED_CONTRACT_DOC);
        assertThat(testOwnerOperator.getContractDocContentType()).isEqualTo(UPDATED_CONTRACT_DOC_CONTENT_TYPE);

        // Validate the OwnerOperator in Elasticsearch
        verify(mockOwnerOperatorSearchRepository, times(1)).save(testOwnerOperator);
    }

    @Test
    @Transactional
    public void updateNonExistingOwnerOperator() throws Exception {
        int databaseSizeBeforeUpdate = ownerOperatorRepository.findAll().size();

        // Create the OwnerOperator

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOwnerOperatorMockMvc.perform(put("/api/owner-operators")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ownerOperator)))
            .andExpect(status().isBadRequest());

        // Validate the OwnerOperator in the database
        List<OwnerOperator> ownerOperatorList = ownerOperatorRepository.findAll();
        assertThat(ownerOperatorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the OwnerOperator in Elasticsearch
        verify(mockOwnerOperatorSearchRepository, times(0)).save(ownerOperator);
    }

    @Test
    @Transactional
    public void deleteOwnerOperator() throws Exception {
        // Initialize the database
        ownerOperatorService.save(ownerOperator);

        int databaseSizeBeforeDelete = ownerOperatorRepository.findAll().size();

        // Delete the ownerOperator
        restOwnerOperatorMockMvc.perform(delete("/api/owner-operators/{id}", ownerOperator.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OwnerOperator> ownerOperatorList = ownerOperatorRepository.findAll();
        assertThat(ownerOperatorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the OwnerOperator in Elasticsearch
        verify(mockOwnerOperatorSearchRepository, times(1)).deleteById(ownerOperator.getId());
    }

    @Test
    @Transactional
    public void searchOwnerOperator() throws Exception {
        // Initialize the database
        ownerOperatorService.save(ownerOperator);
        when(mockOwnerOperatorSearchRepository.search(queryStringQuery("id:" + ownerOperator.getId())))
            .thenReturn(Collections.singletonList(ownerOperator));
        // Search the ownerOperator
        restOwnerOperatorMockMvc.perform(get("/api/_search/owner-operators?query=id:" + ownerOperator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ownerOperator.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].contactDesignation").value(hasItem(DEFAULT_CONTACT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].dot").value(hasItem(DEFAULT_DOT)))
            .andExpect(jsonPath("$.[*].mc").value(hasItem(DEFAULT_MC.intValue())))
            .andExpect(jsonPath("$.[*].profileStatus").value(hasItem(DEFAULT_PROFILE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].preffredCurrency").value(hasItem(DEFAULT_PREFFRED_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].contractDocContentType").value(hasItem(DEFAULT_CONTRACT_DOC_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contractDoc").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTRACT_DOC))));
    }
}
