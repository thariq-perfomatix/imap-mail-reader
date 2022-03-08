package com.perfomatix.mailreader;

import com.perfomatix.mailreader.service.MailReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.MailReceiver;
import org.springframework.integration.mail.MailReceivingMessageSource;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Thariq
 * @created 08-03-2022
 **/

@RequiredArgsConstructor
@Configuration
@EnableIntegration
public class MailReaderConfiguration {

    private final MailReceiverService receiveMailService;


    @ServiceActivator(inputChannel = "receiveEmailChannel")
    public void receive(Message message) throws MessagingException, IOException {
        receiveMailService.handleMessage(message);
    }

    @Bean("receiveEmailChannel")
    public DirectChannel defaultChannel() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setDatatypes(javax.mail.internet.MimeMessage.class);
        return directChannel;
    }

    @Bean()
    @InboundChannelAdapter(
            channel = "receiveEmailChannel",
            poller = @Poller(fixedDelay = "5000")
    )
    public MailReceivingMessageSource mailMessageSource(MailReceiver mailReceiver) {
        MailReceivingMessageSource mailReceivingMessageSource = new MailReceivingMessageSource(mailReceiver);
        return mailReceivingMessageSource;
    }

    @Bean
    public MailReceiver imapMailReceiver(@Value("imaps://${mail.receiver.imap.username}:${mail.receiver.imap.password}@${mail.receiver.imap.host}/${mail.receiver.imap.folder}") String storeUrl) {
        ImapMailReceiver imapMailReceiver = new ImapMailReceiver(storeUrl);
        imapMailReceiver.setShouldMarkMessagesAsRead(true);
        imapMailReceiver.setShouldDeleteMessages(false);
        imapMailReceiver.setMaxFetchSize(10);
        // imapMailReceiver.setAutoCloseFolder(true);
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.put("mail.imap.socketFactory.fallback", false);
        javaMailProperties.put("mail.store.protocol", "imaps");
        javaMailProperties.put("mail.debug", true);

        imapMailReceiver.setJavaMailProperties(javaMailProperties);

        return imapMailReceiver;
    }


}

