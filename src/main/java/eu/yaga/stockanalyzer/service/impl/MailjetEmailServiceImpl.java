package eu.yaga.stockanalyzer.service.impl;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Contact;
import com.mailjet.client.resource.Email;
import eu.yaga.stockanalyzer.service.EmailService;
import eu.yaga.stockanalyzer.util.EmailProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementaion of {@link EmailService} that uses mailjet.com as mail provider
 */
public class MailjetEmailServiceImpl implements EmailService {

    private static final Logger log = LoggerFactory.getLogger(MailjetEmailServiceImpl.class);

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void send(String subject, String text) {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        JSONArray mjRecipients;

        mjRecipients = new JSONArray();
        for (String recipient : emailProperties.getListRecipient()) {
            mjRecipients.put(new JSONObject().put(Contact.EMAIL, recipient));
        }

        client = new MailjetClient(emailProperties.getMjApikeyPublic(), emailProperties.getMjApikeyPrivate());
        request = new MailjetRequest(Email.resource)
                .property(Email.FROMEMAIL, emailProperties.getFromEmail())
                .property(Email.FROMNAME, emailProperties.getFromName())
                .property(Email.SUBJECT, subject)
                .property(Email.TEXTPART, text)
                .property(Email.RECIPIENTS, mjRecipients);

        try {
            response = client.post(request);
            log.info("Mail API response: " + response.getStatus());
            log.info("Mail API response: " + response.getData());
        } catch (MailjetException e) {
            log.error("Error sending mail: " + e.getLocalizedMessage());
        } catch (MailjetSocketTimeoutException e) {
            log.error("Timeout sending mail: " + e.getLocalizedMessage());
        }
    }
}
