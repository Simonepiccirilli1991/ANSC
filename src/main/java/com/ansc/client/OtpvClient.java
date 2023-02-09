package com.ansc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.ansc.model.request.CheckOtpRequest;
import com.ansc.model.response.CheckOtpResponse;
import com.ansc.service.AnagraficaService;

import reactor.core.publisher.Mono;

@Component
public class OtpvClient {

	@Value("${config.otpv0.end-point}")
	private String url;
	WebClient webClient = WebClient.create(url);
	
	Logger logger = LoggerFactory.getLogger(OtpvClient.class);
	
	
	public CheckOtpResponse checkOtp(CheckOtpRequest request) {
		logger.info("CLIENT :OtpvClient - checkOtp -  START with raw request: {}", request);
		
		CheckOtpResponse iResp = new CheckOtpResponse();
		Mono<CheckOtpResponse> response = null;

		String uri = UriComponentsBuilder.fromHttpUrl(url + "/v1/checkotp").toUriString();
		try {
			response = webClient.post()
					.uri(uri)
					 .bodyValue(request)
					 .retrieve()
                     .bodyToMono(CheckOtpResponse.class)
                     .map(iresp -> {
                         // do something with the response
                         return iresp;
                       });

		}catch(Exception e) {
			logger.error("CLIENT :OtpvClient - checkOtp - EXCEPTION", e);
		}

		iResp = response.block();
		
		if(ObjectUtils.isEmpty(iResp) || iResp.getAutenticationSucc() == false) {
			//TODO implemnetare eccezzione
		}
		logger.info("CLIENT :OtpvClient - checkOtp - END with response: {}", response);
		
		return iResp;
	}
}
