package models.query;

import java.util.StringTokenizer;

import models.city.City;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;

public class QueryRunner 
{	
	private static final String SERVICE = "http://dbpedia.org/sparql";
	
	private static final String FIELD1  = "cityAbstract";
	private static final String FIELD2  = "cityLat";
	private static final String FIELD3  = "cityLong";
	private static final String FIELD4  = "cityPopulationTotal";
	private static final String FIELD5  = "countryName";
	private static final String FIELD6  = "currencyCode";
	
	public static boolean exists(String queryString, String cityName)
	{
		String query = String.format(queryString, cityName);
		//System.out.println(query);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(SERVICE, query);
		
		return qexec.execAsk();
	}
	
	public static City execute(String queryString, String cityName)
	{
		City city = new City();
		
		String query = String.format(queryString, cityName);
		//System.out.println(query);
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService(SERVICE, query);
		ResultSet results = qexec.execSelect() ;

		for ( ; results.hasNext() ; )
		{
			QuerySolution qsolution = results.nextSolution() ;
		    
			Literal result = qsolution.getLiteral(FIELD1) ;
		    String cityAbstract = result.getString();	
		    
			result = qsolution.getLiteral(FIELD2) ;
		    String cityLat = result.getString();
		    
			result = qsolution.getLiteral(FIELD3) ;
		    String cityLong = result.getString();		    

			result = qsolution.getLiteral(FIELD4) ;
		    String cityPopulationTotal = result.getString();	
		    
			result = qsolution.getLiteral(FIELD5) ;
		    String countryName = result.getString();
		    
			result = qsolution.getLiteral(FIELD6) ;
		    String currencyCode = result.getString();
		    
		    city.setName(cityName);
		    city.setOverview(cityAbstract);
		    city.setLatitude(cityLat);
		    city.setLogitude(cityLong);
		    city.setPopulationTotal(cityPopulationTotal);
		    city.setCountry(countryName);
		    // Remove additional information on currency
		    if(currencyCode.indexOf(",") >= 0) {
				StringTokenizer st = new StringTokenizer(currencyCode, ",");
				currencyCode = st.nextToken();
			}
		    city.setcurrencyCode(currencyCode);
		}
		
		return  city;
	}	
}

