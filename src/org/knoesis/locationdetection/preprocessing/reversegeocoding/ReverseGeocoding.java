package org.knoesis.locationdetection.preprocessing.reversegeocoding;

/**
 * Interface for geocoding
 * @author revathy
 *
 */
public interface ReverseGeocoding {

			public String getLocationInfo(double latitude, double longitude) throws Exception;
			
}
