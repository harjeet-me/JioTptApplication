package com.jio.tms.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Insurance.
 */
@Entity
@Table(name = "insurance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "insurance")
public class Insurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "provider_name")
    private String providerName;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Lob
    @Column(name = "policy_document")
    private byte[] policyDocument;

    @Column(name = "policy_document_content_type")
    private String policyDocumentContentType;

    @Column(name = "coverage_statement")
    private String coverageStatement;

    @OneToOne(mappedBy = "operInsurance")
    @JsonIgnore
    private OwnerOperator ownerOperator;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public Insurance providerName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public Insurance issueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
        return this;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Insurance expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public byte[] getPolicyDocument() {
        return policyDocument;
    }

    public Insurance policyDocument(byte[] policyDocument) {
        this.policyDocument = policyDocument;
        return this;
    }

    public void setPolicyDocument(byte[] policyDocument) {
        this.policyDocument = policyDocument;
    }

    public String getPolicyDocumentContentType() {
        return policyDocumentContentType;
    }

    public Insurance policyDocumentContentType(String policyDocumentContentType) {
        this.policyDocumentContentType = policyDocumentContentType;
        return this;
    }

    public void setPolicyDocumentContentType(String policyDocumentContentType) {
        this.policyDocumentContentType = policyDocumentContentType;
    }

    public String getCoverageStatement() {
        return coverageStatement;
    }

    public Insurance coverageStatement(String coverageStatement) {
        this.coverageStatement = coverageStatement;
        return this;
    }

    public void setCoverageStatement(String coverageStatement) {
        this.coverageStatement = coverageStatement;
    }

    public OwnerOperator getOwnerOperator() {
        return ownerOperator;
    }

    public Insurance ownerOperator(OwnerOperator ownerOperator) {
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
        if (!(o instanceof Insurance)) {
            return false;
        }
        return id != null && id.equals(((Insurance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Insurance{" +
            "id=" + getId() +
            ", providerName='" + getProviderName() + "'" +
            ", issueDate='" + getIssueDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", policyDocument='" + getPolicyDocument() + "'" +
            ", policyDocumentContentType='" + getPolicyDocumentContentType() + "'" +
            ", coverageStatement='" + getCoverageStatement() + "'" +
            "}";
    }
}
