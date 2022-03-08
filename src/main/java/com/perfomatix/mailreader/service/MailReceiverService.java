package com.perfomatix.mailreader.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * @author Thariq
 * @created 08-03-2022
 **/

@Service
public interface MailReceiverService {

    void handleMessage(MimeMessage message) throws MessagingException, IOException;
}
