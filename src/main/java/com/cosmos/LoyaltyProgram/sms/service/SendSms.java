package com.cosmos.LoyaltyProgram.sms.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cosmos.LoyaltyProgram.model.Transaction;
import com.cosmos.LoyaltyProgram.sms.modal.SendSMSResponse;

@Service("sendSms")
public class SendSms {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${sms.apikey}")
	String apiKey;

	@Value("${sms.sendsms.url}")
	private String sendurl;
	
	Logger logger=Logger.getLogger(SendSms.class);
	
	@Async
	public void sendTransactionSuccessSMStoDriver(Transaction transaction){
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("apikey", apiKey);
		params.add("numbers", transaction.getDriver().getPhoneNumber());
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
	

}
