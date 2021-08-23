package model;

import java.io.Serializable;
import java.util.Date;

public class UsageToSendKafka implements Serializable {
    int balKey;
    String msisdn;
    int partitionKey;
    int packageId;
    int usedAmount;
    Date startDate;
    Date endDate;
    Long lastUpdateDate;
    String operation;

    public UsageToSendKafka(int balKey, String msisdn, int partitionKey, int packageId, int usedAmount, Date startDate, Date endDate) {
        this.balKey = balKey;
        this.msisdn = msisdn;
        this.partitionKey = partitionKey;
        this.packageId = packageId;
        this.usedAmount = usedAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastUpdateDate =  new Date().getTime();;
        this.operation = "update";
    }

    public int getBalKey() {
        return balKey;
    }

    public void setBalKey(int balKey) {
        this.balKey = balKey;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public int getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(int partitionKey) {
        this.partitionKey = partitionKey;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public int getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(int usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Long lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
