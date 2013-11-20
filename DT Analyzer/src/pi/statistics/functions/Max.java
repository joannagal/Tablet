package pi.statistics.functions;

import pi.statistics.logic.Function;
import pi.statistics.logic.StatisticResult;

public class Max extends Function {

    private double max = -999999;
    
    public Max() {
	super("Max");
    }

    @Override
    public void countResult(StatisticResult statResult) {
	statResult.addValue(this.getName(), max);
	
    }

    @Override
    public void iterate(double value) {
	if (value > max) max = value;
    }

    @Override
    public void backToBegin() {
	max = -999999;
	
    }

}
