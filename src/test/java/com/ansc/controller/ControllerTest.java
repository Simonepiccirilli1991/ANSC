package com.ansc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ansc.client.OtpvClient;
import com.ansc.model.request.AnagraficaRequest;
import com.ansc.model.request.SicRequest;
import com.ansc.model.response.AnagraficaResponse;
import com.ansc.model.response.CheckOtpResponse;
import com.ansc.model.response.SicResponse;
import com.ansc.service.AnagraficaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ControllerTest {

	@Autowired
	MockMvc mvc;
	@Autowired
	AnagraficaService anagService;
	@MockBean
	OtpvClient otpv;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void insertEGetAnagraficaTestOK() throws Exception {
		
		AnagraficaRequest request = new AnagraficaRequest();
		request.setBancaId("bandaId-1");
		request.setBt("bt-1");
		request.setCelluare("cellulare-1");
		request.setCf("cf-1");
		request.setCognome("cognome-1");
		request.setMail("mail-1");
		request.setNome("nome-1");
		request.setPin("pin");
		
		String response = mvc.perform(post("/ag/insert")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		AnagraficaResponse resp = mapper.readValue(response, AnagraficaResponse.class);
		
		assertThat(resp.getMsg()).isEqualTo("Ansc-OK");
		
		String response2 = mvc.perform(get("/ag/get/bt-1")
				.contentType("application/json"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		AnagraficaResponse resp2 = mapper.readValue(response2, AnagraficaResponse.class);
		
		assertThat(resp2.getCelluare()).isEqualTo("cellulare-1");
	}
	
	@Test
	public void certifyMailTestOK() throws Exception {
		
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
		
		String response = mvc.perform(post("/sic/certify")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		SicResponse iResp =  mapper.readValue(response, SicResponse.class);
		
		assertThat(iResp.getMsg()).isEqualTo("Email succefull certified");
		assertThat(anagService.getAnagrafica("bt-2").getMailCertificata()).isTrue();
	}
	
	@Test
	public void checkPinTestOK() throws Exception {
		
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
		
		String response = mvc.perform(post("/sic/checkpin")
				.contentType("application/json")
				.content(mapper.writeValueAsString(request)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		SicResponse iResp =   mapper.readValue(response, SicResponse.class);
		
		assertThat(iResp.getMsg()).isEqualTo("Pin check success");
	}
	
	@Test
	public void changePinTestOK() throws Exception {
		
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
		
		String response = mvc.perform(post("/sic/changepin")
				.contentType("application/json")
				.content(mapper.writeValueAsString(iRequest)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		SicResponse iResp =  mapper.readValue(response, SicResponse.class);
		
		assertThat(iResp.getMsg()).isEqualTo("Pin succfully changed");
	}
}
