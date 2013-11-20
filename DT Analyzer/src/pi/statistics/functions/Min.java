package pi.statistics.functions;

import pi.statistics.logic.Function;
import pi.statistics.logic.StatisticResult;

public class Min extends Function {

    private double min = 9999999;
    
    public Min() {
	super("Min");
    }

    @Override
    public void countResult(StatisticResult statResult) {
	statResult.addValue(this.getName(), min);
    }

    @Override
    public void iterate(double value) {
	if (value < min) min = value;
    }

    @Override
    public void backToBegin() {
	 min = 9999999;
	
    }


}
