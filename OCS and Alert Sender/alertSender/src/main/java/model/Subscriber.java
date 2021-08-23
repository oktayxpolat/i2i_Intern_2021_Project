package model;

import java.util.Date;

public class Subscriber {
    int balKey;
    String msisdn;
    int partitionKey;
    int packageId;
    String packageType;
    int usedAmount;
    int limitOfPackage;
    Date startDate;
    Date endDate;

    public Subscriber(int balKey, String msisdn, int partitionKey, int packageId, String packageType, int usedAmount, int limitOfPackage, Date startDate, Date endDate) {
        this.balKey = balKey;
        this.msisdn = msisdn;
        this.partitionKey = partitionKey;
        this.packageId = packageId;
        this.packageType = packageType;
        this.usedAmount = usedAmount;
        this.limitOfPackage = limitOfPackage;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public int getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(int usedAmount) {
        this.usedAmount = usedAmount;
    }

    public int getLimitOfPackage() {
        return limitOfPackage;
    }

    public void setLimitOfPackage(int limitOfPackage) {
        this.limitOfPackage = limitOfPackage;
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
}
