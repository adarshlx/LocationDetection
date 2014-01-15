package org.knoesis.locationdetection.classification.util;

import java.io.File;

import org.knoesis.locationdetection.classification.config.Config;
import org.knoesis.locationdetection.classification.weka.Bayes;
import org.knoesis.locationdetection.classification.weka.Classifier;

public class CreateClassifier {
	/**
	 * Creating a Random Forest Classifier 
	 * The training data is read from data/trainingdata.arff
	 * The classifier is written to : model/RandomForest.model
	 */
	public static void createNBClassifier() throws Exception {
				Classifier classifier = new Bayes();			
				/*1. Train the classifier and write it to memory*/
				System.out.println("Using Training data: " + Config.TRAINING_DATA);
				classifier.trainClassifier(new File(Config.TRAINING_DATA));	
				/*2. Write Classifier to the disk*/
				classifier.writeClassifierToDisk();			
	}	
}
