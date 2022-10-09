import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Driver {
	
	// Does not truncate file if it already exists. Must delete between runs
	public static void updateFile(String input, String path) throws IOException{
		byte[] strToBytes = input.getBytes();
		Files.write(Paths.get(path), strToBytes, StandardOpenOption.APPEND, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
	} // updateFile

	public static void main(String[] args) throws Exception{
		
//		DatasetGenerator d = new DatasetGenerator(100, 1000, 1000, 10);
//		try {
//			d.writeFiles();
//		} // try
//		catch(IOException e) {
//			e.printStackTrace();
//		} // catch
//		System.out.println("True avg LL of dataset: " + d.getAvgLL());
//		System.out.println();
		
		System.out.println("Linear Naive Bayes");
		System.out.println("------------------");
		LinearNaiveBayes LNB = new LinearNaiveBayes();
		LNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
		System.out.println(LNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
		System.out.println("Log Likelihood: " + LNB.logLikelihood);
		System.out.println("Average Log Likelihood: " + LNB.avgLL);
		//System.out.println("KL-Divergence: " + d.KLDivergence(LNB.getParams()));
		System.out.println();
		
		updateFile("Weight budget, Fraction width, Average LL, Accuracy\n", "C:\\Users\\lance\\Documents\\GRA Stuff\\results.txt");
		// Cycle weight
		for(int i = 10; i <= 400; i += 10) {
			// Cycle grade
			for(int j = 1; j <= 5; j++) {
				FractionalWBNB classifier = new FractionalWBNB(i, j);
				classifier.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
				classifier.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt"));
				String data = "";
				data += i;
				data += ", ";
				data += j;
				data += ", ";
				data += classifier.avgLL;
				data += ", ";
				data += classifier.accuracy;
	//			data += ", ";
//				data += d.KLDivergence(classifier.getParams());
				data += "\n";
				updateFile(data, "C:\\Users\\lance\\Documents\\GRA Stuff\\results.txt");
				System.out.print(data);
			} // for
		} // for
		
//		DatasetGenerator d = new DatasetGenerator(10, 400, 400);
//		try {
//			d.writeFiles();
//		} // try
//		catch(IOException e) {
//			e.printStackTrace();
//		} // catch
//		d.printParamValues();
//		
//		System.out.println("\nLinear Naive Bayes");
//		System.out.println("------------------");
//		LinearNaiveBayes LNB = new LinearNaiveBayes();
//		LNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		LNB.printParamValues();
//		System.out.println(LNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
//		System.out.println("Log Likelihood: " + LNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + LNB.avgLL);
//		
//		updateFile("Weight budget, Fraction width, Average LL\n", "C:\\Users\\lance\\Documents\\GRA Stuff\\RandomDataSetPerformance2.txt");
//		// Cycle weight
//		for(int i = 2; i <= 20; i += 2) {
//			// Cycle weights by increments of 20
//			for(int j = 1; j <= 50; j++) {
//				FractionalWBNB classifier = new FractionalWBNB(j, i);
//				classifier.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//				String data = "";
//				data += i;
//				data += ", ";
//				data += j;
//				data += ", ";
//				data += classifier.avgLL;
//				//data +=", ";
//				//data += classifier.accuracy;
//				data += "\n";
//				updateFile(data, "C:\\Users\\lance\\Documents\\GRA Stuff\\RandomDataSetPerformance2.txt");
//				System.out.print(data);
//			} // for
//		} // for
		
//		//updateFile("Weight budget, Fraction width, Average LL\n", "C:\\Users\\lance\\Documents\\GRA Stuff\\LargerGrade.txt");
//		// Cycle weight
//		for(int i = 20; i <= 20; i += 10) {
//			// Cycle weights by increments of 20
//			for(int j = 1; j <= 50; j++) {
//				FractionalWBNB classifier = new FractionalWBNB(j, i);
//				classifier.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//				String data = "";
//				data += i;
//				data += ", ";
//				data += j;
//				data += ", ";
//				data += classifier.avgLL;
//				//data +=", ";
//				//data += classifier.accuracy;
//				data += "\n";
//				updateFile(data, "C:\\Users\\lance\\Documents\\GRA Stuff\\LargerGrade.txt");
//				System.out.print(data);
//			} // for
//		} // for
		
//		//updateFile("Weight budget, Fraction width, Average LL\n", "C:\\Users\\lance\\Documents\\GRA Stuff\\0-1Data.txt");
//		// Cycle weight
//		for(int i = 210; i <= 300; i += 10) {
//			// Cycle weights by increments of 20
//			for(int j = 1; j <= 5; j++) {
//				FractionalWBNB classifier = new FractionalWBNB(j, i);
//				classifier.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//				String data = "";
//				data += i;
//				data += ", ";
//				data += j;
//				data += ", ";
//				data += classifier.avgLL;
//				//data +=", ";
//				//data += classifier.accuracy;
//				data += "\n";
//				updateFile(data, "C:\\Users\\lance\\Documents\\GRA Stuff\\0-1Data.txt");
//				System.out.print(data);
//			} // for
//		} // for
		
//		DatasetGenerator d = new DatasetGenerator(10, 400, 400);
//		try {
//			d.writeFiles();
//		} // try
//		catch(IOException e) {
//			e.printStackTrace();
//		} // catch
//		d.printParamValues();
//		
//		System.out.println("\nLinear Naive Bayes");
//		System.out.println("------------------");
//		LinearNaiveBayes LNB = new LinearNaiveBayes();
//		LNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		LNB.printParamValues();
//		System.out.println(LNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
//		System.out.println("Log Likelihood: " + LNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + LNB.avgLL);
//		
//		System.out.println("\nWeight Budgeted Linear Naive Bayes");
//		System.out.println("------------------");
//		WeightBudgetedNB WBNB = new WeightBudgetedNB(10);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		WBNB.printParamValues();
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		System.out.println("Fractional Weight Budgeted Linear Naive Bayes");
//		System.out.println("------------------");
//		FractionalWBNB FWBNB = new FractionalWBNB(10, 1);
//		FWBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		FWBNB.printParamValues();
//		System.out.println(FWBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
//		System.out.println("Log Likelihood: " + FWBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + FWBNB.avgLL);
//		System.out.println();
//		
//		FWBNB = new FractionalWBNB(10, 2);
//		FWBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		FWBNB.printParamValues();
//		System.out.println(FWBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
//		System.out.println("Log Likelihood: " + FWBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + FWBNB.avgLL);
//		System.out.println();
//		
//		FWBNB = new FractionalWBNB(10, 3);
//		FWBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		FWBNB.printParamValues();
//		System.out.println(FWBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
//		System.out.println("Log Likelihood: " + FWBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + FWBNB.avgLL);
//		System.out.println();
		
//		System.out.println("Normal Naive Bayes");
//		System.out.println("------------------");
//		NaiveBayes NB = new NaiveBayes();
//		NB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(NB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println();
//		
//		System.out.println("Linear Naive Bayes");
//		System.out.println("------------------");
//		LinearNaiveBayes LNB = new LinearNaiveBayes();
//		LNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(LNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + LNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + LNB.avgLL);
//		System.out.println();
//		
//		System.out.println("Weight Budgeted Linear Naive Bayes");
//		System.out.println("------------------");
//		WeightBudgetedNB WBNB = new WeightBudgetedNB(50);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		System.out.println("Fractional Weight Budgeted Linear Naive Bayes");
//		System.out.println("------------------");
//		FractionalWBNB FWBNB = new FractionalWBNB(50, 2);
//		FWBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(FWBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + FWBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + FWBNB.avgLL);
//		System.out.println();
//		
//		FWBNB = new FractionalWBNB(50, 3);
//		FWBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(FWBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + FWBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + FWBNB.avgLL);
//		System.out.println();
//		
//		FWBNB = new FractionalWBNB(50, 4);
//		FWBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(FWBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + FWBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + FWBNB.avgLL);
//		System.out.println();
//		
//		FWBNB = new FractionalWBNB(50, 5);
//		FWBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(FWBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + FWBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + FWBNB.avgLL);
//		System.out.println();
		
//		DatasetGenerator d = new DatasetGenerator(10, 400, 400);
//		try {
//			d.writeFiles();
//		} // try
//		catch(IOException e) {
//			e.printStackTrace();
//		} // catch
//		d.printParamValues();
//		
//		System.out.println("\nLinear Naive Bayes");
//		System.out.println("------------------");
//		LinearNaiveBayes LNB = new LinearNaiveBayes();
//		LNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		LNB.printParamValues();
//		System.out.println(LNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
//		System.out.println("Log Likelihood: " + LNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + LNB.avgLL);
//		
//		System.out.println("\nWeight Budgeted Linear Naive Bayes");
//		System.out.println("------------------");
//		WeightBudgetedNB WBNB = new WeightBudgetedNB(200);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		WBNB.printParamValues();
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		System.out.println("\nNormal Naive Bayes");
//		System.out.println("------------------");
//		NaiveBayes NB = new NaiveBayes();
//		NB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\trainfile.txt"));
//		System.out.println(NB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\testfile.txt")));
		
//		System.out.println("Normal Naive Bayes");
//		System.out.println("------------------");
//		NaiveBayes NB = new NaiveBayes();
//		NB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(NB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		
//		System.out.println("\nLinear Naive Bayes");
//		System.out.println("------------------");
//		LinearNaiveBayes LNB = new LinearNaiveBayes();
//		LNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(LNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + LNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + LNB.avgLL);
//		
//		System.out.println("\nWeight Budgeted Linear Naive Bayes");
//		System.out.println("------------------");
//		WeightBudgetedNB WBNB = new WeightBudgetedNB(50);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		WBNB = new WeightBudgetedNB(100);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		WBNB = new WeightBudgetedNB(200);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		WBNB = new WeightBudgetedNB(300);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		WBNB = new WeightBudgetedNB(400);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		WBNB = new WeightBudgetedNB(500);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		WBNB = new WeightBudgetedNB(600);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();
//		
//		WBNB = new WeightBudgetedNB(700);
//		WBNB.trainClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\train-0-1.txt"));
//		System.out.println(WBNB.testClassifier(new File("C:\\Users\\lance\\Documents\\GRA Stuff\\NB HW Assignment\\test-0-1.txt")));
//		System.out.println("Log Likelihood: " + WBNB.logLikelihood);
//		System.out.println("Average Log Likelihood: " + WBNB.avgLL);
//		System.out.println();

	} // main

} // class
