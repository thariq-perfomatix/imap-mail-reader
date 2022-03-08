package com.perfomatix.mailreader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.transformer.Transformer;

@SpringBootApplication
public class MailReaderApplication {



	public static void main(String[] args) {
		SpringApplication.run(MailReaderApplication.class, args);
	}


}
