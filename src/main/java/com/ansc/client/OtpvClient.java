package com.ansc.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.ansc.model.request.CheckOtpRequest;
import com.ansc.model.response.CheckOtpResponse;

import reactor.core.publisher.Mono;

@Component
public class OtpvClient {

	@Value("${config.smoc0.end-point}")
	private String url;
	WebClient webClient = WebClient.create(url);
	
	
	public CheckOtpResponse checkOtp(CheckOtpRequest request) {

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
			//TODO inserire log e gestione eccezzione
		}

		iResp = response.block();
		
		if(ObjectUtils.isEmpty(iResp) || iResp.getAutenticationSucc() == false) {
			//TODO implemnetare eccezzione
		}
		return iResp;
	}
}
