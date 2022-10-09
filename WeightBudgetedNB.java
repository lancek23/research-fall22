import java.util.Scanner;
import java.io.File;

public class WeightBudgetedNB {
	
	private int budget;
	
	// Additional "bias" value
	private int numParams;
	private int numObservations;
	
	private int[] positiveExamples;
	private int[] negativeExamples;
	private int[] paramValues;
	
	public double logLikelihood;
	public double avgLL;
	public double accuracy;
	
	public WeightBudgetedNB(int budg) {
		numParams = 0;
		numObservations = 0;
		budget = budg;
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
				numParams = values.length;
				positiveExamples = new int[numParams];
				negativeExamples = new int[numParams];
				paramValues = new int[numParams];
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
		
		assignWeights();
		
		// Compute log likelihood
		for(int i = 0; i < numParams; i++) {
			logLikelihood += positiveExamples[i] * Math.log(sigmoid(paramValues[i]));
			logLikelihood += negativeExamples[i] * Math.log(sigmoid(-paramValues[i]));
		} // for
		avgLL = logLikelihood / numObservations;
		
	} // trainClassifier
	
	private double sigmoid(int num) {
		double ret = 1 / (1 + Math.pow(Math.E, -num));
		if(ret == Double.POSITIVE_INFINITY || ret == Double.NEGATIVE_INFINITY ||  Double.compare(-0.0f, ret) == 0 || Double.compare(+0.0f, ret) == 0) {
			System.out.println("Overflow");
		} // if
		return ret;
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
		ret += "\nWeight budged used: ";
		ret += budget;
		return ret;
	} // testClassifier
	
	// Return a prediction for the class of a given observation based on its known values
	public int predict(String[] values) {
		int exponent = paramValues[0];
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
	
	// EVERYTHING FROM HERE IS FROM Dynamic2.java
	
	private Parameter[] params;
	private Cell[][] table;
	
//	public void printTableBottomRow() {
//		System.out.println("Final row of table");
//		for(int i = 0; i < table[table.length - 1].length; i++) {
//			System.out.print("Weight: " + i);
//			System.out.print(", LL: " + table[table.length - 1][i].logLoss);
//			System.out.println(", Avg LL: " + (table[table.length - 1][i].logLoss / numObservations));
//		} // for
//	} // printTableBottomRow
	
	class Cell {
		int[] weights;
		double logLoss;
		
		public Cell(int numParams, double ll) {
			weights = new int[numParams];
			for(int i = 0; i < weights.length; i++) {
				weights[i] = 0;
			} // for
			logLoss = ll;
		} // constructor
		
		public String toString() {
			String ret = "Log Loss:\t";
			ret += logLoss;
			ret += "\nWeights:\t";
			for(int i = 0; i < weights.length; i++) {
				if(!params[i].isPositive() && weights[i] != 0) {
					ret += "-";
				} // if
				ret += weights[i];
				ret += " ";
			} // for
			
			return ret;
		} // toString
		
	} // Cell class
	
	// Assign integer weights
	public void assignWeights() {
		
		// Create parameters
		params = new Parameter[numParams];
		for(int i = 0; i < params.length; i++) {
			params[i] = new Parameter("w_" + i, positiveExamples[i], negativeExamples[i]);
		} // for
		
		// Create table
		table = new Cell[numParams][budget+1];
		
		// PERFORM DYNAMIC PROGRAMMING
		Cell c = generateCellRecursively(numParams - 1, budget);
		
		// Let's check final row to see if we should use less weight
		for(int i = 0; i < table[table.length - 1].length - 1; i++) {
			// The final row never gets generated so we have to make it
			table[table.length - 1][i] = generateCellRecursively(table.length - 1, i);
			if(table[table.length - 1][i].logLoss > c.logLoss) {
				c = table[table.length - 1][i];
			} // if
		} // for
		
		for(int i = 0; i < numParams; i++) {
			paramValues[i] = c.weights[i];
			// The cell stores the absolute value of the weight
			if(!params[i].isPositive()) {
				paramValues[i] *= -1;
			} // if
		} // for
		
	} // assignWeights
	
	// TIME COMPLEXITY: O(nk^2)
	public Cell generateCellRecursively(int row, int col) {
		// Check if calculation has already been done
		if(table[row][col] != null) {
			return table[row][col];
		} // if
		
		// Base case
		if(row == 0) {
			Cell c = new Cell(1, params[0].logLikelihood(col));
			c.weights[0] = col;
			return c;
		} // if
		if(col == 0) {
			double initialLoss = params[0].logLikelihood(0);
			Cell c = new Cell(row+1, (initialLoss * (row+1)));
			return c;
		} // if
		
		// Find largest 
		double largestTotal = Double.NEGATIVE_INFINITY;
		int largestIndex = -1;
		for(int i = 0; i <= col; i++) {
			Cell temp = generateCellRecursively(row - 1, i);
			if(temp.logLoss + params[row].logLikelihood(col - i) > largestTotal) {
				largestTotal = temp.logLoss + params[row].logLikelihood(col - i);
				largestIndex = i;
			} // if
		} // for
		
		// Now, get that largest Cell
		Cell prev = generateCellRecursively(row - 1, largestIndex);
		
		// Generate the new Cell based on the old one, but adding the new parameter and needed weight
		Cell ret = new Cell(prev.weights.length + 1, prev.logLoss + params[row].logLikelihood(col - largestIndex));
		for(int i = 0; i < prev.weights.length; i++) {
			ret.weights[i] = prev.weights[i];
		} // for
		ret.weights[ret.weights.length - 1] = col - largestIndex;
		
		// Set table value so this no longer has to be recalculated
		table[row][col] = ret;
		
		return ret;
		
	} // generateCellRecursively
	
	public void printParamValues() {
		System.out.print("Model parameter values as linear weights: ");
		for(int i = 0; i < paramValues.length; i++) {
			System.out.print(paramValues[i] + ", ");
		} // for
		System.out.println();
	} // printParamValues

} // class
