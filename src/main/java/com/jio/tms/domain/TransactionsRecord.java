package com.jio.tms.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.jio.tms.domain.enumeration.TransactionType;

import com.jio.tms.domain.enumeration.TxStatus;

import com.jio.tms.domain.enumeration.CURRENCY;

/**
 * A TransactionsRecord.
 */
@Entity
@Table(name = "transactions_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "transactionsrecord")
public class TransactionsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tx_type")
    private TransactionType txType;

    @Column(name = "description")
    private String description;

    @Column(name = "tx_ammount")
    private Double txAmmount;

    @Column(name = "tx_ref_no")
    private String txRefNo;

    @Column(name = "tx_created_by")
    private String txCreatedBy;

    @Column(name = "tx_created_date")
    private LocalDate txCreatedDate;

    @Column(name = "tx_completed_by")
    private String txCompletedBy;

    @Column(name = "tx_completed_date")
    private LocalDate txCompletedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TxStatus status;

    @Lob
    @Column(name = "tx_doc")
    private byte[] txDoc;

    @Column(name = "tx_doc_content_type")
    private String txDocContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CURRENCY currency;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("transactionsRecords")
    private Customer customer;

    @ManyToOne
    @JsonIgnoreProperties("transactionsRecords")
    private Accounts customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTxType() {
        return txType;
    }

    public TransactionsRecord txType(TransactionType txType) {
        this.txType = txType;
        return this;
    }

    public void setTxType(TransactionType txType) {
        this.txType = txType;
    }

    public String getDescription() {
        return description;
    }

    public TransactionsRecord description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTxAmmount() {
        return txAmmount;
    }

    public TransactionsRecord txAmmount(Double txAmmount) {
        this.txAmmount = txAmmount;
        return this;
    }

    public void setTxAmmount(Double txAmmount) {
        this.txAmmount = txAmmount;
    }

    public String getTxRefNo() {
        return txRefNo;
    }

    public TransactionsRecord txRefNo(String txRefNo) {
        this.txRefNo = txRefNo;
        return this;
    }

    public void setTxRefNo(String txRefNo) {
        this.txRefNo = txRefNo;
    }

    public String getTxCreatedBy() {
        return txCreatedBy;
    }

    public TransactionsRecord txCreatedBy(String txCreatedBy) {
        this.txCreatedBy = txCreatedBy;
        return this;
    }

    public void setTxCreatedBy(String txCreatedBy) {
        this.txCreatedBy = txCreatedBy;
    }

    public LocalDate getTxCreatedDate() {
        return txCreatedDate;
    }

    public TransactionsRecord txCreatedDate(LocalDate txCreatedDate) {
        this.txCreatedDate = txCreatedDate;
        return this;
    }

    public void setTxCreatedDate(LocalDate txCreatedDate) {
        this.txCreatedDate = txCreatedDate;
    }

    public String getTxCompletedBy() {
        return txCompletedBy;
    }

    public TransactionsRecord txCompletedBy(String txCompletedBy) {
        this.txCompletedBy = txCompletedBy;
        return this;
    }

    public void setTxCompletedBy(String txCompletedBy) {
        this.txCompletedBy = txCompletedBy;
    }

    public LocalDate getTxCompletedDate() {
        return txCompletedDate;
    }

    public TransactionsRecord txCompletedDate(LocalDate txCompletedDate) {
        this.txCompletedDate = txCompletedDate;
        return this;
    }

    public void setTxCompletedDate(LocalDate txCompletedDate) {
        this.txCompletedDate = txCompletedDate;
    }

    public TxStatus getStatus() {
        return status;
    }

    public TransactionsRecord status(TxStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TxStatus status) {
        this.status = status;
    }

    public byte[] getTxDoc() {
        return txDoc;
    }

    public TransactionsRecord txDoc(byte[] txDoc) {
        this.txDoc = txDoc;
        return this;
    }

    public void setTxDoc(byte[] txDoc) {
        this.txDoc = txDoc;
    }

    public String getTxDocContentType() {
        return txDocContentType;
    }

    public TransactionsRecord txDocContentType(String txDocContentType) {
        this.txDocContentType = txDocContentType;
        return this;
    }

    public void setTxDocContentType(String txDocContentType) {
        this.txDocContentType = txDocContentType;
    }

    public CURRENCY getCurrency() {
        return currency;
    }

    public TransactionsRecord currency(CURRENCY currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(CURRENCY currency) {
        this.currency = currency;
    }

    public String getRemarks() {
        return remarks;
    }

    public TransactionsRecord remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TransactionsRecord customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Accounts getCustomer() {
        return customer;
    }

    public TransactionsRecord customer(Accounts accounts) {
        this.customer = accounts;
        return this;
    }

    public void setCustomer(Accounts accounts) {
        this.customer = accounts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionsRecord)) {
            return false;
        }
        return id != null && id.equals(((TransactionsRecord) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionsRecord{" +
            "id=" + getId() +
            ", txType='" + getTxType() + "'" +
            ", description='" + getDescription() + "'" +
            ", txAmmount=" + getTxAmmount() +
            ", txRefNo='" + getTxRefNo() + "'" +
            ", txCreatedBy='" + getTxCreatedBy() + "'" +
            ", txCreatedDate='" + getTxCreatedDate() + "'" +
            ", txCompletedBy='" + getTxCompletedBy() + "'" +
            ", txCompletedDate='" + getTxCompletedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", txDoc='" + getTxDoc() + "'" +
            ", txDocContentType='" + getTxDocContentType() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
