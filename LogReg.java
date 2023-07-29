import java.util.ArrayList;

public class LogReg {
	
	// Returns an ArrayList containing all possible distributions of weights over the given integers
	public static ArrayList<ArrayList<Integer>> distributeWeights(int remainingWeight, int remainingParams, ArrayList<Integer> listSoFar, ArrayList<ArrayList<Integer>> retList) {
		// Base case
		if(remainingParams == 1) {
			listSoFar.add(remainingWeight);
			retList.add(listSoFar);
			return retList;
		} // if
		
		// Assign each possible weight value to the next param
		for(int i = 0; i <= remainingWeight; i++) {
			ArrayList<Integer> newList = (ArrayList)listSoFar.clone();
			newList.add(i);
			distributeWeights(remainingWeight - i, remainingParams - 1, newList, retList);
		} // for
		return retList;
	} // distributeWeights
	
	// min inclusive, max exclusive
	public static int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	} // getRandomNumber

	public static void main(String[] args) {
		
		int numParams = getRandomNumber(1, 9);
		int totalWeight = 15;
		int numObservations = 100;
		
		// Randomly generate observation classes
		boolean[] observationClasses = new boolean[numObservations];
		double positiveChance = Math.random();
		for(int i = 0; i < positiveChance; i++) {
			observationClasses[i] = (Math.random() < positiveChance);
		} // for
		
		ArrayList<ArrayList<Integer>> parameterObs = new ArrayList<>();
		// Cycle params, exlcuding bias
		for(int i = 1; i < numParams; i++) {
			ArrayList<Integer> positiveObservations = new ArrayList<Integer>();
			
			positiveChance = Math.random();
			// Cycle observations
			for(int j = 0; j < numObservations; j++) {
				if(Math.random() < positiveChance) {
					positiveObservations.add(j);
				} // if
			} // for
			
			// Add the observations for which the parameter was positive
			parameterObs.add(positiveObservations);
		} // for

		// BRUTE FORCE OPTIMIZATION
		// Generate all distributions
		ArrayList<Integer> emptyList = new ArrayList<>();
		ArrayList<ArrayList<Integer>> allDists = new ArrayList<>();
		allDists = distributeWeights(totalWeight, numParams, emptyList, allDists);
	
		// Find optimal distribution
		int largestIndex = 0;
		double bestError = Double.POSITIVE_INFINITY;
		// Cycle all distributions
		for(int i = 0; i < allDists.size(); i++) {
			double total = 0;
			
			// Cycle observations
			for(int j = 0; j < numObservations; j++) {
				// Add bias
				int exponent = allDists.get(i).get(0);
				// Cycle parameters
				for(int k = 0; k < numParams - 1; k++) {
					// If the paramter is positive for the given observation
					if(parameterObs.get(k).contains(j)) {
						exponent += allDists.get(i).get(k + 1);
					} // if
				} // for
				
				// Add error from this observation
				double exponential;
				if(observationClasses[j]) {
					exponential = Math.exp(-exponent);
				} // if
				else {
					exponential = Math.exp(exponent);
				} // else
				
				total += -Math.log(1.0 / (1 + exponential));
				
			} // for
		
			if(total < bestError) {
				bestError = total;
				largestIndex = i;
			} // if
		} // for
	
		System.out.println("Number of parameters: " + numParams);
		System.out.println("Total weight budget: " + totalWeight);
		System.out.println("\n\nOptimal integer weights (calculated via brute force, ignoring negatives):");
		System.out.println(allDists.get(largestIndex));
	} // main

} // class