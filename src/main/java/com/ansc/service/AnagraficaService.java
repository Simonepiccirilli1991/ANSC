package com.ansc.service;

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
	
	public AnagraficaResponse insertAna(AnagraficaRequest request) {
		
		AnagraficaResponse response = new AnagraficaResponse();
		
		try {
			anaRepo.save(requestToAna(request));
		}catch(Exception e) {
			
			response.setError(true);
			response.setErrMsg("Error during saving anagrafica for bt:"+request.getBt());
			return response;
		}
		
		response.setMsg("Ansc-OK");
		return response;
	}
	
	
	public AnagraficaResponse getAnagrafica(String bt) {
		
		AnagraficaResponse response = new AnagraficaResponse();
		
		Anagrafica anagrafica = null;
		try {
			
			anagrafica = anaRepo.findByBt(bt);
			response = entityToResponse(anagrafica);
			
		}catch(Exception e) {
			response.setError(true);
			response.setErrMsg("Error during retriving anagrafica for bt:"+bt);
			return response;
		}
		
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
