package com.jio.tms.web.rest;

import com.jio.tms.JioTptApplicationApp;
import com.jio.tms.domain.Trip;
import com.jio.tms.repository.TripRepository;
import com.jio.tms.repository.search.TripSearchRepository;
import com.jio.tms.service.TripService;
import com.jio.tms.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.jio.tms.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jio.tms.domain.enumeration.StatusEnum;
import com.jio.tms.domain.enumeration.HAZMAT;
import com.jio.tms.domain.enumeration.COVEREDBY;
import com.jio.tms.domain.enumeration.LoadType;
import com.jio.tms.domain.enumeration.SizeEnum;
/**
 * Integration tests for the {@link TripResource} REST controller.
 */
@SpringBootTest(classes = JioTptApplicationApp.class)
public class TripResourceIT {

    private static final String DEFAULT_TRIP_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRIP_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPMENT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SHIPMENT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_BOL = "AAAAAAAAAA";
    private static final String UPDATED_BOL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PICKUP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PICKUP = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DROP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DROP = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PICKUP_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_PICKUP_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_DROP_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_DROP_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_LOCATION = "BBBBBBBBBB";

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.PICKEDUP;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.ONROAD;

    private static final Long DEFAULT_DETENTION = 1L;
    private static final Long UPDATED_DETENTION = 2L;

    private static final Instant DEFAULT_CHASIS_IN_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHASIS_IN_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_POD = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_POD = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_POD_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_POD_CONTENT_TYPE = "image/png";

    private static final HAZMAT DEFAULT_HAZMAT = HAZMAT.YES;
    private static final HAZMAT UPDATED_HAZMAT = HAZMAT.NO;

    private static final String DEFAULT_RECIEVED_BY = "AAAAAAAAAA";
    private static final String UPDATED_RECIEVED_BY = "BBBBBBBBBB";

    private static final COVEREDBY DEFAULT_COVERED_BY = COVEREDBY.CompanyDriver;
    private static final COVEREDBY UPDATED_COVERED_BY = COVEREDBY.OwnerOperator;

    private static final LoadType DEFAULT_LOAD_TYPE = LoadType.REEFER;
    private static final LoadType UPDATED_LOAD_TYPE = LoadType.FLATBED;

    private static final SizeEnum DEFAULT_CONTAINER_SIZE = SizeEnum.C53;
    private static final SizeEnum UPDATED_CONTAINER_SIZE = SizeEnum.C43;

    private static final Integer DEFAULT_NUMBERS_OF_CONTAINER = 1;
    private static final Integer UPDATED_NUMBERS_OF_CONTAINER = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripService tripService;

    /**
     * This repository is mocked in the com.jio.tms.repository.search test package.
     *
     * @see com.jio.tms.repository.search.TripSearchRepositoryMockConfiguration
     */
    @Autowired
    private TripSearchRepository mockTripSearchRepository;

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

    private MockMvc restTripMockMvc;

    private Trip trip;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TripResource tripResource = new TripResource(tripService);
        this.restTripMockMvc = MockMvcBuilders.standaloneSetup(tripResource)
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
    public static Trip createEntity(EntityManager em) {
        Trip trip = new Trip()
            .tripNumber(DEFAULT_TRIP_NUMBER)
            .description(DEFAULT_DESCRIPTION)
            .shipmentNumber(DEFAULT_SHIPMENT_NUMBER)
            .bol(DEFAULT_BOL)
            .pickup(DEFAULT_PICKUP)
            .drop(DEFAULT_DROP)
            .pickupLocation(DEFAULT_PICKUP_LOCATION)
            .dropLocation(DEFAULT_DROP_LOCATION)
            .currentLocation(DEFAULT_CURRENT_LOCATION)
            .status(DEFAULT_STATUS)
            .detention(DEFAULT_DETENTION)
            .chasisInTime(DEFAULT_CHASIS_IN_TIME)
            .pod(DEFAULT_POD)
            .podContentType(DEFAULT_POD_CONTENT_TYPE)
            .hazmat(DEFAULT_HAZMAT)
            .recievedBy(DEFAULT_RECIEVED_BY)
            .coveredBy(DEFAULT_COVERED_BY)
            .loadType(DEFAULT_LOAD_TYPE)
            .containerSize(DEFAULT_CONTAINER_SIZE)
            .numbersOfContainer(DEFAULT_NUMBERS_OF_CONTAINER)
            .comments(DEFAULT_COMMENTS);
        return trip;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Trip createUpdatedEntity(EntityManager em) {
        Trip trip = new Trip()
            .tripNumber(UPDATED_TRIP_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .shipmentNumber(UPDATED_SHIPMENT_NUMBER)
            .bol(UPDATED_BOL)
            .pickup(UPDATED_PICKUP)
            .drop(UPDATED_DROP)
            .pickupLocation(UPDATED_PICKUP_LOCATION)
            .dropLocation(UPDATED_DROP_LOCATION)
            .currentLocation(UPDATED_CURRENT_LOCATION)
            .status(UPDATED_STATUS)
            .detention(UPDATED_DETENTION)
            .chasisInTime(UPDATED_CHASIS_IN_TIME)
            .pod(UPDATED_POD)
            .podContentType(UPDATED_POD_CONTENT_TYPE)
            .hazmat(UPDATED_HAZMAT)
            .recievedBy(UPDATED_RECIEVED_BY)
            .coveredBy(UPDATED_COVERED_BY)
            .loadType(UPDATED_LOAD_TYPE)
            .containerSize(UPDATED_CONTAINER_SIZE)
            .numbersOfContainer(UPDATED_NUMBERS_OF_CONTAINER)
            .comments(UPDATED_COMMENTS);
        return trip;
    }

    @BeforeEach
    public void initTest() {
        trip = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getTripNumber()).isEqualTo(DEFAULT_TRIP_NUMBER);
        assertThat(testTrip.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTrip.getShipmentNumber()).isEqualTo(DEFAULT_SHIPMENT_NUMBER);
        assertThat(testTrip.getBol()).isEqualTo(DEFAULT_BOL);
        assertThat(testTrip.getPickup()).isEqualTo(DEFAULT_PICKUP);
        assertThat(testTrip.getDrop()).isEqualTo(DEFAULT_DROP);
        assertThat(testTrip.getPickupLocation()).isEqualTo(DEFAULT_PICKUP_LOCATION);
        assertThat(testTrip.getDropLocation()).isEqualTo(DEFAULT_DROP_LOCATION);
        assertThat(testTrip.getCurrentLocation()).isEqualTo(DEFAULT_CURRENT_LOCATION);
        assertThat(testTrip.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTrip.getDetention()).isEqualTo(DEFAULT_DETENTION);
        assertThat(testTrip.getChasisInTime()).isEqualTo(DEFAULT_CHASIS_IN_TIME);
        assertThat(testTrip.getPod()).isEqualTo(DEFAULT_POD);
        assertThat(testTrip.getPodContentType()).isEqualTo(DEFAULT_POD_CONTENT_TYPE);
        assertThat(testTrip.getHazmat()).isEqualTo(DEFAULT_HAZMAT);
        assertThat(testTrip.getRecievedBy()).isEqualTo(DEFAULT_RECIEVED_BY);
        assertThat(testTrip.getCoveredBy()).isEqualTo(DEFAULT_COVERED_BY);
        assertThat(testTrip.getLoadType()).isEqualTo(DEFAULT_LOAD_TYPE);
        assertThat(testTrip.getContainerSize()).isEqualTo(DEFAULT_CONTAINER_SIZE);
        assertThat(testTrip.getNumbersOfContainer()).isEqualTo(DEFAULT_NUMBERS_OF_CONTAINER);
        assertThat(testTrip.getComments()).isEqualTo(DEFAULT_COMMENTS);

        // Validate the Trip in Elasticsearch
        verify(mockTripSearchRepository, times(1)).save(testTrip);
    }

    @Test
    @Transactional
    public void createTripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip with an existing ID
        trip.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate);

        // Validate the Trip in Elasticsearch
        verify(mockTripSearchRepository, times(0)).save(trip);
    }


    @Test
    @Transactional
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
            .andExpect(jsonPath("$.[*].tripNumber").value(hasItem(DEFAULT_TRIP_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shipmentNumber").value(hasItem(DEFAULT_SHIPMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].bol").value(hasItem(DEFAULT_BOL)))
            .andExpect(jsonPath("$.[*].pickup").value(hasItem(DEFAULT_PICKUP.toString())))
            .andExpect(jsonPath("$.[*].drop").value(hasItem(DEFAULT_DROP.toString())))
            .andExpect(jsonPath("$.[*].pickupLocation").value(hasItem(DEFAULT_PICKUP_LOCATION)))
            .andExpect(jsonPath("$.[*].dropLocation").value(hasItem(DEFAULT_DROP_LOCATION)))
            .andExpect(jsonPath("$.[*].currentLocation").value(hasItem(DEFAULT_CURRENT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].detention").value(hasItem(DEFAULT_DETENTION.intValue())))
            .andExpect(jsonPath("$.[*].chasisInTime").value(hasItem(DEFAULT_CHASIS_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].podContentType").value(hasItem(DEFAULT_POD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pod").value(hasItem(Base64Utils.encodeToString(DEFAULT_POD))))
            .andExpect(jsonPath("$.[*].hazmat").value(hasItem(DEFAULT_HAZMAT.toString())))
            .andExpect(jsonPath("$.[*].recievedBy").value(hasItem(DEFAULT_RECIEVED_BY)))
            .andExpect(jsonPath("$.[*].coveredBy").value(hasItem(DEFAULT_COVERED_BY.toString())))
            .andExpect(jsonPath("$.[*].loadType").value(hasItem(DEFAULT_LOAD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].containerSize").value(hasItem(DEFAULT_CONTAINER_SIZE.toString())))
            .andExpect(jsonPath("$.[*].numbersOfContainer").value(hasItem(DEFAULT_NUMBERS_OF_CONTAINER)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trip.getId().intValue()))
            .andExpect(jsonPath("$.tripNumber").value(DEFAULT_TRIP_NUMBER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.shipmentNumber").value(DEFAULT_SHIPMENT_NUMBER))
            .andExpect(jsonPath("$.bol").value(DEFAULT_BOL))
            .andExpect(jsonPath("$.pickup").value(DEFAULT_PICKUP.toString()))
            .andExpect(jsonPath("$.drop").value(DEFAULT_DROP.toString()))
            .andExpect(jsonPath("$.pickupLocation").value(DEFAULT_PICKUP_LOCATION))
            .andExpect(jsonPath("$.dropLocation").value(DEFAULT_DROP_LOCATION))
            .andExpect(jsonPath("$.currentLocation").value(DEFAULT_CURRENT_LOCATION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.detention").value(DEFAULT_DETENTION.intValue()))
            .andExpect(jsonPath("$.chasisInTime").value(DEFAULT_CHASIS_IN_TIME.toString()))
            .andExpect(jsonPath("$.podContentType").value(DEFAULT_POD_CONTENT_TYPE))
            .andExpect(jsonPath("$.pod").value(Base64Utils.encodeToString(DEFAULT_POD)))
            .andExpect(jsonPath("$.hazmat").value(DEFAULT_HAZMAT.toString()))
            .andExpect(jsonPath("$.recievedBy").value(DEFAULT_RECIEVED_BY))
            .andExpect(jsonPath("$.coveredBy").value(DEFAULT_COVERED_BY.toString()))
            .andExpect(jsonPath("$.loadType").value(DEFAULT_LOAD_TYPE.toString()))
            .andExpect(jsonPath("$.containerSize").value(DEFAULT_CONTAINER_SIZE.toString()))
            .andExpect(jsonPath("$.numbersOfContainer").value(DEFAULT_NUMBERS_OF_CONTAINER))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrip() throws Exception {
        // Initialize the database
        tripService.save(trip);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockTripSearchRepository);

        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        Trip updatedTrip = tripRepository.findById(trip.getId()).get();
        // Disconnect from session so that the updates on updatedTrip are not directly saved in db
        em.detach(updatedTrip);
        updatedTrip
            .tripNumber(UPDATED_TRIP_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .shipmentNumber(UPDATED_SHIPMENT_NUMBER)
            .bol(UPDATED_BOL)
            .pickup(UPDATED_PICKUP)
            .drop(UPDATED_DROP)
            .pickupLocation(UPDATED_PICKUP_LOCATION)
            .dropLocation(UPDATED_DROP_LOCATION)
            .currentLocation(UPDATED_CURRENT_LOCATION)
            .status(UPDATED_STATUS)
            .detention(UPDATED_DETENTION)
            .chasisInTime(UPDATED_CHASIS_IN_TIME)
            .pod(UPDATED_POD)
            .podContentType(UPDATED_POD_CONTENT_TYPE)
            .hazmat(UPDATED_HAZMAT)
            .recievedBy(UPDATED_RECIEVED_BY)
            .coveredBy(UPDATED_COVERED_BY)
            .loadType(UPDATED_LOAD_TYPE)
            .containerSize(UPDATED_CONTAINER_SIZE)
            .numbersOfContainer(UPDATED_NUMBERS_OF_CONTAINER)
            .comments(UPDATED_COMMENTS);

        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrip)))
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getTripNumber()).isEqualTo(UPDATED_TRIP_NUMBER);
        assertThat(testTrip.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTrip.getShipmentNumber()).isEqualTo(UPDATED_SHIPMENT_NUMBER);
        assertThat(testTrip.getBol()).isEqualTo(UPDATED_BOL);
        assertThat(testTrip.getPickup()).isEqualTo(UPDATED_PICKUP);
        assertThat(testTrip.getDrop()).isEqualTo(UPDATED_DROP);
        assertThat(testTrip.getPickupLocation()).isEqualTo(UPDATED_PICKUP_LOCATION);
        assertThat(testTrip.getDropLocation()).isEqualTo(UPDATED_DROP_LOCATION);
        assertThat(testTrip.getCurrentLocation()).isEqualTo(UPDATED_CURRENT_LOCATION);
        assertThat(testTrip.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTrip.getDetention()).isEqualTo(UPDATED_DETENTION);
        assertThat(testTrip.getChasisInTime()).isEqualTo(UPDATED_CHASIS_IN_TIME);
        assertThat(testTrip.getPod()).isEqualTo(UPDATED_POD);
        assertThat(testTrip.getPodContentType()).isEqualTo(UPDATED_POD_CONTENT_TYPE);
        assertThat(testTrip.getHazmat()).isEqualTo(UPDATED_HAZMAT);
        assertThat(testTrip.getRecievedBy()).isEqualTo(UPDATED_RECIEVED_BY);
        assertThat(testTrip.getCoveredBy()).isEqualTo(UPDATED_COVERED_BY);
        assertThat(testTrip.getLoadType()).isEqualTo(UPDATED_LOAD_TYPE);
        assertThat(testTrip.getContainerSize()).isEqualTo(UPDATED_CONTAINER_SIZE);
        assertThat(testTrip.getNumbersOfContainer()).isEqualTo(UPDATED_NUMBERS_OF_CONTAINER);
        assertThat(testTrip.getComments()).isEqualTo(UPDATED_COMMENTS);

        // Validate the Trip in Elasticsearch
        verify(mockTripSearchRepository, times(1)).save(testTrip);
    }

    @Test
    @Transactional
    public void updateNonExistingTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Create the Trip

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trip)))
            .andExpect(status().isBadRequest());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Trip in Elasticsearch
        verify(mockTripSearchRepository, times(0)).save(trip);
    }

    @Test
    @Transactional
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripService.save(trip);

        int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Delete the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Trip in Elasticsearch
        verify(mockTripSearchRepository, times(1)).deleteById(trip.getId());
    }

    @Test
    @Transactional
    public void searchTrip() throws Exception {
        // Initialize the database
        tripService.save(trip);
        when(mockTripSearchRepository.search(queryStringQuery("id:" + trip.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(trip), PageRequest.of(0, 1), 1));
        // Search the trip
        restTripMockMvc.perform(get("/api/_search/trips?query=id:" + trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
            .andExpect(jsonPath("$.[*].tripNumber").value(hasItem(DEFAULT_TRIP_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shipmentNumber").value(hasItem(DEFAULT_SHIPMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].bol").value(hasItem(DEFAULT_BOL)))
            .andExpect(jsonPath("$.[*].pickup").value(hasItem(DEFAULT_PICKUP.toString())))
            .andExpect(jsonPath("$.[*].drop").value(hasItem(DEFAULT_DROP.toString())))
            .andExpect(jsonPath("$.[*].pickupLocation").value(hasItem(DEFAULT_PICKUP_LOCATION)))
            .andExpect(jsonPath("$.[*].dropLocation").value(hasItem(DEFAULT_DROP_LOCATION)))
            .andExpect(jsonPath("$.[*].currentLocation").value(hasItem(DEFAULT_CURRENT_LOCATION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].detention").value(hasItem(DEFAULT_DETENTION.intValue())))
            .andExpect(jsonPath("$.[*].chasisInTime").value(hasItem(DEFAULT_CHASIS_IN_TIME.toString())))
            .andExpect(jsonPath("$.[*].podContentType").value(hasItem(DEFAULT_POD_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].pod").value(hasItem(Base64Utils.encodeToString(DEFAULT_POD))))
            .andExpect(jsonPath("$.[*].hazmat").value(hasItem(DEFAULT_HAZMAT.toString())))
            .andExpect(jsonPath("$.[*].recievedBy").value(hasItem(DEFAULT_RECIEVED_BY)))
            .andExpect(jsonPath("$.[*].coveredBy").value(hasItem(DEFAULT_COVERED_BY.toString())))
            .andExpect(jsonPath("$.[*].loadType").value(hasItem(DEFAULT_LOAD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].containerSize").value(hasItem(DEFAULT_CONTAINER_SIZE.toString())))
            .andExpect(jsonPath("$.[*].numbersOfContainer").value(hasItem(DEFAULT_NUMBERS_OF_CONTAINER)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
}
