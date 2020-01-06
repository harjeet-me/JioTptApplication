package com.jio.tms.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.jio.tms.domain.enumeration.StatusEnum;

import com.jio.tms.domain.enumeration.HAZMAT;

import com.jio.tms.domain.enumeration.COVEREDBY;

import com.jio.tms.domain.enumeration.LoadType;

import com.jio.tms.domain.enumeration.SizeEnum;

/**
 * A Trip.
 */
@Entity
@Table(name = "trip")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "trip")
public class Trip implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "trip_number")
    private String tripNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "shipment_number")
    private String shipmentNumber;

    @Column(name = "bol")
    private String bol;

    @Column(name = "pickup")
    private LocalDate pickup;

    @Column(name = "jhi_drop")
    private LocalDate drop;

    @Column(name = "pickup_location")
    private String pickupLocation;

    @Column(name = "drop_location")
    private String dropLocation;

    @Column(name = "current_location")
    private String currentLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @Column(name = "detention")
    private Long detention;

    @Column(name = "chasis_in_time")
    private Instant chasisInTime;

    @Lob
    @Column(name = "pod")
    private byte[] pod;

    @Column(name = "pod_content_type")
    private String podContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "hazmat")
    private HAZMAT hazmat;

    @Column(name = "recieved_by")
    private String recievedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "covered_by")
    private COVEREDBY coveredBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "load_type")
    private LoadType loadType;

    @Enumerated(EnumType.STRING)
    @Column(name = "container_size")
    private SizeEnum containerSize;

    @Column(name = "numbers_of_container")
    private Integer numbersOfContainer;

    @Column(name = "comments")
    private String comments;

    @OneToOne
    @JoinColumn(unique = true)
    private Location pickupLocation;

    @OneToOne
    @JoinColumn(unique = true)
    private Location dropLocation;

    @OneToMany(mappedBy = "trip")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("loadOrders")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("trips")
    private Driver driver;

    @ManyToOne
    @JsonIgnoreProperties("trips")
    private Equipment equipment;

    @ManyToOne
    @JsonIgnoreProperties("loadOrders")
    private OwnerOperator ownerOperator;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTripNumber() {
        return tripNumber;
    }

    public Trip tripNumber(String tripNumber) {
        this.tripNumber = tripNumber;
        return this;
    }

    public void setTripNumber(String tripNumber) {
        this.tripNumber = tripNumber;
    }

    public String getDescription() {
        return description;
    }

    public Trip description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShipmentNumber() {
        return shipmentNumber;
    }

    public Trip shipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
        return this;
    }

    public void setShipmentNumber(String shipmentNumber) {
        this.shipmentNumber = shipmentNumber;
    }

    public String getBol() {
        return bol;
    }

    public Trip bol(String bol) {
        this.bol = bol;
        return this;
    }

    public void setBol(String bol) {
        this.bol = bol;
    }

    public LocalDate getPickup() {
        return pickup;
    }

    public Trip pickup(LocalDate pickup) {
        this.pickup = pickup;
        return this;
    }

    public void setPickup(LocalDate pickup) {
        this.pickup = pickup;
    }

    public LocalDate getDrop() {
        return drop;
    }

    public Trip drop(LocalDate drop) {
        this.drop = drop;
        return this;
    }

    public void setDrop(LocalDate drop) {
        this.drop = drop;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public Trip pickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
        return this;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public Trip dropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
        return this;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public Trip currentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
        return this;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public Trip status(StatusEnum status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getDetention() {
        return detention;
    }

    public Trip detention(Long detention) {
        this.detention = detention;
        return this;
    }

    public void setDetention(Long detention) {
        this.detention = detention;
    }

    public Instant getChasisInTime() {
        return chasisInTime;
    }

    public Trip chasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
        return this;
    }

    public void setChasisInTime(Instant chasisInTime) {
        this.chasisInTime = chasisInTime;
    }

    public byte[] getPod() {
        return pod;
    }

    public Trip pod(byte[] pod) {
        this.pod = pod;
        return this;
    }

    public void setPod(byte[] pod) {
        this.pod = pod;
    }

    public String getPodContentType() {
        return podContentType;
    }

    public Trip podContentType(String podContentType) {
        this.podContentType = podContentType;
        return this;
    }

    public void setPodContentType(String podContentType) {
        this.podContentType = podContentType;
    }

    public HAZMAT getHazmat() {
        return hazmat;
    }

    public Trip hazmat(HAZMAT hazmat) {
        this.hazmat = hazmat;
        return this;
    }

    public void setHazmat(HAZMAT hazmat) {
        this.hazmat = hazmat;
    }

    public String getRecievedBy() {
        return recievedBy;
    }

    public Trip recievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
        return this;
    }

    public void setRecievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
    }

    public COVEREDBY getCoveredBy() {
        return coveredBy;
    }

    public Trip coveredBy(COVEREDBY coveredBy) {
        this.coveredBy = coveredBy;
        return this;
    }

    public void setCoveredBy(COVEREDBY coveredBy) {
        this.coveredBy = coveredBy;
    }

    public LoadType getLoadType() {
        return loadType;
    }

    public Trip loadType(LoadType loadType) {
        this.loadType = loadType;
        return this;
    }

    public void setLoadType(LoadType loadType) {
        this.loadType = loadType;
    }

    public SizeEnum getContainerSize() {
        return containerSize;
    }

    public Trip containerSize(SizeEnum containerSize) {
        this.containerSize = containerSize;
        return this;
    }

    public void setContainerSize(SizeEnum containerSize) {
        this.containerSize = containerSize;
    }

    public Integer getNumbersOfContainer() {
        return numbersOfContainer;
    }

    public Trip numbersOfContainer(Integer numbersOfContainer) {
        this.numbersOfContainer = numbersOfContainer;
        return this;
    }

    public void setNumbersOfContainer(Integer numbersOfContainer) {
        this.numbersOfContainer = numbersOfContainer;
    }

    public String getComments() {
        return comments;
    }

    public Trip comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Trip pickupLocation(Location location) {
        this.pickupLocation = location;
        return this;
    }

    public void setPickupLocation(Location location) {
        this.pickupLocation = location;
    }

    public Location getDropLocation() {
        return dropLocation;
    }

    public Trip dropLocation(Location location) {
        this.dropLocation = location;
        return this;
    }

    public void setDropLocation(Location location) {
        this.dropLocation = location;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Trip invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Trip addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setTrip(this);
        return this;
    }

    public Trip removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setTrip(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Trip customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public Trip driver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Trip equipment(Equipment equipment) {
        this.equipment = equipment;
        return this;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public OwnerOperator getOwnerOperator() {
        return ownerOperator;
    }

    public Trip ownerOperator(OwnerOperator ownerOperator) {
        this.ownerOperator = ownerOperator;
        return this;
    }

    public void setOwnerOperator(OwnerOperator ownerOperator) {
        this.ownerOperator = ownerOperator;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trip)) {
            return false;
        }
        return id != null && id.equals(((Trip) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Trip{" +
            "id=" + getId() +
            ", tripNumber='" + getTripNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", shipmentNumber='" + getShipmentNumber() + "'" +
            ", bol='" + getBol() + "'" +
            ", pickup='" + getPickup() + "'" +
            ", drop='" + getDrop() + "'" +
            ", pickupLocation='" + getPickupLocation() + "'" +
            ", dropLocation='" + getDropLocation() + "'" +
            ", currentLocation='" + getCurrentLocation() + "'" +
            ", status='" + getStatus() + "'" +
            ", detention=" + getDetention() +
            ", chasisInTime='" + getChasisInTime() + "'" +
            ", pod='" + getPod() + "'" +
            ", podContentType='" + getPodContentType() + "'" +
            ", hazmat='" + getHazmat() + "'" +
            ", recievedBy='" + getRecievedBy() + "'" +
            ", coveredBy='" + getCoveredBy() + "'" +
            ", loadType='" + getLoadType() + "'" +
            ", containerSize='" + getContainerSize() + "'" +
            ", numbersOfContainer=" + getNumbersOfContainer() +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
