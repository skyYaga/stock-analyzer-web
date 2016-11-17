package eu.yaga.stockanalyzer.service;

import org.springframework.stereotype.Service;

/**
 * Interface for email sending
 */
@Service
public interface EmailService {

    void send(String subject, String text);

}
