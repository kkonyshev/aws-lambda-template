package sh.lambda.email.content.api;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 *
 * Created by Konstantin Konyshev on 19/03/2017.
 */
public class DecryptedData implements ProcessedContent, DecoratedData {

    private static Clock clock = Clock.system(ZoneId.of("UTC"));

    private transient EncryptedContent contentDescriptionData;
    private String version = "0.1";
    private String result;
    private OffsetDateTime processedOn;
    private String error;

    protected DecryptedData() {}

    public DecryptedData(EncryptedContent contentDescriptionData, String result) {
        this.contentDescriptionData = contentDescriptionData;
        this.result = result;
        this.processedOn = OffsetDateTime.now(clock);
        this.error = null;
    }

    public DecryptedData(EncryptedContent contentDescriptionData, String result, String error) {
        this.contentDescriptionData = contentDescriptionData;
        this.result = result;
        this.processedOn = OffsetDateTime.now(clock);
        this.error = error;
    }

    @Override
    public EncryptedContent getData() {
        return this.contentDescriptionData;
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public String getResult() {
        return this.result;
    }

    @Override
    public OffsetDateTime getProcessedOn() {
        return this.processedOn;
    }

    @Override
    public String getError() {
        return this.error;
    }

    public static void setClock(Clock clock) {
        DecryptedData.clock = clock;
    }

    public DecryptedData setContentDescriptionData(EncryptedContent contentDescriptionData) {
        this.contentDescriptionData = contentDescriptionData;
        return this;
    }

    public DecryptedData setVersion(String version) {
        this.version = version;
        return this;
    }

    public DecryptedData setResult(String result) {
        this.result = result;
        return this;
    }

    public DecryptedData setProcessedOn(OffsetDateTime processedOn) {
        this.processedOn = processedOn;
        return this;
    }

    public DecryptedData setError(String error) {
        this.error = error;
        return this;
    }
}
