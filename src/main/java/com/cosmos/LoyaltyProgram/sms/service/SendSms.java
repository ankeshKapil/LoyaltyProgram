package com.cosmos.LoyaltyProgram.sms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cosmos.LoyaltyProgram.model.Driver;
import com.cosmos.LoyaltyProgram.model.Transaction;
import com.cosmos.LoyaltyProgram.sms.bulk.modal.BulkSMSData;
import com.cosmos.LoyaltyProgram.sms.bulk.modal.Message;
import com.cosmos.LoyaltyProgram.sms.modal.SendSMSResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("sendSms")
public class SendSms {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${sms.apikey}")
	String apiKey;

	@Value("${sms.sendsms.url}")
	private String sendurl;
	
	@Value("${sms.sendsms.bulk.url}")
	private String bulksendurl;
	
	Logger logger=Logger.getLogger(SendSms.class);
	
	@Async
	public void sendTransactionSuccessSMStoDriver(Transaction transaction){
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("apikey", apiKey);
		params.add("numbers", transaction.getDriver().getPhoneNumber());
		params.add("test", "true");
		String message="";
		if(transaction.isRedeem()){
			 message="Priy "+ transaction.getDriver().getFirstName()+", Bruj Mohar Petro Centre par aapne "+transaction.getFuelVolume()+ 
					 " loyalty points apne account se Redeem karaye hain."
					+ " Dhanyawad";
		}
		else{
			 message="Priy "+ transaction.getDriver().getFirstName()+", Bruj Mohar Petro Centre par aapne " +transaction.getFuelVolume()+ " Lt diesel"
					+ " dalwaya hai. Aapke account mein " +transaction.getFuelVolume()+ " loyalty points jama kiye gye hain."
					+ " Dhanyawad";
		}
	
		params.add("message", message);
		logger.info("SENDING SMS:-"+message);
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(sendurl).queryParams(params).build();
		logger.info(uriComponents.toUri());
		SendSMSResponse sendSMSResponse=restTemplate.postForObject(uriComponents.toUri(),null, SendSMSResponse.class);
		logger.info(sendSMSResponse);
	}
	
	@Async
	public void sendNotifyDormantDriver(List<Driver> drivers){
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("apikey", apiKey);
	//	params.add("numbers", StringUtils.collectionToDelimitedString(drivers.stream().map(Driver::getPhoneNumber).collect(Collectors.toCollection(ArrayList::new)), ","));
		//params.add("test", "true");
		BulkSMSData data=new BulkSMSData();
		List<Message> bulkMsg=new ArrayList<>();
		for(Driver driver:drivers){
			Message message=new Message();
			message.setNumber(driver.getPhoneNumber());
			message.setText("Priy"+driver.getFirstName()+",Aapne bde dino se Bruj Mohan Petro Centre par fueling nahi karwayi hai. Kripya seva ka mauka dein.");
			bulkMsg.add(message);
		};
		data.setMessages(bulkMsg);
		ObjectMapper mapper=new ObjectMapper();
		try {
			params.add("data", mapper.writeValueAsString(data));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(bulksendurl).queryParams(params).build();
		logger.info(uriComponents.toUri());
		SendSMSResponse sendSMSResponse=restTemplate.postForObject(uriComponents.toUri(),null, SendSMSResponse.class);
		logger.info(sendSMSResponse);
	}
	
	

}
