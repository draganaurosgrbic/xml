package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.enums.StatusZahteva;
import com.example.demo.enums.TipOdluke;
import com.example.demo.exception.ResourceTakenException;
import com.example.demo.exist.SearchUtil;
import com.example.demo.mapper.OdlukaMapper;
import com.example.demo.mapper.ZahtevMapper;
import com.example.demo.model.Korisnik;
import com.example.demo.model.Pretraga;
import com.example.demo.parser.DOMParser;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.OdlukaRDF;
import com.example.demo.repository.xml.OdlukaExist;
import com.example.demo.service.email.NotificationManager;
import com.example.demo.transformer.OdlukaTransformer;
import com.example.demo.ws.utils.SOAPActions;
import com.example.demo.ws.utils.SOAPService;

@Service
public class OdlukaService implements ServiceInterface {
		
	@Autowired
	private OdlukaExist odlukaExist;
	
	@Autowired
	private OdlukaRDF odlukaRDF;
		
	@Autowired
	private OdlukaMapper odlukaMapper;

	@Autowired
	private KorisnikService korisnikService;

	@Autowired
	private ZahtevService zahtevService;
		
	@Autowired
	private ZalbaService zalbaService;
	
	@Autowired
	private SOAPService soapService;
	
	@Autowired
	private NotificationManager notificationManager;
	
	@Autowired
	private DOMParser domParser;
	
	@Autowired
	private OdlukaTransformer odlukaTransformer;
	
	@Autowired
	private JAXBParser jaxbParser;
	
	@Override
	public void add(String xml) {
		Document document = this.odlukaMapper.map(xml);
		String brojZahteva = ((Element) document.getElementsByTagNameNS(Namespaces.ODLUKA, "datumZahteva").item(0))
				.getAttribute("href").replace(Namespaces.ZAHTEV + "/", "");
		Document zahtevDocument = this.zahtevService.find(brojZahteva);
		
		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			if (ZahtevMapper.getStatusZahteva(zahtevDocument).equals(StatusZahteva.odobreno)) {
				throw new ResourceTakenException();
			}
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odobreno + "");
		}
		else {
			if (!ZahtevMapper.getStatusZahteva(zahtevDocument).equals(StatusZahteva.cekanje)) {
				throw new ResourceTakenException();
			}
			zahtevDocument.getElementsByTagNameNS(Namespaces.ZAHTEV, "status").item(0).setTextContent(StatusZahteva.odbijeno + "");
		}

		if (OdlukaMapper.getTipOdluke(document).equals(TipOdluke.obavestenje)) {
			this.soapService.sendSOAPMessage(this.domParser.buildDocument("<broj>" + brojZahteva + "</broj>"), SOAPActions.otkazi_zalbu);
			this.zalbaService.otkazi(brojZahteva);
		}
		this.odlukaExist.add(document);
		this.odlukaRDF.add(document);
		this.zahtevService.update(brojZahteva, zahtevDocument);
		this.notificationManager.notifyOdluka(document, this.odlukaTransformer.byteHtml(Utils.getBroj(document)), this.odlukaTransformer.bytePdf(Utils.getBroj(document)));		
	}

	@Override
	public void update(String documentId, Document document) {
		this.odlukaExist.update(documentId, document);
		this.odlukaRDF.update(documentId, document);
	}

	@Override
	public void delete(String documentId) {
		this.odlukaExist.delete(documentId);
		this.odlukaRDF.delete(documentId);
	}
	
	@Override
	public String findAll() {
		Korisnik korisnik = this.korisnikService.currentUser();
		String xpathExp;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			xpathExp = "/odluka:Odluka";
		}
		else {
			xpathExp = String.format("/odluka:Odluka[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		return this.odlukaMapper.map(this.odlukaExist.findAll(xpathExp));
	}
	
	@Override
	public Document find(String documentId) {
		return this.odlukaExist.find(documentId);
	}

	@Override
	public String nextDocumentId() {
		return this.odlukaExist.nextDocumentId();
	}
	
	@Override
	public String regularSearch(String xml) {
		Korisnik korisnik = this.korisnikService.currentUser();
		String prefix;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			prefix = "/odluka:Odluka";
		}
		else {
			prefix = String.format("/odluka:Odluka[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		Pretraga pretraga = (Pretraga) this.jaxbParser.unmarshalFromXml(xml, Pretraga.class);
		String xpathExp = String.format("%s%s", prefix, SearchUtil.pretragaToXpath(pretraga));
		ResourceSet resources = this.odlukaExist.findAll(xpathExp);
		return this.odlukaMapper.map(resources);
	}

	@Override
	public String advancedSearch(String xml) {
		Korisnik korisnik = this.korisnikService.currentUser();
		String prefix;
		if (korisnik.getUloga().equals(Constants.SLUZBENIK)) {
			prefix = "/odluka:Odluka";
		}
		else {
			prefix = String.format("/odluka:Odluka[@href='%s']", Namespaces.KORISNIK + "/" + korisnik.getMejl());
		}
		String xpathExp = String.format("%s%s", prefix, this.odlukaRDF.search(xml));
		ResourceSet resources = this.odlukaExist.findAll(xpathExp);
		return this.odlukaMapper.map(resources);
	}

	public List<String> zalbe(String documentId) {
		return this.odlukaRDF.zalbe(documentId);
	}

	public List<String> resenja(String documentId) {
		return this.odlukaRDF.resenja(documentId);
	}
	
}
