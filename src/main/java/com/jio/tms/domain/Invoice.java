package com.jio.tms.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.jio.tms.domain.enumeration.TaxType;

import com.jio.tms.domain.enumeration.CURRENCY;

import com.jio.tms.domain.enumeration.InvoiceStatus;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "invoice")
public class Invoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "tax_rate")
    private Double taxRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type")
    private TaxType taxType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CURRENCY currency;

    @Column(name = "invoice_tax_total")
    private Double invoiceTaxTotal;

    @Column(name = "invoice_sub_total")
    private Double invoiceSubTotal;

    @Column(name = "invoice_total")
    private Double invoiceTotal;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "invoice_paid_date")
    private LocalDate invoicePaidDate;

    @Column(name = "pay_ref_no")
    private String payRefNo;

    @Column(name = "invoice_due_date")
    private LocalDate invoiceDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InvoiceStatus status;

    @Lob
    @Column(name = "invoice_pdf")
    private byte[] invoicePdf;

    @Column(name = "invoice_pdf_content_type")
    private String invoicePdfContentType;

    @Column(name = "remarks")
    private String remarks;

    @OneToMany(mappedBy = "invoice")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("invoices")
    private Trip trip;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Invoice orderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getTaxRate() {
        return taxRate;
    }

    public Invoice taxRate(Double taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    public TaxType getTaxType() {
        return taxType;
    }

    public Invoice taxType(TaxType taxType) {
        this.taxType = taxType;
        return this;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    public CURRENCY getCurrency() {
        return currency;
    }

    public Invoice currency(CURRENCY currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(CURRENCY currency) {
        this.currency = currency;
    }

    public Double getInvoiceTaxTotal() {
        return invoiceTaxTotal;
    }

    public Invoice invoiceTaxTotal(Double invoiceTaxTotal) {
        this.invoiceTaxTotal = invoiceTaxTotal;
        return this;
    }

    public void setInvoiceTaxTotal(Double invoiceTaxTotal) {
        this.invoiceTaxTotal = invoiceTaxTotal;
    }

    public Double getInvoiceSubTotal() {
        return invoiceSubTotal;
    }

    public Invoice invoiceSubTotal(Double invoiceSubTotal) {
        this.invoiceSubTotal = invoiceSubTotal;
        return this;
    }

    public void setInvoiceSubTotal(Double invoiceSubTotal) {
        this.invoiceSubTotal = invoiceSubTotal;
    }

    public Double getInvoiceTotal() {
        return invoiceTotal;
    }

    public Invoice invoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
        return this;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public Invoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getInvoicePaidDate() {
        return invoicePaidDate;
    }

    public Invoice invoicePaidDate(LocalDate invoicePaidDate) {
        this.invoicePaidDate = invoicePaidDate;
        return this;
    }

    public void setInvoicePaidDate(LocalDate invoicePaidDate) {
        this.invoicePaidDate = invoicePaidDate;
    }

    public String getPayRefNo() {
        return payRefNo;
    }

    public Invoice payRefNo(String payRefNo) {
        this.payRefNo = payRefNo;
        return this;
    }

    public void setPayRefNo(String payRefNo) {
        this.payRefNo = payRefNo;
    }

    public LocalDate getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public Invoice invoiceDueDate(LocalDate invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
        return this;
    }

    public void setInvoiceDueDate(LocalDate invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public Invoice status(InvoiceStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(InvoiceStatus status) {
        this.status = status;
    }

    public byte[] getInvoicePdf() {
        return invoicePdf;
    }

    public Invoice invoicePdf(byte[] invoicePdf) {
        this.invoicePdf = invoicePdf;
        return this;
    }

    public void setInvoicePdf(byte[] invoicePdf) {
        this.invoicePdf = invoicePdf;
    }

    public String getInvoicePdfContentType() {
        return invoicePdfContentType;
    }

    public Invoice invoicePdfContentType(String invoicePdfContentType) {
        this.invoicePdfContentType = invoicePdfContentType;
        return this;
    }

    public void setInvoicePdfContentType(String invoicePdfContentType) {
        this.invoicePdfContentType = invoicePdfContentType;
    }

    public String getRemarks() {
        return remarks;
    }

    public Invoice remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Set<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public Invoice invoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
        return this;
    }

    public Invoice addInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.add(invoiceItem);
        invoiceItem.setInvoice(this);
        return this;
    }

    public Invoice removeInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItems.remove(invoiceItem);
        invoiceItem.setInvoice(null);
        return this;
    }

    public void setInvoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Invoice customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Trip getTrip() {
        return trip;
    }

    public Invoice trip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Invoice)) {
            return false;
        }
        return id != null && id.equals(((Invoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Invoice{" +
            "id=" + getId() +
            ", orderNo='" + getOrderNo() + "'" +
            ", taxRate=" + getTaxRate() +
            ", taxType='" + getTaxType() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", invoiceTaxTotal=" + getInvoiceTaxTotal() +
            ", invoiceSubTotal=" + getInvoiceSubTotal() +
            ", invoiceTotal=" + getInvoiceTotal() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", invoicePaidDate='" + getInvoicePaidDate() + "'" +
            ", payRefNo='" + getPayRefNo() + "'" +
            ", invoiceDueDate='" + getInvoiceDueDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", invoicePdf='" + getInvoicePdf() + "'" +
            ", invoicePdfContentType='" + getInvoicePdfContentType() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
