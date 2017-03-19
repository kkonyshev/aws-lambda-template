package sh.lambda.email.content.api;

/**
 *
 * Created by Konstantin Konyshev on 19/03/2017.
 */
public class ProcessedData {
    private String uuid;
    private String bucket;
    private String key;
    private boolean success;

    protected ProcessedData() {}

    public ProcessedData(String uuid, String bucket, String key, boolean success) {
        this.uuid = uuid;
        this.bucket = bucket;
        this.key = key;
        this.success = success;
    }

    public String getUuid() {
        return uuid;
    }

    public ProcessedData setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public String getKey() {
        return key;
    }

    public ProcessedData setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public ProcessedData setKey(String key) {
        this.key = key;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public ProcessedData setSuccess(boolean success) {
        this.success = success;
        return this;
    }
}
