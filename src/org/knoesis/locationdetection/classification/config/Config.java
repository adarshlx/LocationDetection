package org.knoesis.locationdetection.classification.config;

public class Config {

			public static final String TRAINING_DATA = "data/city/Training_Bengaluru.arff";
			public static final String TESTING_DATE = "data/testdata.arff";
			public static final String UNLABELED_DATA = "data/city/Unlabeled_Bengaluru.arff";
			public static final String DATA_SOURCE = "data/data_source.arff";
			public static final String LABELED_FILE_ARFF = "data/labeled.arff";
			public static final String LABELED_FILE_CSV = "data/labeled.csv";
			public static final String CSV_ACTUAL_LABELS = "data/testingdata_results.csv";
			/** 
			 * Weka FilteredClassifier Options
			 **/
			public static final String[] filteredClassifierOptions = {"-R","first-last","-W","2500","-prune-rate","-1.0","-N","0","-stemmer",
						       "weka.core.stemmers.NullStemmer","-M","1","-tokenizer",
						       "weka.core.tokenizers.WordTokenizer","-delimiters"," \r\n\t.,;:\'\''()?!"};
			
			public static final String NB_CLASSIFIER_PATH = "model/bayes.model";
}
