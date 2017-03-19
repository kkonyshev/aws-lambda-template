package sh.lambda.email.content.api;

import java.time.OffsetDateTime;

/**
 *
 * Created by Konstantin Konyshev on 19/03/2017.
 */
public interface ProcessedContent {
    String getVersion();
    String getResult();
    OffsetDateTime getProcessedOn();
    String getError();
}
