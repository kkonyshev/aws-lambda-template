package sh.lambda.email.content.api;

/**
 *
 * Created by Konstantin Konyshev on 19/03/2017.
 */
public class EncryptedContent {
    private String uuid;
    private String bucket;
    private String key;
    private byte[] decryptVector;
    private byte[] secret;
    private String algorithm;
    private String cipherTransformation;

    protected EncryptedContent() {}

    public EncryptedContent(String uuid, String bucket, String key, byte[] decryptVector, byte[] secret, String algorithm, String cipherTransformation) {
        this.uuid = uuid;
        this.bucket = bucket;
        this.key = key;
        this.decryptVector = decryptVector;
        this.secret = secret;
        this.algorithm = algorithm;
        this.cipherTransformation = cipherTransformation;
    }

    public String getUuid() {
        return uuid;
    }

    public EncryptedContent setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getBucket() {
        return bucket;
    }

    public EncryptedContent setBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public String getKey() {
        return key;
    }

    public EncryptedContent setKey(String key) {
        this.key = key;
        return this;
    }

    public byte[] getDecryptVector() {
        return decryptVector;
    }

    public EncryptedContent setDecryptVector(byte[] decryptVector) {
        this.decryptVector = decryptVector;
        return this;
    }

    public byte[] getSecret() {
        return secret;
    }

    public EncryptedContent setSecret(byte[] secret) {
        this.secret = secret;
        return this;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public EncryptedContent setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public String getCipherTransformation() {
        return cipherTransformation;
    }

    public EncryptedContent setCipherTransformation(String cipherTransformation) {
        this.cipherTransformation = cipherTransformation;
        return this;
    }
}
