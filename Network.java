import java.io.File;
import java.util.Scanner;

public class Network {
	
	private int numLayers;
	private int[] layerSizes;
	public FractionalWBNB[][] layers;
	
	public double accuracy;
	
	public Network(FractionalWBNB[] neurons, int[] sizes) {
		
		numLayers = sizes.length;
		
		int index = 0;
		
		layers = new FractionalWBNB[numLayers][];
		layerSizes = new int[numLayers];
		
		for(int i = 0; i < layers.length; i++) {
			// Create size of each layer
			layers[i] = new FractionalWBNB[sizes[i]];
			layerSizes[i] = sizes[i];
			
			// Add neurons to each layer
			for(int j = 0; j < layers[i].length; j++) {
				layers[i][j] = neurons[index];
				index++;
			} // for
		} // for
		
	} // constructor
	
	public Network(int[] sizes) {
		
		numLayers = sizes.length;
		
		layers = new FractionalWBNB[numLayers][];
		layerSizes = new int[numLayers];
		
		for(int i = 0; i < layers.length; i++) {
			// Create size of each layer
			layers[i] = new FractionalWBNB[sizes[i]];
			layerSizes[i] = sizes[i];
		} // for
		
	} // constructor
	
	// Get accuracy of predictions on a given data file
	public String testNetwork(File testFile) throws Exception {
		
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
			
	} // testNetwork
	
	public int predict(String[] values) {
		
		int prediction = 0;
		
		int[] prevLayerOutputs = new int[layerSizes[0]];;
		int[] currentLayerOutputs;
		
		for(int i = 0; i < numLayers; i++) {
			
			// If we're in input layer
			if(i == 0) {
				for(int j = 0; j < prevLayerOutputs.length; j++) {
					// Use normal predict here, last value is a class label
					prevLayerOutputs[j] = layers[i][j].predict(values);
				} // for
				continue;
			} // if
			
			// If we're in output layer
			if(i == numLayers - 1) {
				return layers[i][0].networkPrediction(prevLayerOutputs);
			} // if
			
			// Otherwise we're in some middle layer
			currentLayerOutputs = new int[layerSizes[i]];
			for(int j = 0; j < currentLayerOutputs.length; j++) {
				currentLayerOutputs[j] = layers[i][j].networkPrediction(prevLayerOutputs);
			} // for
			
			// Convert for next iteration
			prevLayerOutputs = currentLayerOutputs;
			
		} // for
		
		return prediction;
		
	} // predict

} // class
