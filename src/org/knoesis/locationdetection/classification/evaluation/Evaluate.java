package org.knoesis.locationdetection.classification.evaluation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.knoesis.locationdetection.classification.config.Config;
import org.knoesis.locationdetection.classification.model.Tweet;


public class Evaluate {

	
		private static List<Tweet> readFile(String fileName) throws Exception {
			
				File file = new File(fileName);
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line = null;
				List<Tweet> labeledData = new ArrayList<Tweet>();
				
				while((line = br.readLine()) != null) {
						String[] fields = line.split(",");
						Tweet t = new Tweet(fields[0], fields[1]);
						labeledData.add(t);
				}
				br.close();
				
				return labeledData;
		}
}
