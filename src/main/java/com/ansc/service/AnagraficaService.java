package com.ansc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ansc.model.Anagrafica;
import com.ansc.model.request.AnagraficaRequest;
import com.ansc.model.response.AnagraficaResponse;
import com.ansc.repo.AnagraficaRepo;

@Service
public class AnagraficaService {

	@Autowired
	AnagraficaRepo anaRepo;
	
	Logger logger = LoggerFactory.getLogger(AnagraficaService.class);
	
	
	public AnagraficaResponse insertAna(AnagraficaRequest request) {
		logger.info("API :AnagraficaService - insertAna -  START with raw request: {}", request);
		
		AnagraficaResponse response = new AnagraficaResponse();
		
		try {
			anaRepo.save(requestToAna(request));
		}catch(Exception e) {
			logger.error("REPO :AnagraficaService - insertAna - EXCEPTION", e);
			response.setError(true);
			response.setErrMsg("Error during saving anagrafica for bt:"+request.getBt());
			return response;
		}
		
		response.setMsg("Ansc-OK");
		logger.info("API :AnagraficaService - insinsertAnaert - END with response: {}", response);
		
		return response;
	}
	
	
	public AnagraficaResponse getAnagrafica(String bt) {
		logger.info("API :AnagraficaService - getAnagrafica -  START with raw request: {}", bt);
		AnagraficaResponse response = new AnagraficaResponse();
		
		Anagrafica anagrafica = null;
		try {
			
			anagrafica = anaRepo.findByBt(bt);
			response = entityToResponse(anagrafica);
			
		}catch(Exception e) {
			logger.error("Client :AnagraficaService - getAnagrafica - EXCEPTION", e);
			response.setError(true);
			response.setErrMsg("Error during retriving anagrafica for bt:"+bt);
			return response;
		}
		
		logger.info("API :AnagraficaService - getAnagrafica - END with response: {}", response);
		return response;
	}
	
	private AnagraficaResponse entityToResponse(Anagrafica ana) {
		
		AnagraficaResponse response = new AnagraficaResponse();
		response.setBancaId(ana.getBancaId());
		response.setCelluare(ana.getCelluare());
		response.setCf(ana.getCf());
		response.setCognome(ana.getCognome());
		response.setMail(ana.getMail());
		response.setMailCertificata(ana.getMailCertificata());
		response.setNome(ana.getNome());
		
		return response;
	}
	
	private Anagrafica requestToAna(AnagraficaRequest request) {
		
		Anagrafica ana = new Anagrafica();
		ana.setBancaId(request.getBancaId());
		ana.setBt(request.getBt());
		ana.setCelluare(request.getCelluare());
		ana.setCf(request.getCf());
		ana.setCognome(request.getCognome());
		ana.setMail(request.getMail());
		ana.setNome(request.getNome());
		ana.setMailCertificata(false);
		ana.setPin(request.getPin());
		
		return ana;
	}
}
