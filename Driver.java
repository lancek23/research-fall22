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
		
		// Create files
		//updateFile("Digit 1, Digit 2, Weight, Grade, Avg LL, Accuracy, Log Reg Accuracy, L2 Error\n", "C:\\Users\\lance\\Documents\\GRA Stuff\\Generated Data\\log-reg-emulation-3.txt");
		
		// Create classifiers
		FractionalWBNB classifier;
		
		// Cycle first digit
		for(int i = 0; i < 9; i++) {
			// Cycle second digit
			for(int j = i + 1; j < 10; j++) {
				
				// Cycle weight
				for(int w = 150; w <= 150; w += 10) {
					// Cycle grade
					for(int g = 6; g < 8; g++) {
						classifier = new FractionalWBNB(w, g);
						classifier.trainClassifierByParameters(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\Python Files\\LogRegParams\\Attempt3\\params-" + i + "-" + j + ".txt"));
						classifier.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\Python Files\\Digit datasets\\binarize-normal\\testing-" + i + "-" + j + ".txt"));
					
//						StringBuilder data2 = new StringBuilder();
//						data2.append(i);
//						data2.append(", ");
//						data2.append(j);
//						data2.append(", ");
//						data2.append(w);
//						data2.append(", ");
//						data2.append(g);
//						data2.append(", ");
//						data2.append(classifier.avgLL);
//						data2.append(", ");
//						data2.append(classifier.accuracy);
//						data2.append(", ");
//						Scanner fileScanner = new Scanner(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\Python Files\\LogRegParams\\Attempt3\\accuracy-" + i + "-" + j + ".txt"));
//						data2.append(fileScanner.next());
//						data2.append(", ");
//						data2.append(classifier.L2Error);
//						data2.append("\n");
//						updateFile(data2.toString(), "C:\\Users\\lance\\Documents\\GRA Stuff\\Generated Data\\log-reg-emulation-3.txt");
//						System.out.print(data2.toString());
						
						updateFile(classifier.getParams(), "C:\\Users\\lance\\Documents\\GRA Stuff\\Generated Data\\Parameters-grade-" + g + "\\approximation-parameters-" + i + "-" + j + ".txt");
						System.out.printf("Grade: %d - (%d, %d)%n", g, i, j);
					} // for
				} // for
			} // for
		} // for
		
//		// Create files
//		updateFile("Digit 1, Digit 2, Weight, Grade, Avg LL, Accuracy\n", "C:\\Users\\lance\\Documents\\GRA Stuff\\Generated Data\\all-digits-with-bottom-row.txt");
//		
//		// Create classifiers
//		FractionalWBNB classifier;
//		
//		// Cycle first digit
//		for(int i = 0; i < 9; i++) {
//			// Cycle second digit
//			for(int j = i + 1; j < 10; j++) {
//				
//				// Cycle weight
//				for(int w = 10; w <= 150; w += 10) {
//					// Cycle grade
//					for(int g = 1; g <= 5; g++) {
//						classifier = new FractionalWBNB(w, g);
//						classifier.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\Arthur-binarized\\balanced\\training-" + i + "-" + j + ".txt"));
//						classifier.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\Arthur-binarized\\balanced\\testing-" + i + "-" + j + ".txt"));
//						StringBuilder data2 = new StringBuilder();
//						data2.append(i);
//						data2.append(", ");
//						data2.append(j);
//						data2.append(", ");
//						data2.append(w);
//						data2.append(", ");
//						data2.append(g);
//						data2.append(", ");
//						data2.append(classifier.avgLL);
//						data2.append(", ");
//						data2.append(classifier.accuracy);
//						data2.append("\n");
//						updateFile(data2.toString(), "C:\\Users\\lance\\Documents\\GRA Stuff\\Generated Data\\all-digits-with-bottom-row.txt");
//						System.out.print(data2.toString());
//					} // for
//				} // for
//				
//			} // for
//		} // for
		
	} // main

} // class
