package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.enums.MetadataType;
import com.example.demo.service.ResenjeService;
import com.example.demo.transformer.ResenjeTransformer;

import org.springframework.core.io.Resource;

@RestController
@RequestMapping(value = "/api/resenja")
@PreAuthorize("hasAuthority('sluzbenik')")
public class ResenjeContoller {

	@Autowired
	private ResenjeService resenjeService;

	@Autowired
	private ResenjeTransformer resenjeTransformer;

	@GetMapping(produces = "text/xml; charset=utf-8")
	public ResponseEntity<String> findAll() {
		return new ResponseEntity<>(this.resenjeService.findAll(), HttpStatus.OK);
	}

	@GetMapping(value = "/{broj}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Object> find(@PathVariable String broj, @RequestHeader("Accept") String format) {
		if (format.equals("text/html")) {
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "text/html; charset=utf-8")
					.body(this.resenjeTransformer.html(broj));
		}
		Resource resource = this.resenjeTransformer.pdf(broj);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/pdf")
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping(value = "/{broj}/metadata")
	public ResponseEntity<String> metadata(@PathVariable String broj, @RequestHeader("Accept") String format) {
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, format + "; charset=utf-8")
				.body(this.resenjeTransformer.metadata(broj,
						format.equals("text/xml") ? MetadataType.xml : MetadataType.json));
	}

	@PostMapping(value = "pretraga", consumes = "text/xml; charset=utf-8", produces = "text/xml; charset=utf-8")
	public ResponseEntity<String> regularSearch(@RequestBody String xml, @RequestHeader("search-type") String tipPretrage) {
		if (tipPretrage.equals("obicna")) {
			return new ResponseEntity<>(this.resenjeService.regularSearch(xml), HttpStatus.OK);			
		}
		return new ResponseEntity<>(this.resenjeService.advancedSearch(xml), HttpStatus.OK);
	}

}
