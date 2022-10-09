// INTEGER WEIGHT PARAMETERS
public class Parameter {
	
	private String name;
	private int a;
	private int z;
	private int weight;
	private boolean positive;
	
	public Parameter(String name, int numPositive, int numNegative) {
		this.name = name;
		a = numPositive;
		z = numNegative;
		weight = 0;
		positive = (a >= z);
	} // constructor
	
	public void incrementWeight() {
		weight++;
	} // incrementWeight
	
	public void setWeight(int w) {
		weight = w;
	} // setWeight
	
	public int getWeight() {
		return weight;
	} // getWeight
	
	public double trueOptimum() {
		return Math.log(a*1.0/z);
	} // trueOptimum
	
	public double marginalReturn() {
		return logLikelihood(weight + 1) - logLikelihood(weight);
	} // marginalReturn
	
	public double logLikelihood(int paramWeight) {
		// if a >= z we will want to assign a positive weight
		if (a >= z) {
			return a * Math.log(sigmoid(paramWeight)) + z * Math.log(sigmoid(-paramWeight));
		} // if
		
		// otherwise, we assign a negative weight so the signs are "flipped"
		return a * Math.log(sigmoid(-paramWeight)) + z * Math.log(sigmoid(paramWeight));
	} // logLikelihood
	
	public double logLikelihood(double paramWeight) {
		// if a >= z we will want to assign a positive weight
		if (a >= z) {
			return a * Math.log(sigmoid(paramWeight)) + z * Math.log(sigmoid(-paramWeight));
		} // if
		
		// otherwise, we assign a negative weight so the signs are "flipped"
		return a * Math.log(sigmoid(-paramWeight)) + z * Math.log(sigmoid(paramWeight));
	} // logLikelihood
	
	// What was I using this for?
//	public double avgLL(int paramWeight) {
//		return logLikelihood(paramWeight) / (a + z);
//	} // avgLL
	
	private double sigmoid(int num) {
		return 1 / (1 + Math.pow(Math.E, -num));
	} // sigmoid
	
	private double sigmoid(double num) {
		return 1 / (1 + Math.pow(Math.E, -num));
	} // sigmoid
	
	public String getName() {
		return name;
	} // getName
	
	public boolean isPositive() {
		return positive;
	} // isPositive
	
	public String toString() {
		String ret = this.name;
		ret += ", Positive examples: ";
		ret += a;
		ret += ", Negative examples: ";
		ret += z;
		ret += ", Positive sign: ";
		ret += positive;
		return ret;
	} // toString

} // class
