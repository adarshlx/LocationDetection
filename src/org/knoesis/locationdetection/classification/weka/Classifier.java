package org.knoesis.locationdetection.classification.weka;

import java.io.File;

public interface Classifier {
	
		public void trainClassifier(File trainingData) throws Exception;
		public void writeClassifierToDisk() throws Exception;
		public void classifyData(File testData, File labeledFile) throws Exception;		
		public void loadClassifierFromDisk() throws Exception;

}
