import java.util.Scanner;
import java.io.File;

public class LinearNaiveBayes {
	
	// Additional "bias" value
	private int numParams;
	private int numObservations;
	
	private int[] positiveExamples;
	private int[] negativeExamples;
	private double[] paramValues;
	
	public double logLikelihood;
	public double avgLL;
	public double accuracy;
	
	public LinearNaiveBayes() {
		numParams = 0;
		numObservations = 0;
		logLikelihood = 0;
	} // constructor
	
	// Set parameters based on data from a given file
	public void trainClassifier(File trainFile) throws Exception{
		
		Scanner fileScanner = new Scanner(trainFile);
		
		boolean firstObs = true;
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] values = line.split(",");
			
			if(firstObs) {
				// Remember, additional bias parameter
				numParams = values.length;
				positiveExamples = new int[numParams];
				negativeExamples = new int[numParams];
				paramValues = new double[numParams];
				firstObs = false;
			} // if
			
			numObservations++;
			
			// Observation is a zero
			if(Integer.parseInt(values[values.length - 1]) == 0) {
				negativeExamples[0]++;
				for(int i = 0; i < values.length - 1; i++) {
					// Param and observation are both 0
					if(Integer.parseInt(values[i]) == 0) {
						positiveExamples[i+1]++;
					} // if
					// Param is 1 and observation is 0
					else {
						negativeExamples[i+1]++;
					} // else
				} // for
			} // if
			
			// Observation is a 1
			else {
				positiveExamples[0]++;
				for(int i = 0; i < values.length - 1; i++) {
					// Param is 0 and observation is 1
					if(Integer.parseInt(values[i]) == 0) {
						negativeExamples[i+1]++;
					} // if
					// Param and observation are both 1
					else {
						positiveExamples[i+1]++;
					} // else
				} // for
			} // else
			
		} // while
		
		fileScanner.close();
		
		for(int i = 0; i < numParams; i++) {
			// add 1 to avoid dividing by zero
			paramValues[i] = Math.log(((positiveExamples[i] * 1.0) + 1) / (negativeExamples[i] + 1));
		} // for
		
		// Compute log likelihood
		for(int i = 0; i < numParams; i++) {
			logLikelihood += positiveExamples[i] * Math.log(sigmoid(paramValues[i]));
			logLikelihood += negativeExamples[i] * Math.log(sigmoid(-paramValues[i]));
		} // for
		avgLL = logLikelihood / numObservations;
		
	} // trainClassifier
	
	private double sigmoid(double num) {
		return 1 / (1 + Math.pow(Math.E, -num));
	} // sigmoid
	
	// Get accuracy of predictions on a given data file
	public String testClassifier(File testFile) throws Exception{
		int total = 0;
		int correct = 0;
		
		Scanner fileScanner = new Scanner(testFile);
		
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] values = line.split(",");
			
			total++;
			
			int prediction = predict(values);
			
			if(prediction == Integer.parseInt(values[values.length - 1])) {
				correct++;
			} // if
		} // while
		
		fileScanner.close();
		
		String ret = "Total classifications made: ";
		ret += total;
		ret += "\nCorrect classifications made: ";
		ret += correct;
		ret += "\nAccuracy: ";
		accuracy = 1.0 * correct / total;
		ret += accuracy;
		return ret;
	} // testClassifier
	
	// Return a prediction for the class of a given observation based on its known values
	public int predict(String[] values) {
		double exponent = paramValues[0];
		for(int i = 0; i < values.length - 1; i++) {
			// Parameter value is 0, but in our case we use -1
			if(Integer.parseInt(values[i]) == 0) {
				exponent -= paramValues[i+1];
			} // if
			// Parameter value is 1
			else {
				exponent += paramValues[i+1];
			} // else
		} // for
		
		// Do this instead of computing sigmoid
		if(exponent >= 0) {
			return 1;
		} // if
		
		return 0;
	} // predict
	
	public void printParamValues() {
		System.out.print("Model parameter values as linear weights: ");
		for(int i = 0; i < paramValues.length; i++) {
			System.out.print(paramValues[i] + ", ");
		} // for
		System.out.println();
	} // printParamValues
	
	public double[] getParams() {
		return paramValues;
	} // getParams

} // class
