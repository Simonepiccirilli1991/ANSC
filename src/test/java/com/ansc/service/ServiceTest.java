package com.ansc.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ansc.client.OtpvClient;
import com.ansc.model.request.AnagraficaRequest;
import com.ansc.model.request.SicRequest;
import com.ansc.model.response.AnagraficaResponse;
import com.ansc.model.response.CheckOtpResponse;
import com.ansc.model.response.SicResponse;

@SpringBootTest
public class ServiceTest {

	@Autowired
	AnagraficaService anagService;
	@Autowired
	SicService sicService;
	@MockBean
	OtpvClient otpv;
	
	
	@Test
	public void inserisciEGetAnagraficaTestOK() {
		
		AnagraficaRequest request = new AnagraficaRequest();
		request.setBancaId("bandaId-1");
		request.setBt("bt-1");
		request.setCelluare("cellulare-1");
		request.setCf("cf-1");
		request.setCognome("cognome-1");
		request.setMail("mail-1");
		request.setNome("nome-1");
		request.setPin("pin");
		
		AnagraficaResponse resp = anagService.insertAna(request);
		
		assertThat(resp.getMsg()).isEqualTo("Ansc-OK");
		
		AnagraficaResponse resp2 = anagService.getAnagrafica("bt-1");
		
		assertThat(resp2.getCelluare()).isEqualTo("cellulare-1");
	}
	
	@Test
	public void certifyMailTestOK() {
		
		AnagraficaRequest request = new AnagraficaRequest();
		request.setBancaId("bandaId-2");
		request.setBt("bt-2");
		request.setCelluare("cellulare-2");
		request.setCf("cf-2");
		request.setCognome("cognome-2");
		request.setMail("mail-2");
		request.setNome("nome-2");
		request.setPin("pin2");
		
		anagService.insertAna(request);
		
		SicRequest iRequest = new SicRequest();
		iRequest.setBt("bt-2");
		iRequest.setOtp("111111");
		iRequest.setPin("pin2");
		iRequest.setTrxId("trxId");
		
		CheckOtpResponse otpRes = new CheckOtpResponse();
		otpRes.setAutenticationSucc(true);
		otpRes.setMsg("daje");
		
		when(otpv.checkOtp(any())).thenReturn(otpRes);
		
		SicResponse iResp =  sicService.verifyMail(iRequest);
		
		assertThat(iResp.getMsg()).isEqualTo("Email succefull certified");
		assertThat(anagService.getAnagrafica("bt-2").getMailCertificata()).isTrue();
		
	}
	
	@Test
	public void checkPinTestOK() {
		
		AnagraficaRequest request = new AnagraficaRequest();
		request.setBancaId("bandaId-3");
		request.setBt("bt-3");
		request.setCelluare("cellulare-3");
		request.setCf("cf-3");
		request.setCognome("cognome-3");
		request.setMail("mail-3");
		request.setNome("nome-3");
		request.setPin("pin3");
		
		anagService.insertAna(request);
		
		SicRequest iRequest = new SicRequest();
		iRequest.setBt("bt-3");
		iRequest.setOtp("111111");
		iRequest.setPin("pin3");
		iRequest.setTrxId("trxId");
		
		SicResponse iResp =  sicService.checkPin(iRequest);
		
		assertThat(iResp.getMsg()).isEqualTo("Pin check success");
	}
	
	@Test
	public void changePinTestOK() {
		
		AnagraficaRequest request = new AnagraficaRequest();
		request.setBancaId("bandaId-4");
		request.setBt("bt-4");
		request.setCelluare("cellulare-4");
		request.setCf("cf-4");
		request.setCognome("cognome-4");
		request.setMail("mail-4");
		request.setNome("nome-4");
		request.setPin("pin4");
		
		anagService.insertAna(request);
		
		SicRequest iRequest = new SicRequest();
		iRequest.setBt("bt-4");
		iRequest.setOtp("111111");
		iRequest.setPin("pin5");
		iRequest.setTrxId("trxId");
		
		CheckOtpResponse otpRes = new CheckOtpResponse();
		otpRes.setAutenticationSucc(true);
		otpRes.setMsg("daje");
		
		when(otpv.checkOtp(any())).thenReturn(otpRes);
		
		SicResponse iResp =  sicService.changePin(iRequest);
		
		assertThat(iResp.getMsg()).isEqualTo("Pin succfully changed");
	}
}
