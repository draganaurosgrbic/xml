package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.ResourceSet;

import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.exist.SearchUtil;
import com.example.demo.mapper.ResenjeMapper;
import com.example.demo.model.Pretraga;
import com.example.demo.parser.JAXBParser;
import com.example.demo.repository.rdf.ResenjeRDF;
import com.example.demo.repository.xml.ResenjeExist;

@Service
public class ResenjeService implements ServiceInterface {
	
	@Autowired
	private ResenjeExist resenjeExist;
	
	@Autowired
	private ResenjeRDF resenjeRDF;

	@Autowired
	private ResenjeMapper resenjeMapper;
	
	@Autowired
	private ZalbaService zalbaService;
	
	@Autowired
	private JAXBParser jaxbParser;
		
	@Override
	public void add(String xml) {
		Document document = this.resenjeMapper.map(xml);
		String brojZalbe = ((Element) document.getElementsByTagNameNS(Namespaces.RESENJE, "datumZalbe").item(0))
				.getAttribute("href").replace(Namespaces.ZALBA + "/", "");
		Document zalbaDocument = this.zalbaService.find(brojZalbe);

		this.resenjeExist.update(Utils.getBroj(document), document);
		this.resenjeRDF.add(document);
		zalbaDocument.getElementsByTagNameNS(Namespaces.ZALBA, "status").item(0).setTextContent(ResenjeMapper.getStatusResenja(document) + "");
		this.zalbaService.update(brojZalbe, zalbaDocument);
	}

	@Override
	public void update(String documentId, Document document) {
		this.resenjeExist.update(documentId, document);
		this.resenjeRDF.update(documentId, document);
	}
	
	@Override
	public void delete(String documentId) {
		this.resenjeExist.delete(documentId);
		this.resenjeRDF.delete(documentId);
	}

	@Override
	public String findAll() {
		return this.resenjeMapper.map(this.resenjeExist.findAll("/resenje:Resenje"));		
	}

	@Override
	public Document find(String documentId) {
		return this.resenjeExist.find(documentId);
	}
	
	@Override
	public String nextDocumentId() {
		return this.resenjeExist.nextDocumentId();
	}

	@Override
	public String regularSearch(String xml) {
		Pretraga pretraga = (Pretraga) this.jaxbParser.unmarshalFromXml(xml, Pretraga.class);
		String xpathExp = String.format("/resenje:Resenje%s", SearchUtil.pretragaToXpath(pretraga));
		ResourceSet resources = this.resenjeExist.findAll(xpathExp);
		return this.resenjeMapper.map(resources);
	}

	@Override
	public String advancedSearch(String xml) {
		String xpathExp = String.format("/resenje:Resenje%s", this.resenjeRDF.search(xml));
		ResourceSet resources = this.resenjeExist.findAll(xpathExp);
		return this.resenjeMapper.map(resources);
	}

}
