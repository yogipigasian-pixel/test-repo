package com.barclaycardus.dynamicurl.entity;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
@DynamoDbBean
public class UrlInformation {
    private String urlId;
    private Long effectiveDate;
    private String partnerCode;
    private String partnerUUID;
    private String contact;
    private String contactType;
    private String workflowCode;
    private String url;
    private Long expireOn;
    private Integer urlAccessCount;
    private String status;
    private Long lockedUpto;
    private Integer urlAccessFailedCount;
    private String createdBy;
    private Long createdOn;
    private String updatedBy;
    private Long updatedOn;
    @DynamoDbPartitionKey
    public String getUrlId() { return urlId; }
    public void setUrlId(String urlId) { this.urlId = urlId; }
    @DynamoDbSecondaryPartitionKey(indexNames = "contact-createdOn-index")
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    @DynamoDbSecondarySortKey(indexNames = "contact-createdOn-index")
    public Long getCreatedOn() { return createdOn; }
    public void setCreatedOn(Long createdOn) { this.createdOn = createdOn; }
    public Long getEffectiveDate() { return effectiveDate; }
    public void setEffectiveDate(Long effectiveDate) { this.effectiveDate = effectiveDate; }
    public String getPartnerCode() { return partnerCode; }
    public void setPartnerCode(String partnerCode) { this.partnerCode = partnerCode; }
    public String getPartnerUUID() { return partnerUUID; }
    public void setPartnerUUID(String partnerUUID) { this.partnerUUID = partnerUUID; }
    public String getContactType() { return contactType; }
    public void setContactType(String contactType) { this.contactType = contactType; }
    public String getWorkflowCode() { return workflowCode; }
    public void setWorkflowCode(String workflowCode) { this.workflowCode = workflowCode; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public Long getExpireOn() { return expireOn; }
    public void setExpireOn(Long expireOn) { this.expireOn = expireOn; }
    public Integer getUrlAccessCount() { return urlAccessCount; }
    public void setUrlAccessCount(Integer urlAccessCount) { this.urlAccessCount = urlAccessCount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getLockedUpto() { return lockedUpto; }
    public void setLockedUpto(Long lockedUpto) { this.lockedUpto = lockedUpto; }
    public Integer getUrlAccessFailedCount() { return urlAccessFailedCount; }
    public void setUrlAccessFailedCount(Integer urlAccessFailedCount) { this.urlAccessFailedCount = urlAccessFailedCount; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }
    // ADDED - this was missing!
    public Long getUpdatedOn() { return updatedOn; }
    public void setUpdatedOn(Long updatedOn) { this.updatedOn = updatedOn; }
}
