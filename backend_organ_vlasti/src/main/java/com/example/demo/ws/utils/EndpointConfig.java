package com.example.demo.ws.utils;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.ws.odluka.OdlukaPortImpl;
import com.example.demo.ws.resenje.ResenjePortImpl;
import com.example.demo.ws.zahtev.ZahtevPortImpl;
import com.example.demo.ws.zalba.ZalbaPortImpl;

@Configuration
public class EndpointConfig {

	@Autowired
	private Bus bus;
	
	@Autowired
	private ZalbaPortImpl zlpi;
	
	@Autowired
	private ResenjePortImpl rpi;
	
	@Autowired
	private ZahtevPortImpl zhpi;
	
	@Autowired
	private OdlukaPortImpl opi;
	
	@Bean
	public Endpoint zalbaEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(this.bus, this.zlpi);
		endpoint.publish("/zalba");
		return endpoint;
	}
	
	@Bean
	public Endpoint resenjeEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(this.bus, this.rpi);
		endpoint.publish("/resenje");
		return endpoint;
	}
	
	@Bean
	public Endpoint zahtevEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(this.bus, this.zhpi);
		endpoint.publish("/zahtev");
		return endpoint;
	}

	@Bean
	public Endpoint odlukaEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(this.bus, this.opi);
		endpoint.publish("/odluka");
		return endpoint;
	}
	
}
