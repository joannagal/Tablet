package pi.statistics.functions;

import pi.statistics.logic.Function;
import pi.statistics.logic.StatisticResult;

public class Amplitude extends Function {
    
    public Amplitude() {
	super("Amplitude");
    }

    @Override
    public void countResult(StatisticResult statResult) {
	//TODO null?
	double min = statResult.getValue().get("Min").doubleValue();
	double max = statResult.getValue().get("Max").doubleValue();
	double amplitude = max - min;
	
	statResult.addValue(this.getName(), amplitude);
    }

    @Override
    public void iterate(double value) {

    }

    @Override
    public void backToBegin() {
	
	
    }

}
