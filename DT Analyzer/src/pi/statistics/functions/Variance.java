package pi.statistics.functions;

import pi.statistics.logic.Function;
import pi.statistics.logic.StatisticResult;

public class Variance extends Function {
    private double x = 0;
    private double y = 0;
    private double var = 0;
    private double avg;
    private double n = 0;

    public Variance() {
	super("Variance");
    }

    @Override
    public void countResult(StatisticResult statResult) {

	if (n != 0) {
	    var = y / (n-1);
	} else {
	    // TODO Co jeœli mianownik (liczba próbek) jest zerem
	}
	statResult.addValue(this.getName(), var);


    }

    @Override
    public void iterate(double value) {
	if (avg != 0) {
	    x = Math.pow((value - avg), 2);
	    y += x;
	    n++;
	}

    }
    
    public void setAverage(StatisticResult statResult){
	this.avg = statResult.getValue().get("Average").doubleValue();
	
    }

    @Override
    public void backToBegin() {
	    x = 0;
	    y = 0;
	    var = 0;
	    n = 0;
	
    }

}
