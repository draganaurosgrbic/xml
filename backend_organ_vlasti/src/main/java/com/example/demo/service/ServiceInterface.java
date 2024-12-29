package com.example.demo.service;

import org.w3c.dom.Document;

public interface ServiceInterface {
	
	public void add(String xml);
	public void update(String documentId, Document document);
	public void delete(String documentId);
	public String findAll();
	public Document find(String documentId);
	public String nextDocumentId();
	public String regularSearch(String xml);
	public String advancedSearch(String xml);

}
