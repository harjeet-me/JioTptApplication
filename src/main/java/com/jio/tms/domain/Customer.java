package com.jio.tms.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import com.jio.tms.domain.enumeration.Designation;

import com.jio.tms.domain.enumeration.PreffredContactType;

import com.jio.tms.domain.enumeration.CountryEnum;

import com.jio.tms.domain.enumeration.ToggleStatus;

import com.jio.tms.domain.enumeration.CURRENCY;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customer")
public class Customer implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "preffred_contact_type")
    private PreffredContactType preffredContactType;

    @Column(name = "website")
    private String website;

    @Column(name = "secondary_contact_person")
    private String secondaryContactPerson;

    @Column(name = "sec_contact_number")
    private Long secContactNumber;

    @Column(name = "sec_contact_email")
    private String secContactEmail;

    @Column(name = "sec_contact_pre_contact_time")
    private String secContactPreContactTime;

    @Column(name = "fax")
    private Long fax;

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

    @Lob
    @Column(name = "company_logo")
    private byte[] companyLogo;

    @Column(name = "company_logo_content_type")
    private String companyLogoContentType;

    @Column(name = "customer_since")
    private LocalDate customerSince;

    @Column(name = "remarks")
    private String remarks;

    @Lob
    @Column(name = "contract")
    private byte[] contract;

    @Column(name = "contract_content_type")
    private String contractContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ToggleStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "preffred_currency")
    private CURRENCY preffredCurrency;

    @Column(name = "payterms")
    private String payterms;

    @Column(name = "time_zone")
    private ZonedDateTime timeZone;

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Trip> loadOrders = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contact> morecontacts = new HashSet<>();

    @OneToMany(mappedBy = "customer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TransactionsRecord> transactionsRecords = new HashSet<>();

    @OneToOne(mappedBy = "customer")
    @JsonIgnore
    private Accounts accounts;

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

    public Customer company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public Customer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Customer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Designation getContactDesignation() {
        return contactDesignation;
    }

    public Customer contactDesignation(Designation contactDesignation) {
        this.contactDesignation = contactDesignation;
        return this;
    }

    public void setContactDesignation(Designation contactDesignation) {
        this.contactDesignation = contactDesignation;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Customer phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PreffredContactType getPreffredContactType() {
        return preffredContactType;
    }

    public Customer preffredContactType(PreffredContactType preffredContactType) {
        this.preffredContactType = preffredContactType;
        return this;
    }

    public void setPreffredContactType(PreffredContactType preffredContactType) {
        this.preffredContactType = preffredContactType;
    }

    public String getWebsite() {
        return website;
    }

    public Customer website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSecondaryContactPerson() {
        return secondaryContactPerson;
    }

    public Customer secondaryContactPerson(String secondaryContactPerson) {
        this.secondaryContactPerson = secondaryContactPerson;
        return this;
    }

    public void setSecondaryContactPerson(String secondaryContactPerson) {
        this.secondaryContactPerson = secondaryContactPerson;
    }

    public Long getSecContactNumber() {
        return secContactNumber;
    }

    public Customer secContactNumber(Long secContactNumber) {
        this.secContactNumber = secContactNumber;
        return this;
    }

    public void setSecContactNumber(Long secContactNumber) {
        this.secContactNumber = secContactNumber;
    }

    public String getSecContactEmail() {
        return secContactEmail;
    }

    public Customer secContactEmail(String secContactEmail) {
        this.secContactEmail = secContactEmail;
        return this;
    }

    public void setSecContactEmail(String secContactEmail) {
        this.secContactEmail = secContactEmail;
    }

    public String getSecContactPreContactTime() {
        return secContactPreContactTime;
    }

    public Customer secContactPreContactTime(String secContactPreContactTime) {
        this.secContactPreContactTime = secContactPreContactTime;
        return this;
    }

    public void setSecContactPreContactTime(String secContactPreContactTime) {
        this.secContactPreContactTime = secContactPreContactTime;
    }

    public Long getFax() {
        return fax;
    }

    public Customer fax(Long fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(Long fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Customer streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public Customer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public Customer stateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
        return this;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public CountryEnum getCountry() {
        return country;
    }

    public Customer country(CountryEnum country) {
        this.country = country;
        return this;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Customer postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDot() {
        return dot;
    }

    public Customer dot(String dot) {
        this.dot = dot;
        return this;
    }

    public void setDot(String dot) {
        this.dot = dot;
    }

    public Long getMc() {
        return mc;
    }

    public Customer mc(Long mc) {
        this.mc = mc;
        return this;
    }

    public void setMc(Long mc) {
        this.mc = mc;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public Customer companyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
        return this;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogoContentType() {
        return companyLogoContentType;
    }

    public Customer companyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
        return this;
    }

    public void setCompanyLogoContentType(String companyLogoContentType) {
        this.companyLogoContentType = companyLogoContentType;
    }

    public LocalDate getCustomerSince() {
        return customerSince;
    }

    public Customer customerSince(LocalDate customerSince) {
        this.customerSince = customerSince;
        return this;
    }

    public void setCustomerSince(LocalDate customerSince) {
        this.customerSince = customerSince;
    }

    public String getRemarks() {
        return remarks;
    }

    public Customer remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public byte[] getContract() {
        return contract;
    }

    public Customer contract(byte[] contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(byte[] contract) {
        this.contract = contract;
    }

    public String getContractContentType() {
        return contractContentType;
    }

    public Customer contractContentType(String contractContentType) {
        this.contractContentType = contractContentType;
        return this;
    }

    public void setContractContentType(String contractContentType) {
        this.contractContentType = contractContentType;
    }

    public ToggleStatus getStatus() {
        return status;
    }

    public Customer status(ToggleStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ToggleStatus status) {
        this.status = status;
    }

    public CURRENCY getPreffredCurrency() {
        return preffredCurrency;
    }

    public Customer preffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
        return this;
    }

    public void setPreffredCurrency(CURRENCY preffredCurrency) {
        this.preffredCurrency = preffredCurrency;
    }

    public String getPayterms() {
        return payterms;
    }

    public Customer payterms(String payterms) {
        this.payterms = payterms;
        return this;
    }

    public void setPayterms(String payterms) {
        this.payterms = payterms;
    }

    public ZonedDateTime getTimeZone() {
        return timeZone;
    }

    public Customer timeZone(ZonedDateTime timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public void setTimeZone(ZonedDateTime timeZone) {
        this.timeZone = timeZone;
    }

    public Set<Trip> getLoadOrders() {
        return loadOrders;
    }

    public Customer loadOrders(Set<Trip> trips) {
        this.loadOrders = trips;
        return this;
    }

    public Customer addLoadOrder(Trip trip) {
        this.loadOrders.add(trip);
        trip.setCustomer(this);
        return this;
    }

    public Customer removeLoadOrder(Trip trip) {
        this.loadOrders.remove(trip);
        trip.setCustomer(null);
        return this;
    }

    public void setLoadOrders(Set<Trip> trips) {
        this.loadOrders = trips;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public Customer invoices(Set<Invoice> invoices) {
        this.invoices = invoices;
        return this;
    }

    public Customer addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setCustomer(this);
        return this;
    }

    public Customer removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setCustomer(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<Contact> getMorecontacts() {
        return morecontacts;
    }

    public Customer morecontacts(Set<Contact> contacts) {
        this.morecontacts = contacts;
        return this;
    }

    public Customer addMorecontact(Contact contact) {
        this.morecontacts.add(contact);
        contact.setCustomer(this);
        return this;
    }

    public Customer removeMorecontact(Contact contact) {
        this.morecontacts.remove(contact);
        contact.setCustomer(null);
        return this;
    }

    public void setMorecontacts(Set<Contact> contacts) {
        this.morecontacts = contacts;
    }

    public Set<TransactionsRecord> getTransactionsRecords() {
        return transactionsRecords;
    }

    public Customer transactionsRecords(Set<TransactionsRecord> transactionsRecords) {
        this.transactionsRecords = transactionsRecords;
        return this;
    }

    public Customer addTransactionsRecord(TransactionsRecord transactionsRecord) {
        this.transactionsRecords.add(transactionsRecord);
        transactionsRecord.setCustomer(this);
        return this;
    }

    public Customer removeTransactionsRecord(TransactionsRecord transactionsRecord) {
        this.transactionsRecords.remove(transactionsRecord);
        transactionsRecord.setCustomer(null);
        return this;
    }

    public void setTransactionsRecords(Set<TransactionsRecord> transactionsRecords) {
        this.transactionsRecords = transactionsRecords;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public Customer accounts(Accounts accounts) {
        this.accounts = accounts;
        return this;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", contactDesignation='" + getContactDesignation() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", preffredContactType='" + getPreffredContactType() + "'" +
            ", website='" + getWebsite() + "'" +
            ", secondaryContactPerson='" + getSecondaryContactPerson() + "'" +
            ", secContactNumber=" + getSecContactNumber() +
            ", secContactEmail='" + getSecContactEmail() + "'" +
            ", secContactPreContactTime='" + getSecContactPreContactTime() + "'" +
            ", fax=" + getFax() +
            ", address='" + getAddress() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", stateProvince='" + getStateProvince() + "'" +
            ", country='" + getCountry() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", dot='" + getDot() + "'" +
            ", mc=" + getMc() +
            ", companyLogo='" + getCompanyLogo() + "'" +
            ", companyLogoContentType='" + getCompanyLogoContentType() + "'" +
            ", customerSince='" + getCustomerSince() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", contract='" + getContract() + "'" +
            ", contractContentType='" + getContractContentType() + "'" +
            ", status='" + getStatus() + "'" +
            ", preffredCurrency='" + getPreffredCurrency() + "'" +
            ", payterms='" + getPayterms() + "'" +
            ", timeZone='" + getTimeZone() + "'" +
            "}";
    }
}
