package com.perfomatix.mailreader.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;

/**
 * @author Thariq
 * @created 08-03-2022
 **/

@Service
public class MailReceiverServiceImpl implements MailReceiverService{
    @Override
    public void handleMessage(Message message) throws MessagingException, IOException {
        //process message here
        System.out.println(message.getContent());
    }
}
