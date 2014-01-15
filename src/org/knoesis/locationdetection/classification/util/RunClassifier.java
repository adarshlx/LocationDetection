package org.knoesis.locationdetection.classification.util;

import java.io.File;

import org.knoesis.locationdetection.classification.config.Config;
import org.knoesis.locationdetection.classification.weka.Bayes;
import org.knoesis.locationdetection.classification.weka.Classifier;

public class RunClassifier {
	
	
		public static void runClassifier() throws Exception {
				
				Classifier classifier = new Bayes();	
				classifier.loadClassifierFromDisk();					
				classifier.classifyData(new File(Config.TESTING_DATE), new File(Config.LABELED_FILE_ARFF));					
				WekaOperations.convertArffToCsv(Config.LABELED_FILE_ARFF, Config.LABELED_FILE_CSV);
				
		}
}
