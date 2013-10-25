package org.knoesis.locationdetection.preprocessing.model;

/**
 * Field information from Reverse Geocoding APIs
 * @author revathy
 */

public class Location {
		
			String country;
			String state;
			String city;
			String postalCode;
			
			
			public Location (String country, String state, String city, String postalCode) {
					this.country = country;
					this.state = state;
					this.city = city;
					this.postalCode = postalCode;
			}
			
			public String getCountry() {
					return this.country;
			}
			
			public String getState() {
					return this.state;
			}
			
			public String getCity() {
					return this.city;
			}
			
			public String getPostalCode() {
					return this.postalCode;
			}
			
			
}
