import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class Driver {
	
	// Does not truncate file if it already exists. Must delete between runs
	public static void updateFile(String input, String path) throws IOException{
		byte[] strToBytes = input.getBytes();
		Files.write(Paths.get(path), strToBytes, StandardOpenOption.APPEND, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	} // updateFile

	public static void main(String[] args) throws Exception{
		
		// Cycle first digit
		for(int i = 0; i <= 8; i++) {
			// Cycle second digit
			for(int j = i + 1; j <= 9; j++) {
				
				int w = 100;
				int g = 5;
				
				System.out.printf("Testing %d vs %d with weight %d and grade %d%n", i, j, w, g);
				
				// Get existing original parameters and approximated parameters
				String dataPath = "C:/Users/lance/Documents/GRA Stuff/Network/Data/Attempt9/network-" + i + "-" + j + "/";
				String budget = "Budget: " + w;
				String grade = "Grade: " + g;
				
				// Create network
				int[] layerSizes = {2, 1};
				Network neuralNetwork = new Network(layerSizes);
				
				try {
					Scanner fs = new Scanner(new File(dataPath + "approximation.txt"));
					
					while(fs.hasNextLine()) {
						if(fs.nextLine().equals(budget) && fs.nextLine().equals(grade)) {
							
							// Move through accuracy and open line
							fs.nextLine();
							fs.nextLine();
							
							// Cycle hidden neurons
							for(int n = 0; n < layerSizes[0]; n++) {
								// Get hidden neuron weights (ignoring title line and blank following line)
								fs.nextLine();
								String hidden = fs.nextLine();
								String[] hiddenArr = hidden.split(", ");
								fs.nextLine();
								
								System.out.printf("Building hidden neuron %d%n", n);
								neuralNetwork.layers[0][n] = new FractionalWBNB(w, g);
								neuralNetwork.layers[0][n].setParameters(new File(dataPath + "hidden-neuron-" + n + ".txt"), hiddenArr);
							} // for
							
							// Get output neuron weights (ignoring title line)
							fs.nextLine();
							String output = fs.nextLine();
							String[] outputArr = output.split(", ");
							System.out.println("Building output neuron");
							neuralNetwork.layers[1][0] = new FractionalWBNB(w, g);
							neuralNetwork.layers[1][0].setParameters(new File(dataPath + "output-neuron-0.txt"), outputArr);
							
						} // if
					} // while
					
				} // try
				catch(Exception e) {
					System.out.println("D^:");
					e.printStackTrace();
				} // catch
				
				// Testing network
				System.out.println("Testing network...");
				String results = neuralNetwork.testNetwork(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\Python Files\\Digit datasets\\binarize-normal\\testing-" + i + "-" + j + ".txt"));
				System.out.println(results);
				
				// Calculating average error across network
				double totalError = 0;
				int totalParams = 0;
				for(int row = 0; row < neuralNetwork.layers.length; row++) {
					for(int col = 0; col < neuralNetwork.layers[row].length; col++) {
						totalError += neuralNetwork.layers[row][col].L2Error;
						totalParams += neuralNetwork.layers[row][col].getNumParams();
					} // for
				} // for
				System.out.println("Average error: " + (totalError / totalParams));

			} // for

		} // for
		
	} // main

} // class
