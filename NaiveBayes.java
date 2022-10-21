import java.util.Scanner;
import java.io.File;

public class NaiveBayes {
	
	private int numParams;
	private int numObservations;
	private int numZeros;
	private int[] zerosGivenZeros;
	private int[] zerosGivenOnes;
	
	// probOne = 1 - probZero
	private double probZero;
	
	private double[] probZerosGivenZeros;
	private double[] probZerosGivenOnes;
	
	public double logLikelihood;
	public double accuracy;
	
	public NaiveBayes() {
		numObservations = 0;
		numZeros = 0;
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
				numParams = values.length - 1;
				zerosGivenZeros = new int[numParams];
				zerosGivenOnes = new int[numParams];
				probZerosGivenZeros = new double[numParams];
				probZerosGivenOnes = new double[numParams];
				firstObs = false;
			} // if
			
			numObservations++;
			
			// Observation is a zero
			if(Integer.parseInt(values[values.length - 1]) == 0) {
				numZeros++;
				for(int i = 0; i < values.length - 1; i++) {
					// Observation and parameter are both zeros
					if(Integer.parseInt(values[i]) == 0) {
						zerosGivenZeros[i]++;
					} // if
				} // for
			} // if
			
			// Observation is a 1
			else {
				for(int i = 0; i < values.length - 1; i++) {
					// Observation is 1, parameter is 0
					if(Integer.parseInt(values[i]) == 0) {
						zerosGivenOnes[i]++;
					} // if
				} // for
			} // else
		} // while
		
		fileScanner.close();
		
		// Create probabilities
		probZero = 1.0 * numZeros / numObservations;
		
		// Calculate probabilities for every parameter
		for(int i = 0; i < numParams; i++) {
			probZerosGivenZeros[i] = (1.0 * zerosGivenZeros[i]) / numZeros;
			probZerosGivenOnes[i] = (1.0 * zerosGivenOnes[i]) / (numObservations - numZeros);
		} // for
		
	} // trainClassifier
	
	// Get accuracy of predictions on a given data file
	public String testClassifier(File testFile) throws Exception{
		int total = 0;
		int correct = 0;
		
		Scanner fileScanner = new Scanner(testFile);
		
		while(fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			String[] values = line.split(",");
			
			total++;
			
			// Create a probability for both 0 and 1, starting with priors
			double zero = probZero;
			double one = 1 - probZero;
			for(int i = 0; i < numParams; i++) {
				// Parameter value is 0
				if(Integer.parseInt(values[i]) == 0) {
					zero *= probZerosGivenZeros[i];
					one *= probZerosGivenOnes[i];
				} // if
				// Parameter value is 1
				else {
					zero *= (1 - probZerosGivenZeros[i]);
					one *= (1 - probZerosGivenOnes[i]);
				} // else
				
			} // for
			
			int prediction = 0;
			if(one > zero) {
				prediction = 1;
			} // if
			
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

} // class
