package models.endpoint;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;

import play.Play;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import controllers.Application;

public class SparqlEndpoint {

	public static ResultSet queryData(String q)
	{
		String directory = Play.application().path() + "/public/data/";
        Dataset dataset = TDBFactory.createDataset(directory);

        // GET MODEL
        Model taweb = dataset.getNamedModel("taweb");

        // GET NAMESPACES FROM MODELS
        String dbpediaowlNS = taweb.getNsPrefixURI("dbpedia-owl");
    	String dbpediaNS = taweb.getNsPrefixURI("dbpedia");
    	String dbppropNS = taweb.getNsPrefixURI("dbpprop");
    	String revNS = taweb.getNsPrefixURI("rev");
    	String geoNS = taweb.getNsPrefixURI("geo");
    	String customNS = taweb.getNsPrefixURI("custom");

    	// PREFIXES
    	String prefixes = 	"PREFIX rdfs: <"+RDFS.getURI()+"> "+
    						"PREFIX rdf: <"+RDF.getURI()+"> " +
    						"PREFIX owl: <"+OWL.getURI()+"> " +
    						"PREFIX dc: <"+DC.getURI()+"> " +
    						"PREFIX foaf: <"+FOAF.getURI()+"> "+
    						"PREFIX dbpedia-owl: <"+dbpediaowlNS+"> "+
    						"PREFIX dbpedia: <"+dbpediaNS+"> " +
    						"PREFIX dbpprop: <"+dbppropNS+"> " +
    						"PREFIX rev: <"+revNS+"> " +
    						"PREFIX geo: <"+geoNS+"> " +
    						"PREFIX custom: <"+customNS+"> ";

        // SPARQL
    	String q1 = prefixes + q;

    	try {
        Query query = QueryFactory.create(q1);
        QueryExecution qexec = QueryExecutionFactory.create(query, taweb);
        ResultSet results = qexec.execSelect();
        
//        System.out.println("--1--");
//        System.out.println(results.toString());

//        qexec.close();
//        System.out.println("--2--");
//        System.out.println(results.toString());
        
//        dataset.close();
//        System.out.println("--3--");
//        System.out.println(results.toString());
        
        	return results;
    	}
    	catch(Exception e) {
    		return null;
    	}
//        
//		String resultStr = "";
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		
//		int formatInt = 0;
//		try {
//			formatInt = Integer.parseInt(format);
//		}
//		catch(NumberFormatException e) {
//			formatInt = 0;
//		}
//		
//		switch(formatInt) {
//			case 0:
//			    ResultSetFormatter.out(baos, results);
//			    resultStr += baos.toString();
//		
//			    int firstIndex = 0;
//			    int lastIndex = 0;
//			    String subString = "";
//		
//			    resultStr = resultStr.replace("<", "&lt;");
//			    resultStr = resultStr.replace(">", "&gt;");
//			    resultStr = resultStr.replace("-", " ");
//			    resultStr = resultStr.replace("=", " ");
//			    resultStr = resultStr.replace("\r", "</td></tr>");
//			    resultStr = resultStr.replace("\n", "<tr><td>");
//			    resultStr = resultStr.replace("|</td></tr><tr><td>|", "</td></tr><tr><td>");
//			    resultStr = resultStr.replace("|", "</td><td>");
//			    resultStr = resultStr.trim();
//			    resultStr = "<table>" + resultStr;
//			    resultStr += "</table>";
//			    resultStr = resultStr.replace("<table></td></tr><tr><td></td>", "<table border=\"1\"><tr>");
//			    resultStr = resultStr.replace("</td></tr><tr><td></table>", "</tr></td></table>");
//			    resultStr = resultStr.replace("</td><td></td></tr><tr><td>", " ");
//			    resultStr = resultStr.replace("</td></tr><tr><td></td><td>", "</td></tr><tr><td>");
//			    
		//	    while(resultStr.indexOf("&lt;", lastIndex) >= 0) {
		//	    	if(resultStr.indexOf("&lt;", lastIndex) >= 0) {
		//	    		firstIndex = resultStr.indexOf("&lt;", lastIndex);
		//	    		if(resultStr.indexOf("&gt;",firstIndex) >= 0) {
		//	    			lastIndex = resultStr.indexOf("&gt;",firstIndex);
		//	    			subString = resultStr.substring(firstIndex+4, lastIndex);
		//	    			resultStr = resultStr.replace(subString, "<a href=\"" + subString + "\">" + subString + "</a>");
		//	    			System.out.println(subString);
		//	    		}
		//	    	}
		//	    }
//			    break;
//			case 1:
//				
//		}
//
//        qexec.close();
//        dataset.close();
//
//		return resultStr;
	}
	
	public static String outputHtml(ResultSet results)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ResultSetFormatter.out(baos, results);
	    String resultStr = baos.toString();

	    resultStr = resultStr.replace("<", "&lt;");
	    resultStr = resultStr.replace(">", "&gt;");
	    resultStr = resultStr.replace("-", " ");
	    resultStr = resultStr.replace("=", " ");
	    resultStr = resultStr.replace("\r", "</td></tr>");
	    resultStr = resultStr.replace("\n", "<tr><td>");
	    resultStr = resultStr.replace("|</td></tr><tr><td>|", "</td></tr><tr><td>");
	    resultStr = resultStr.replace("|", "</td><td>");
	    resultStr = resultStr.trim();
	    resultStr = "<table>" + resultStr;
	    resultStr += "</table>";
	    resultStr = resultStr.replace("<table></td></tr><tr><td></td>", "<table border=\"1\"><tr>");
	    resultStr = resultStr.replace("</td></tr><tr><td></table>", "</tr></td></table>");
	    resultStr = resultStr.replace("</td><td></td></tr><tr><td>", " ");
	    resultStr = resultStr.replace("</td></tr><tr><td></td><td>", "</td></tr><tr><td>");
	    
	    return resultStr;
	}
}
