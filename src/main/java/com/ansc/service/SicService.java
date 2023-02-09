package com.ansc.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ansc.client.OtpvClient;
import com.ansc.model.Anagrafica;
import com.ansc.model.request.CheckOtpRequest;
import com.ansc.model.request.SicRequest;
import com.ansc.model.response.SicResponse;
import com.ansc.repo.AnagraficaRepo;

@Service
public class SicService {

	@Autowired
	AnagraficaRepo anaRepo;
	@Autowired
	OtpvClient otpv;
	Logger logger = LoggerFactory.getLogger(SicService.class);
	
	// certifica mail
	public SicResponse verifyMail(SicRequest request) {
		logger.info("API :SicService - verifyMail -  START with raw request: {}", request);
		
		SicResponse response = new SicResponse();
		
		Optional<Anagrafica> anagrafica = Optional.ofNullable(anaRepo.findByBt(request.getBt()));
		
		if(anagrafica.isEmpty()) {
			response.setError(true);
			response.setErrMsg("No anagrafica found for bt:"+request.getBt());
			logger.info("API :SicService - verifyMail - END with response: {}", response);
			
			return response;
		}
		
		// check email already certified
		if(Boolean.TRUE.equals(anagrafica.get().getMailCertificata())) {
			response.setError(true);
			response.setErrMsg("Email already certified for bt:"+request.getBt());
			logger.info("API :SicService - verifyMail - END with response: {}", response);
			
			return response;
		}
		//TODO implementare in fragment gestione eccezzioni
		otpv.checkOtp(reqToReq(request));
		
		// update anagrafica
		if(!Boolean.TRUE.equals(updateMailCertified(anagrafica.get()))) {
			response.setError(true);
			response.setErrMsg("Error on updating mail certified for bt:"+request.getBt());
			logger.info("API :SicService - verifyMail - END with response: {}", response);
			return response;
		}
		response.setMsg("Email succefull certified");
		logger.info("API :SicService - verifyMail - END with response: {}", response);
		
		return response;
	}
	
	//check pin
	public SicResponse checkPin(SicRequest request) {
		logger.info("API :SicService - checkPin -  START with raw request: {}", request);
		
		SicResponse response = new SicResponse();
		
		Optional<Anagrafica> anag = Optional.ofNullable(anaRepo.findByBt(request.getBt()));
		
		if(anag.isEmpty()) {
			response.setError(true);
			response.setErrMsg("No anagrafica found for bt:"+request.getBt());
			logger.info("API :SicService - checkPin - END with response: {}", response);
			
			return response;
		}
		
		String pin = anag.get().getPin();
		
		if(!pin.equals(request.getPin())) {
			response.setError(true);
			response.setErrMsg("No valid pin provided for bt:"+request.getBt());
			logger.info("API :SicService - checkPin - END with response: {}", response);
			
			return response;
		}
		
		response.setMsg("Pin check success");
		logger.info("API :SicService - checkPin - END with response: {}", response);
		
		return response;
	}
	
	//change pin
	public SicResponse changePin(SicRequest request) {
		logger.info("API :SicService - changePin -  START with raw request: {}", request);
		
		SicResponse response = new SicResponse();
		
		Optional<Anagrafica> anag = Optional.ofNullable(anaRepo.findByBt(request.getBt()));
		
		if(anag.isEmpty()) {
			response.setError(true);
			response.setErrMsg("No anagrafica found for bt:"+request.getBt());
			logger.info("API :SicService - changePin - END with response: {}", response);
			
			return response;
		}
		// checko otp prima di tutto
		otpv.checkOtp(reqToReq(request));
		
		// controllo che il pin inserito sia diverso da quello vecchio
		if(anag.get().getPin().equals(request.getPin())) {
			response.setError(true);
			response.setErrMsg("New pin have to be different from old one");
			logger.info("API :SicService - changePin - END with response: {}", response);
			
			return response;
		}
		
		anag.get().setPin(request.getPin());
		anaRepo.save(anag.get());
		
		response.setMsg("Pin succfully changed");
		
		logger.info("API :SicService - changePin - END with response: {}", response);
		return response;
	}
	
	
	
	
	
	//update mail certificata
	private Boolean updateMailCertified(Anagrafica anagrafica) {
		logger.info("API :SicService - changePin -  START with raw request: {}", anagrafica);
		
		anagrafica.setMailCertificata(true);
		try {
			anaRepo.save(anagrafica);
		}catch(Exception e) {
			logger.error("Client :AnagraficaService - updateMailCertified - EXCEPTION", e);
			return false;
		}
		logger.info("API :SicService - updateMailCertified - END - complete");
		return true;
	}
	
	//requestoToOtpRequest
	private CheckOtpRequest reqToReq(SicRequest request) {
		
		CheckOtpRequest response = new CheckOtpRequest();
		response.setBt(request.getBt());
		response.setOtp(request.getOtp());
		response.setTransactionId(request.getTrxId());
		response.setProfile("Web");
		
		return response;
	}
}
