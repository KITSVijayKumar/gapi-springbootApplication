package com.pim.migration.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class CommonFunctions {
	
	public static void productDetails(String productId) throws Exception{		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();		
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document=dBuilder.parse("resources/bv_diy_ratings_080118.xml");
		XPath xp= XPathFactory.newInstance().newXPath();		
		
		NodeList nl=(NodeList)xp.compile("//ProductCrossReference[@ProductID='v_0000500225']").evaluate(document, XPathConstants.NODESET);		
		System.out.println("***********************"+productId+" Details ****************************");
		
		for(int i=0;i<nl.getLength();i++)
			{			
			String mydata=(xp.compile("./MetaData").evaluate(nl.item(i)));			
				if(mydata.length()!=0)
				System.out.print(mydata);	
			}
	}
	
	public void readProductReviews(String ProductEan) throws SAXException, IOException, XPathExpressionException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();		
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document=dBuilder.parse("src/test/resources/bv_diy_ratings_090118.xml");
		
		XPath xp= XPathFactory.newInstance().newXPath();	
		
		XPathExpression ProductReviewsUrl = xp.compile("/Feed/Product[@id="+ProductEan+"]/ProductReviewsUrl");
		XPathExpression AverageOverallRating = xp.compile("/Feed/Product[@id="+ProductEan+"]/ReviewStatistics/AverageOverallRating");
		XPathExpression TotalReviewCount = xp.compile("/Feed/Product[@id="+ProductEan+"]/ReviewStatistics/TotalReviewCount");
		
		String productReviewsUrl = (String) ProductReviewsUrl.evaluate(document, XPathConstants.STRING);
		String averageOverallRating = (String) AverageOverallRating.evaluate(document, XPathConstants.STRING);
		String totalReviewCount = (String) TotalReviewCount.evaluate(document, XPathConstants.STRING);
		
		System.out.println("ProductReviewsUrl : " +productReviewsUrl);
		System.out.println("AverageOverallRating : " +averageOverallRating);
		System.out.println("TotalReviewCount : " +totalReviewCount);
	}

//===========================================================================================================================
	
	DocumentBuilder dBuilder;
	Document document;
	public void readXMLdocument(String fileLocation) throws XPathExpressionException{
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();		
		
		dBuilder = null;
		try {
				dBuilder = dbFactory.newDocumentBuilder();
			} 
		catch (ParserConfigurationException e1) 
			{
				e1.printStackTrace();
			}
		document = null;
		try {
				document = dBuilder.parse(fileLocation);
			} 
		catch (SAXException e) 
			{
				e.printStackTrace();
			} 
		catch (IOException e) 
			{
				e.printStackTrace();
			}
	}
	
	XPath xp;
	public String productReviewsUrl;
	public double averageOverallRating;
	public double totalReviewCount;
	public void getXMLxpathForProductReviewsFrom_ReviewStatistics(String productEan) throws XPathExpressionException {
		
		xp= XPathFactory.newInstance().newXPath();	
		
		XPathExpression ProductReviewsUrl = xp.compile("/Feed/Product[@id="+productEan+"]/ProductReviewsUrl");
		XPathExpression AverageOverallRating = xp.compile("/Feed/Product[@id="+productEan+"]/ReviewStatistics/AverageOverallRating");
		XPathExpression TotalReviewCount = xp.compile("/Feed/Product[@id="+productEan+"]/ReviewStatistics/TotalReviewCount");
		
		productReviewsUrl = (String) ProductReviewsUrl.evaluate(document, XPathConstants.STRING);
		averageOverallRating = (double) AverageOverallRating.evaluate(document, XPathConstants.NUMBER);
		totalReviewCount = (double) TotalReviewCount.evaluate(document, XPathConstants.NUMBER);
	}
	
	public void getXMLxpathForProductReviewsFrom_NativeReviewStatistics(String productEan) throws XPathExpressionException {
		
		xp= XPathFactory.newInstance().newXPath();	
		
		XPathExpression ProductReviewsUrl = xp.compile("/Feed/Product[@id="+productEan+"]/ProductReviewsUrl");
		XPathExpression AverageOverallRating = xp.compile("/Feed/Product[@id="+productEan+"]/NativeReviewStatistics/AverageOverallRating");
		XPathExpression TotalReviewCount = xp.compile("/Feed/Product[@id="+productEan+"]/NativeReviewStatistics/TotalReviewCount");
		
		productReviewsUrl = (String) ProductReviewsUrl.evaluate(document, XPathConstants.STRING);
		averageOverallRating = (double) AverageOverallRating.evaluate(document, XPathConstants.NUMBER);
		totalReviewCount = (double) TotalReviewCount.evaluate(document, XPathConstants.NUMBER);
	}
	
	String STORECODE;
	public String EAN, STOCKLEVEL, RANGED, OPCO;
	public String[] STOCK;
	public void readCSV(String storeID, String productEan, String csvFile) {
		String line = "";
        String csvSplitBy = ",";
   
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {               
            	STOCK = line.split(csvSplitBy);
            	
            	STORECODE = STOCK[0];
        		EAN = STOCK[1];
        		STOCKLEVEL = STOCK[2];
        		RANGED = STOCK[3];
        		OPCO = STOCK[4];
        		
 
                if(STORECODE.equalsIgnoreCase(storeID) && EAN.equalsIgnoreCase(productEan)){
                System.out.println("csvFile Matched Line: "+ STORECODE + "," + EAN + ","+ STOCKLEVEL+ ","+ RANGED+ ","+OPCO);
                System.out.println("csvFile StockData for Matched Line: "+" \n\t StoreCode: "+STORECODE + " \n\t ProductEan: "+EAN + " \n\t StockLevel: "+STOCKLEVEL + " \n\t Ranged: "+RANGED + " \n\t OpCO: "+OPCO);
                }
            }
        }
        catch (IOException e) 
        {
        	e.printStackTrace();
        }
	}
}
	
	
	
	
	