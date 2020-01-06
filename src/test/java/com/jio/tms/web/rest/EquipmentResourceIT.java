package com.jio.tms.web.rest;

import com.jio.tms.JioTptApplicationApp;
import com.jio.tms.domain.Equipment;
import com.jio.tms.repository.EquipmentRepository;
import com.jio.tms.repository.search.EquipmentSearchRepository;
import com.jio.tms.service.EquipmentService;
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

import com.jio.tms.domain.enumeration.EquipmentEnum;
import com.jio.tms.domain.enumeration.ToggleStatus;
/**
 * Integration tests for the {@link EquipmentResource} REST controller.
 */
@SpringBootTest(classes = JioTptApplicationApp.class)
public class EquipmentResourceIT {

    private static final String DEFAULT_ENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ENUMBER = "BBBBBBBBBB";

    private static final EquipmentEnum DEFAULT_TYPE = EquipmentEnum.TRAILER;
    private static final EquipmentEnum UPDATED_TYPE = EquipmentEnum.CONTAINER;

    private static final String DEFAULT_OWNERSHIPTYPE = "AAAAAAAAAA";
    private static final String UPDATED_OWNERSHIPTYPE = "BBBBBBBBBB";

    private static final ToggleStatus DEFAULT_STATUS = ToggleStatus.ACTIVE;
    private static final ToggleStatus UPDATED_STATUS = ToggleStatus.INACTIVE;

    private static final String DEFAULT_VIN = "AAAAAAAAAA";
    private static final String UPDATED_VIN = "BBBBBBBBBB";

    private static final String DEFAULT_MAKE = "AAAAAAAAAA";
    private static final String UPDATED_MAKE = "BBBBBBBBBB";

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR_PURCHASED = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_PURCHASED = "BBBBBBBBBB";

    private static final String DEFAULT_LICENSE_PLATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_PLATE_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LICENSE_PLATE_EXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LICENSE_PLATE_EXPIRATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_INSPECTION_STICKER_EXPIRATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INSPECTION_STICKER_EXPIRATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentService equipmentService;

    /**
     * This repository is mocked in the com.jio.tms.repository.search test package.
     *
     * @see com.jio.tms.repository.search.EquipmentSearchRepositoryMockConfiguration
     */
    @Autowired
    private EquipmentSearchRepository mockEquipmentSearchRepository;

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

    private MockMvc restEquipmentMockMvc;

    private Equipment equipment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipmentResource equipmentResource = new EquipmentResource(equipmentService);
        this.restEquipmentMockMvc = MockMvcBuilders.standaloneSetup(equipmentResource)
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
    public static Equipment createEntity(EntityManager em) {
        Equipment equipment = new Equipment()
            .enumber(DEFAULT_ENUMBER)
            .type(DEFAULT_TYPE)
            .ownershiptype(DEFAULT_OWNERSHIPTYPE)
            .status(DEFAULT_STATUS)
            .vin(DEFAULT_VIN)
            .make(DEFAULT_MAKE)
            .model(DEFAULT_MODEL)
            .description(DEFAULT_DESCRIPTION)
            .year(DEFAULT_YEAR)
            .yearPurchased(DEFAULT_YEAR_PURCHASED)
            .licensePlateNumber(DEFAULT_LICENSE_PLATE_NUMBER)
            .licensePlateExpiration(DEFAULT_LICENSE_PLATE_EXPIRATION)
            .inspectionStickerExpiration(DEFAULT_INSPECTION_STICKER_EXPIRATION);
        return equipment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipment createUpdatedEntity(EntityManager em) {
        Equipment equipment = new Equipment()
            .enumber(UPDATED_ENUMBER)
            .type(UPDATED_TYPE)
            .ownershiptype(UPDATED_OWNERSHIPTYPE)
            .status(UPDATED_STATUS)
            .vin(UPDATED_VIN)
            .make(UPDATED_MAKE)
            .model(UPDATED_MODEL)
            .description(UPDATED_DESCRIPTION)
            .year(UPDATED_YEAR)
            .yearPurchased(UPDATED_YEAR_PURCHASED)
            .licensePlateNumber(UPDATED_LICENSE_PLATE_NUMBER)
            .licensePlateExpiration(UPDATED_LICENSE_PLATE_EXPIRATION)
            .inspectionStickerExpiration(UPDATED_INSPECTION_STICKER_EXPIRATION);
        return equipment;
    }

    @BeforeEach
    public void initTest() {
        equipment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipment() throws Exception {
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Create the Equipment
        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isCreated());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate + 1);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getEnumber()).isEqualTo(DEFAULT_ENUMBER);
        assertThat(testEquipment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEquipment.getOwnershiptype()).isEqualTo(DEFAULT_OWNERSHIPTYPE);
        assertThat(testEquipment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEquipment.getVin()).isEqualTo(DEFAULT_VIN);
        assertThat(testEquipment.getMake()).isEqualTo(DEFAULT_MAKE);
        assertThat(testEquipment.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testEquipment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEquipment.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testEquipment.getYearPurchased()).isEqualTo(DEFAULT_YEAR_PURCHASED);
        assertThat(testEquipment.getLicensePlateNumber()).isEqualTo(DEFAULT_LICENSE_PLATE_NUMBER);
        assertThat(testEquipment.getLicensePlateExpiration()).isEqualTo(DEFAULT_LICENSE_PLATE_EXPIRATION);
        assertThat(testEquipment.getInspectionStickerExpiration()).isEqualTo(DEFAULT_INSPECTION_STICKER_EXPIRATION);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(1)).save(testEquipment);
    }

    @Test
    @Transactional
    public void createEquipmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipmentRepository.findAll().size();

        // Create the Equipment with an existing ID
        equipment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentMockMvc.perform(post("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeCreate);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(0)).save(equipment);
    }


    @Test
    @Transactional
    public void getAllEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get all the equipmentList
        restEquipmentMockMvc.perform(get("/api/equipment?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].enumber").value(hasItem(DEFAULT_ENUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ownershiptype").value(hasItem(DEFAULT_OWNERSHIPTYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vin").value(hasItem(DEFAULT_VIN)))
            .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].yearPurchased").value(hasItem(DEFAULT_YEAR_PURCHASED)))
            .andExpect(jsonPath("$.[*].licensePlateNumber").value(hasItem(DEFAULT_LICENSE_PLATE_NUMBER)))
            .andExpect(jsonPath("$.[*].licensePlateExpiration").value(hasItem(DEFAULT_LICENSE_PLATE_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].inspectionStickerExpiration").value(hasItem(DEFAULT_INSPECTION_STICKER_EXPIRATION.toString())));
    }
    
    @Test
    @Transactional
    public void getEquipment() throws Exception {
        // Initialize the database
        equipmentRepository.saveAndFlush(equipment);

        // Get the equipment
        restEquipmentMockMvc.perform(get("/api/equipment/{id}", equipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipment.getId().intValue()))
            .andExpect(jsonPath("$.enumber").value(DEFAULT_ENUMBER))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.ownershiptype").value(DEFAULT_OWNERSHIPTYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.vin").value(DEFAULT_VIN))
            .andExpect(jsonPath("$.make").value(DEFAULT_MAKE))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.yearPurchased").value(DEFAULT_YEAR_PURCHASED))
            .andExpect(jsonPath("$.licensePlateNumber").value(DEFAULT_LICENSE_PLATE_NUMBER))
            .andExpect(jsonPath("$.licensePlateExpiration").value(DEFAULT_LICENSE_PLATE_EXPIRATION.toString()))
            .andExpect(jsonPath("$.inspectionStickerExpiration").value(DEFAULT_INSPECTION_STICKER_EXPIRATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEquipment() throws Exception {
        // Get the equipment
        restEquipmentMockMvc.perform(get("/api/equipment/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipment() throws Exception {
        // Initialize the database
        equipmentService.save(equipment);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockEquipmentSearchRepository);

        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Update the equipment
        Equipment updatedEquipment = equipmentRepository.findById(equipment.getId()).get();
        // Disconnect from session so that the updates on updatedEquipment are not directly saved in db
        em.detach(updatedEquipment);
        updatedEquipment
            .enumber(UPDATED_ENUMBER)
            .type(UPDATED_TYPE)
            .ownershiptype(UPDATED_OWNERSHIPTYPE)
            .status(UPDATED_STATUS)
            .vin(UPDATED_VIN)
            .make(UPDATED_MAKE)
            .model(UPDATED_MODEL)
            .description(UPDATED_DESCRIPTION)
            .year(UPDATED_YEAR)
            .yearPurchased(UPDATED_YEAR_PURCHASED)
            .licensePlateNumber(UPDATED_LICENSE_PLATE_NUMBER)
            .licensePlateExpiration(UPDATED_LICENSE_PLATE_EXPIRATION)
            .inspectionStickerExpiration(UPDATED_INSPECTION_STICKER_EXPIRATION);

        restEquipmentMockMvc.perform(put("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipment)))
            .andExpect(status().isOk());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);
        Equipment testEquipment = equipmentList.get(equipmentList.size() - 1);
        assertThat(testEquipment.getEnumber()).isEqualTo(UPDATED_ENUMBER);
        assertThat(testEquipment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEquipment.getOwnershiptype()).isEqualTo(UPDATED_OWNERSHIPTYPE);
        assertThat(testEquipment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEquipment.getVin()).isEqualTo(UPDATED_VIN);
        assertThat(testEquipment.getMake()).isEqualTo(UPDATED_MAKE);
        assertThat(testEquipment.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testEquipment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEquipment.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testEquipment.getYearPurchased()).isEqualTo(UPDATED_YEAR_PURCHASED);
        assertThat(testEquipment.getLicensePlateNumber()).isEqualTo(UPDATED_LICENSE_PLATE_NUMBER);
        assertThat(testEquipment.getLicensePlateExpiration()).isEqualTo(UPDATED_LICENSE_PLATE_EXPIRATION);
        assertThat(testEquipment.getInspectionStickerExpiration()).isEqualTo(UPDATED_INSPECTION_STICKER_EXPIRATION);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(1)).save(testEquipment);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipment() throws Exception {
        int databaseSizeBeforeUpdate = equipmentRepository.findAll().size();

        // Create the Equipment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipmentMockMvc.perform(put("/api/equipment")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipment)))
            .andExpect(status().isBadRequest());

        // Validate the Equipment in the database
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(0)).save(equipment);
    }

    @Test
    @Transactional
    public void deleteEquipment() throws Exception {
        // Initialize the database
        equipmentService.save(equipment);

        int databaseSizeBeforeDelete = equipmentRepository.findAll().size();

        // Delete the equipment
        restEquipmentMockMvc.perform(delete("/api/equipment/{id}", equipment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipment> equipmentList = equipmentRepository.findAll();
        assertThat(equipmentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Equipment in Elasticsearch
        verify(mockEquipmentSearchRepository, times(1)).deleteById(equipment.getId());
    }

    @Test
    @Transactional
    public void searchEquipment() throws Exception {
        // Initialize the database
        equipmentService.save(equipment);
        when(mockEquipmentSearchRepository.search(queryStringQuery("id:" + equipment.getId())))
            .thenReturn(Collections.singletonList(equipment));
        // Search the equipment
        restEquipmentMockMvc.perform(get("/api/_search/equipment?query=id:" + equipment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipment.getId().intValue())))
            .andExpect(jsonPath("$.[*].enumber").value(hasItem(DEFAULT_ENUMBER)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ownershiptype").value(hasItem(DEFAULT_OWNERSHIPTYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].vin").value(hasItem(DEFAULT_VIN)))
            .andExpect(jsonPath("$.[*].make").value(hasItem(DEFAULT_MAKE)))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].yearPurchased").value(hasItem(DEFAULT_YEAR_PURCHASED)))
            .andExpect(jsonPath("$.[*].licensePlateNumber").value(hasItem(DEFAULT_LICENSE_PLATE_NUMBER)))
            .andExpect(jsonPath("$.[*].licensePlateExpiration").value(hasItem(DEFAULT_LICENSE_PLATE_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].inspectionStickerExpiration").value(hasItem(DEFAULT_INSPECTION_STICKER_EXPIRATION.toString())));
    }
}
