package com.pim.migration.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXML {
	
	public String CDATA;
	public String[] techSpecCDATA;
	
	public void readTechSpecAttributes() throws ParserConfigurationException, IOException, SAXException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("VJ_CastoSelling_Delta_RS_XML_3663602063650.xml");
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder =  dbfactory.newDocumentBuilder();
		Document document = docBuilder.parse(inputStream);
		
		NodeList TechSpecList = document.getElementsByTagName("Attribute");
		
		techSpecCDATA = new String[TechSpecList.getLength()];
		
		for (int i=0; i<TechSpecList.getLength(); i++) {
			
			Node techSpecList = TechSpecList.item(i);
			if(techSpecList.getNodeType() == Node.ELEMENT_NODE) {
				Element TechSpec = (Element) techSpecList;
				String name = TechSpec.getAttribute("Name");
				
				CDATA = TechSpec.getTextContent().trim();
				
				if(CDATA.length()!= 0 && name.contains("Tech Spec"))
				{					
				System.out.println(name + ":" + CDATA);
				techSpecCDATA[i] = name + ":" + CDATA;
				}
				else if(CDATA.trim().length()== 0 && name.contains("Tech Spec"))
				{						
				System.out.println(name + ":" + "No Values");
				} 
			}
		}
	}
	
	public void readEmptyTechSpecAttributes() throws ParserConfigurationException, IOException, SAXException {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("VJ_CastoSelling_Delta_RS_XML_3663602063650.xml");
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder =  dbfactory.newDocumentBuilder();
		Document document = docBuilder.parse(inputStream);
		
		NodeList TechSpecList = document.getElementsByTagName("Attribute");
		for (int i=0; i<TechSpecList.getLength(); i++) {
			Node techSpecList = TechSpecList.item(i);
			if(techSpecList.getNodeType() == Node.ELEMENT_NODE) {
				Element TechSpec = (Element) techSpecList;
				String name = TechSpec.getAttribute("Name");
				
				String CDATA = TechSpec.getTextContent().trim();
				
				if(CDATA.trim().length()== 0 && name.contains("Tech Spec")){				
					System.out.println(name + ":" + "No Values");
				} 
			}
		}
	}
		
	public void readCDATAFromXMLUsingStax() {
		String xmlfile = "VJ_CastoSelling_Delta_RS_XML_3663602063650.xml";
		XMLStreamReader r = null;
		try (InputStream in = new BufferedInputStream(new FileInputStream(xmlfile));) {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			r = factory.createXMLStreamReader(in);			
			while (r.hasNext()) {
				switch (r.getEventType()) {
				case XMLStreamConstants.CHARACTERS:
				case XMLStreamConstants.CDATA:
					if(r.getText().toString().trim().length()!= 0){
						System.out.println(r.getText());
					}
					break;
				default:
					break;
				}
				r.next();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}			
	
	public void the_user_verifies_the_same_with_rs_xml() throws Throwable, SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
	
		try {
		DocumentBuilder docBuilder =  dbfactory.newDocumentBuilder();
		Document doc = docBuilder.parse("resources/VJ_CastoSelling_Delta_RS_XML_3663602063650.xml");
		NodeList TechSpecList = doc.getElementsByTagName("Tech Spec");
		for (int i=0; i<TechSpecList.getLength(); i++) {
			Node TSL = TechSpecList.item(i);
			if(TSL.getNodeType() == Node.ELEMENT_NODE) {
				Element TechSpec = (Element) TSL;
				String name = TechSpec.getAttribute("Name");
				NodeList nameList = TechSpec.getChildNodes();
				for (int j=0; j<nameList.getLength(); j++) {
					Node n = nameList.item(j);
					if (n.getNodeType() == Node.ELEMENT_NODE) {
						Element childName = (Element) n;
						System.out.println("TechSpec" + name + ":" +childName.getTagName() + "=" +childName.getTextContent());;
					}
				}
			}
		}
		
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SAXException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
   }
}




