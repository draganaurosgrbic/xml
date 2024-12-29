package com.example.demo.repository.rdf;

import java.util.List;

import org.apache.jena.query.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;

import com.example.demo.common.Constants;
import com.example.demo.common.Namespaces;
import com.example.demo.common.Utils;
import com.example.demo.fuseki.FusekiManager;

@Repository
public class ZalbaRDF implements RDFInterface {

	@Autowired
	private FusekiManager fusekiManager;

	public static final String ZALBA_GRAPH = "/zalbe";
	public static final String ZALBA_SHAPE = Constants.SHAPE_FOLDER + "zalba.ttl";
	public static final String ZALBA_AND_SEARCH = Constants.SPARQL_FOLDER + "zalba_and.rq";
	public static final String ZALBA_OR_SEARCH = Constants.SPARQL_FOLDER + "zalba_or.rq";

	@Override
	public void add(Document document) {
		this.fusekiManager.add(ZALBA_GRAPH, document, ZALBA_SHAPE);
	}
	
	@Override
	public void update(String documentId, Document document) {
		this.fusekiManager.update(ZALBA_GRAPH, Namespaces.ZALBA + "/" + documentId, document, ZALBA_SHAPE);
	}

	@Override
	public void delete(String documentId) {
		this.fusekiManager.delete(ZALBA_GRAPH, Namespaces.ZALBA + "/" + documentId);
	}
	
	@Override
	public ResultSet findAll(String documentId) {
		return this.fusekiManager.findAll(ZALBA_GRAPH, Namespaces.ZALBA + "/" + documentId);
	}
	
	@Override
	public String search(String xml) {
		return Utils.getReferences(this.fusekiManager.search(ZALBA_GRAPH, xml));
	}
	
	public List<String> odgovori(String documentId) {
		return this.fusekiManager.references(OdgovorRDF.ODGOVOR_GRAPH, Namespaces.PREDIKAT + "zalba", Namespaces.ZALBA + "/" + documentId);
	}
	
	public List<String> resenja(String documentId) {
		return this.fusekiManager.references(ResenjeRDF.RESENJE_GRAPH, Namespaces.PREDIKAT + "zalba", Namespaces.ZALBA + "/" + documentId);
	}

}
