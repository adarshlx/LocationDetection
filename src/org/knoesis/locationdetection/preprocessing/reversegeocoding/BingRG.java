package org.knoesis.locationdetection.preprocessing.reversegeocoding;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * Reverse Geocoding using Bing
 * For a given latitude and longitude Bing Maps API returns the location information
 * @author revathy
 *
 */
public class BingRG implements ReverseGeocoding {
		
	static Logger log = Logger.getLogger(BingRG.class.getName());
	
	public String getLocationInfo (double latitude, double longitude) throws Exception {
				String urlName = "http://dev.virtualearth.net/REST/v1/Locations/"+latitude+","+longitude+"?o=json&key=AvN_gngSCe2p5rMrrDjpurId_CzebXSsdO2rG12Ax3cgoRpF8-llRwc5o-SFglVx";
				URL url = new URL(urlName);
				
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.connect();
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = null;			
				String content = "";
					
				while((line=br.readLine()) != null) {
						content += line;
				}
						
				return content;
	}	

}
