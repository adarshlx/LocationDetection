package org.knoesis.locationdetection.preprocessing.reversegeocoding;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Reverse Geocoding using Google API 
 * limited to 2500 request per day form a single IP address
 * @author revathy
 */

public class GoogleRG implements ReverseGeocoding {
		
			public String getLocationInfo(double latitude, double longitude) throws Exception {
					String content = "";
					
					String urlName = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," +
											longitude + "&sensor=false";
					URL url = new URL(urlName);
					
					System.out.println(urlName);
					HttpURLConnection connection = (HttpURLConnection)url.openConnection();
					connection.connect();
					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String line = null;							
					while((line=br.readLine()) != null) {
							content += line;
					}					
					
					return content;
			}
}
