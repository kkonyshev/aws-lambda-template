package sh.lambda.email.content.util;

import org.jsoup.Jsoup;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

/**
 * Extracts data from MIME style email message as text
 */
public class MimeMessageEncoder {

    /**
     * Email message body
     */
    private final String content;

    public MimeMessageEncoder(MimeMessage message) throws MessagingException, IOException {
        if (message.isMimeType("text/plain")) {
            this.content = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            String result = "";
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result = result + "\n" + bodyPart.getContent();
                    break; // without break same text appears twice in my tests
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + Jsoup.parse(html).text();
                }
            }
            this.content = result;
        } else {
            this.content = "";
        }
    }

    public String getContent() {
        return this.content;
    }
}