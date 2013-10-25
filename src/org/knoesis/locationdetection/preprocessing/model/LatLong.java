package org.knoesis.locationdetection.preprocessing.model;

public class LatLong {
			
		double latitude;
		double longitude;
		
		public LatLong(double latitude, double longitude) {
				this.latitude = latitude;
				this.longitude = longitude;
		}
		
		public double getLatitude() {
				return this.latitude;
		}
		
		public double getLongitude() {
				return this.longitude;
		}	
		
}