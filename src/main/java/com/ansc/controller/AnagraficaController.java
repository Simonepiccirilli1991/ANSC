package com.ansc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ansc.model.request.AnagraficaRequest;
import com.ansc.model.response.AnagraficaResponse;
import com.ansc.service.AnagraficaService;

@RestController
@RequestMapping("ag")
public class AnagraficaController {

	@Autowired
	AnagraficaService anagService;
	
	@PostMapping("insert")
	public AnagraficaResponse insertAnag(@RequestBody AnagraficaRequest request) {
		return anagService.insertAna(request);
	}
	
	@GetMapping("get/{bt}")
	public AnagraficaResponse getAnagrafica(@PathVariable ("bt") String bt) {
		return anagService.getAnagrafica(bt);
	}
	
}
