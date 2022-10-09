import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class DatasetGenerator {
	
	private int numInputs;
	private int numTraining;
	private int numTest;
	
	// First one is bias parameter
	private double[] paramVals;
	// This stores the parameters as a weight for a linear NB classifier
	public double[] paramsAsWeights;
	
	// Data to be written to file
	private String trainData;
	private String testData;
	
	// If this is set to 0, weights can be anything
	private int grade;
	
	// Log Likelihood of test data only
	private double trueLL;
	private double trueAvgLL;
	
	public DatasetGenerator(int inputs) {
		numInputs = inputs;
		numTraining = 100;
		numTest = 100;
		// Add bias parameter
		paramVals = new double[numInputs + 1];
		paramsAsWeights = new double[numInputs + 1];
		trainData = "";
		testData = "";
		grade = 0;
		trueLL = 0;
	} // constructor
	
	public DatasetGenerator(int inputs, int train, int test) {
		numInputs = inputs;
		numTraining = train;
		numTest = test;
		paramVals = new double[numInputs + 1];
		paramsAsWeights = new double[numInputs + 1];
		trainData = "";
		testData = "";
		grade = 0;
		trueLL = 0;
	} // constructor
	
	public DatasetGenerator(int inputs, int train, int test, int g) {
		numInputs = inputs;
		numTraining = train;
		numTest = test;
		paramVals = new double[numInputs + 1];
		paramsAsWeights = new double[numInputs + 1];
		trainData = "";
		testData = "";
		grade = g;
		trueLL = 0;
	} // constructor
	
	private void generateData() {
		// Randomly generate parameter values
		for(int i = 0; i < paramVals.length; i++) {
			paramVals[i] = Math.random();
			paramsAsWeights[i] = -Math.log((1 / paramVals[i]) - 1);
		} // for
		
		// If grade is not 0, we need to re-adjust weights to reflect that grade
		if(grade != 0) {
			adjustGrades();
		} // if
		
		// Generate train data
		for(int i = 0; i < numTraining; i++) {
			// Generate class of observation
			int classification = Math.random() < paramVals[0] ? 1 : 0;
			// Generate each input value
			for(int j = 1; j < paramVals.length; j++) {
				int value = Math.random() < paramVals[j] ? classification : (1 - classification);
				trainData += value + ",";
			} // for
			trainData += classification + "\n";
		} // for
		
		// Generate test data
		for(int i = 0; i < numTest; i++) {
			// Generate class of observation
			int classification = Math.random() < paramVals[0] ? 1 : 0;
			// Adjust LL accordingly
			if(classification == 1) {
				trueLL += Math.log(paramVals[0]);
			} // if
			else {
				trueLL += Math.log(1 - paramVals[0]);
			} // else
			
			// Generate each input value
			for(int j = 1; j < paramVals.length; j++) {
				int value = Math.random() < paramVals[j] ? classification : (1 - classification);
				testData += value + ",";
				// Adjust LL accordingly
				if(value == classification) {
					trueLL += Math.log(paramVals[j]);
				} // if
				else {
					trueLL += Math.log(paramVals[j]);
				} // else
			} // for
			testData += classification + "\n";
		} // for
		
		// Calculate avg LL
		trueAvgLL = (1.0 * trueLL) / numTest;
		
	} // generateData
	
	private void adjustGrades() {
		
		// We need to figure out the nearest number with the correct grade
		for(int i = 0; i < paramVals.length; i++) {
			double interval = 1.0 / grade;
			double nearest = 0;
			while(nearest < Math.abs(paramsAsWeights[i])) {
				nearest += interval;
			} // while
			
			// Determine whether to round up or down
			if(Math.abs(paramsAsWeights[i]) - (nearest - interval) < nearest - Math.abs(paramsAsWeights[i])) {
				nearest -= interval;
			} // if
			
			// Determine if it's negative
			if(paramsAsWeights[i] < 0) {
				nearest *= -1;
			} // if
			
			// Adjust weights and probabilities accordingly
			paramsAsWeights[i] = nearest;
			paramVals[i] = 1.0 / (1 + Math.pow(Math.E, -paramsAsWeights[i]));
		} // for
	} // adjustGrades
	
	public void printParamValues() {
		System.out.print("True parameter values as probabilities: ");
		for(int i = 0; i < paramVals.length; i++) {
			System.out.print(paramVals[i] + ", ");
		} // for
		System.out.println();
		
		System.out.print("True parameter values as linear weights: ");
		for(int i = 0; i < paramVals.length; i++) {
			System.out.print(paramsAsWeights[i] + ", ");
		} // for
		System.out.println();
	} // printParamValues
	
	// Overwrites whatever is currently there
	public void writeFiles() throws IOException{
		generateData();
		
		byte[] strToBytes = trainData.getBytes();
		Files.write(Paths.get("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"), strToBytes);
		
		byte[] strToBytes2 = testData.getBytes();
		Files.write(Paths.get("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt"), strToBytes2);
		
		// Let's also write the parameters
		String probParams = "";
		String linearParams = "";
		for(int i = 0; i < paramVals.length; i++) {
			probParams += paramVals[i] + ", ";
			linearParams += paramsAsWeights[i] + ", ";
		} // for
		
		byte[] strToBytes3 = probParams.getBytes();
		Files.write(Paths.get("C:\\Users\\lance\\Documents\\GRA Stuff\\probability-parameters.txt"), strToBytes3);
		
		byte[] strToBytes4 = linearParams.getBytes();
		Files.write(Paths.get("C:\\Users\\lance\\Documents\\GRA Stuff\\linear-parameters.txt"), strToBytes4);
		
	} // writeFiles
	
	public double getAvgLL() {
		return trueAvgLL;
	} // getAvgLL
	
	// Using a set of linear parameters as weights, this function converts them to probabilities
	// and calculates the KL-Divergence between the two probability distributions
	// Since this one is the true distribution, we can see how far off the other is
	public double KLDivergence(double[] modelParams) {
		if(modelParams.length != paramVals.length) {
			System.out.println("Something went wrong");
			return Double.POSITIVE_INFINITY;
		} // if
		
		double divergence = 0;
		modelParams[0] = 1.0 / (1 + Math.pow(Math.E, -modelParams[0]));
		if(paramVals[0] != 0.0) {
			divergence += paramVals[0] * (Math.log(paramVals[0]) - Math.log(modelParams[0]));
		} // if
		if(paramVals[0] != 1.0) {
			divergence += (1 - paramVals[0]) * (Math.log(1 - paramVals[0]) - Math.log(1 - modelParams[0]));
		} // if
		
		// Cycle parameters
		for(int i = 1; i < modelParams.length; i++) {
			// Convert linear weight to a probability
			modelParams[i] = 1.0 / (1 + Math.pow(Math.E, -modelParams[i]));
			
			// Calculate contribution to divergence
			if(paramVals[i] != 0.0) {
				// P(X=+1 | Y=+1)
				divergence += paramVals[0] * paramVals[i] * (Math.log(paramVals[i]) - Math.log(modelParams[i]));
				// P(X=-1 | Y=-1)
				divergence += (1 - paramVals[0]) * paramVals[i] * (Math.log(paramVals[i]) - Math.log(modelParams[i]));
			} // if
			if(paramVals[i] != 1.0) {
				// P(X=+1 | Y=-1)
				divergence += (1 - paramVals[0]) * (1 - paramVals[i]) * (Math.log(1 - paramVals[i]) - Math.log(1 - modelParams[i]));
				// P(X=-1 | Y=+1)
				divergence += paramVals[0] * (1 - paramVals[i]) * (Math.log(1 - paramVals[i]) - Math.log(1 - modelParams[i]));
			} // if
		} // for
		
		return divergence;
	} // KLDivergence
	
	// Used to create new files of different input sizes without resetting parameters
	public void writeFilesWithDifferentSize(int train, int test) throws IOException{
		// Reset data and LL
		trainData = "";
		testData = "";
		trueLL = 0;
		// Generate train data
		for(int i = 0; i < train; i++) {
			// Generate class of observation
			int classification = Math.random() < paramVals[0] ? 1 : 0;
			// Generate each input value
			for(int j = 1; j < paramVals.length; j++) {
				int value = Math.random() < paramVals[j] ? classification : (1 - classification);
				trainData += value + ",";
			} // for
			trainData += classification + "\n";
		} // for
		
		// Generate test data
		for(int i = 0; i < test; i++) {
			// Generate class of observation
			int classification = Math.random() < paramVals[0] ? 1 : 0;
			// Adjust LL accordingly
			if(classification == 1) {
				trueLL += Math.log(paramVals[0]);
			} // if
			else {
				trueLL += Math.log(1 - paramVals[0]);
			} // else
			
			// Generate each input value
			for(int j = 1; j < paramVals.length; j++) {
				int value = Math.random() < paramVals[j] ? classification : (1 - classification);
				testData += value + ",";
				// Adjust LL accordingly
				if(value == classification) {
					trueLL += Math.log(paramVals[j]);
				} // if
				else {
					trueLL += Math.log(paramVals[j]);
				} // else
			} // for
			testData += classification + "\n";
		} // for
		
		// Calculate avg LL
		trueAvgLL = (1.0 * trueLL) / test;
		
		byte[] strToBytes = trainData.getBytes();
		Files.write(Paths.get("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"), strToBytes);
		
		byte[] strToBytes2 = testData.getBytes();
		Files.write(Paths.get("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt"), strToBytes2);
		
	} // writeFilesWithDifferentSize

//	public static void main(String[] args) {
//		
//		DatasetGenerator d = new DatasetGenerator(4, 10, 10, 2);
//		try {
//			d.writeFiles();
//		} // try
//		catch(IOException e) {
//			e.printStackTrace();
//		} // catch
//		
//		d.printParamValues();
//		
//		try {
//			d.writeFilesWithDifferentSize(5, 5);
//		} // try
//		catch(IOException e) {
//			e.printStackTrace();
//		} // catch
//
//	} // main

} // class
