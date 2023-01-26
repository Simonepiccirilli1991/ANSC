package com.ansc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ansc.model.request.SicRequest;
import com.ansc.model.response.SicResponse;
import com.ansc.service.SicService;

@RestController
@RequestMapping("sic")
public class SicController {

	@Autowired
	SicService sicService;
	
	@PostMapping("certify")
	public SicResponse certify(@RequestBody SicRequest request) {
		return sicService.verifyMail(request);
	}
	
	@PostMapping("checkpin")
	public SicResponse checkPin(@RequestBody SicRequest request) {
		return sicService.checkPin(request);
	}
	
	@PostMapping("changepin")
	public SicResponse changePin(@RequestBody SicRequest request) {
		return sicService.changePin(request);
	}
}
