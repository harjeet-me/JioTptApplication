package com.jio.tms.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.jio.tms.domain.enumeration.Designation;

import com.jio.tms.domain.enumeration.CountryEnum;

import com.jio.tms.domain.enumeration.ToggleStatus;

import com.jio.tms.domain.enumeration.CURRENCY;

/**
 * A OwnerOperator.
 */
@Entity
@Table(name = "owner_operator")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "owneroperator")
public class OwnerOperator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_designation")
    private Designation contactDesignation;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Enumerated(EnumType.STRING)
    @Column(name = "country")
    private CountryEnum country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "dot")
    private String dot;

    @Column(name = "mc")
    private Long mc;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_status")
    private ToggleStatus profileStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "preffred_currency")
    private CURRENCY preffredCurrency;

    @Lob
    @Column(name = "contract_doc")
    private byte[] contractDoc;

    @Column(name = "contract_doc_content_type")
    private String contractDocContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Insurance operInsurance;

    @OneToMany(mappedBy = "ownerOperator")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trip> loadOrders = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public OwnerOperator company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public OwnerOperator firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public OwnerOperator lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Designation getContactDesignation() {
        return contactDesignation;
    }

    public OwnerOperator contactDesignation(Designation contactDesignation) {
        this.contactDesignation = contactDesignation;
        return this;
    }

    public void setContactDesignation(Designation contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

    public String getEmail() {
        return email;
    }

    public OwnerOperator email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public OwnerOperator phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public OwnerOperator address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public OwnerOperator streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public OwnerOperator city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public OwnerOperator stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public OwnerOperator country(CountryEnum country) {
        this.country = country;
        return this;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public OwnerOperator postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDot() {
        return dot;
    }

    public OwnerOperator dot(String dot) {
        this.dot = dot;
        return this;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public Long getMc() {
        return mc;
    }

    public OwnerOperator mc(Long mc) {
        this.mc = mc;
        return this;
    }

    public void setMc(Long mc) {
        this.mc = mc;
    }

    public ToggleStatus getProfileStatus() {
        return profileStatus;
    }

    public OwnerOperator profileStatus(ToggleStatus profileStatus) {
        this.profileStatus = profileStatus;
        return this;
    }

    public void setProfileStatus(ToggleStatus profileStatus) {
        this.profileStatus = profileStatus;
    }

    public CURRENCY getPreffredCurrency() {
        return preffredCurrency;
    }

    public OwnerOperator preffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
        return this;
    }

    public void setPreffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
    }

    public byte[] getContractDoc() {
        return contractDoc;
    }

    public OwnerOperator contractDoc(byte[] contractDoc) {
        this.contractDoc = contractDoc;
        return this;
    }

    public void setContractDoc(byte[] contractDoc) {
        this.contractDoc = contractDoc;
    }

    public String getContractDocContentType() {
        return contractDocContentType;
    }

    public OwnerOperator contractDocContentType(String contractDocContentType) {
        this.contractDocContentType = contractDocContentType;
        return this;
    }

    public void setContractDocContentType(String contractDocContentType) {
        this.contractDocContentType = contractDocContentType;
    }

    public Insurance getOperInsurance() {
        return operInsurance;
    }

    public OwnerOperator operInsurance(Insurance insurance) {
        this.operInsurance = insurance;
        return this;
    }

    public void setOperInsurance(Insurance insurance) {
        this.operInsurance = insurance;
    }

    public Set<Trip> getLoadOrders() {
        return loadOrders;
    }

    public OwnerOperator loadOrders(Set<Trip> trips) {
        this.loadOrders = trips;
        return this;
    }

    public OwnerOperator addLoadOrder(Trip trip) {
        this.loadOrders.add(trip);
        trip.setOwnerOperator(this);
        return this;
    }

    public OwnerOperator removeLoadOrder(Trip trip) {
        this.loadOrders.remove(trip);
        trip.setOwnerOperator(null);
        return this;
    }

    public void setLoadOrders(Set<Trip> trips) {
        this.loadOrders = trips;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OwnerOperator)) {
            return false;
        }
        return id != null && id.equals(((OwnerOperator) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OwnerOperator{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", contactDesignation='" + getContactDesignation() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", address='" + getAddress() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", dot='" + getDot() + "'" +
            ", mc=" + getMc() +
            ", profileStatus='" + getProfileStatus() + "'" +
            ", preffredCurrency='" + getPreffredCurrency() + "'" +
            ", contractDoc='" + getContractDoc() + "'" +
            ", contractDocContentType='" + getContractDocContentType() + "'" +
            "}";
    }
}
