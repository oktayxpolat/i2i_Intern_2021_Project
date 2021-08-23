package model;

public class HazelcastModel {
    String msidn;
    int partitionKey;

    public HazelcastModel(String msidn, int partitionKey) {
        this.msidn = msidn;
        this.partitionKey = partitionKey;
    }

    public String getMsidn() {
        return msidn;
    }

    public void setMsidn(String msidn) {
        this.msidn = msidn;
    }

    public int getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(int partitionKey) {
        this.partitionKey = partitionKey;
    }
}
