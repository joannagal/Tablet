package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class Amplitude
{

	static StatisticResult result;

	static public void init(StatisticResult input)
	{
		result = input;
		input.setValue(new ArrayList <Double> (3));
		input.getValue().add(2000000.0d);
		input.getValue().add(1000000.0d);
		input.getValue().add(-1000000.0d);
	}
	
	static public void iterate(Double value)
	{
		if (value < result.getValue().get(1))	
			result.getValue().set(1, value);
		
		if (value > result.getValue().get(2))	
			result.getValue().set(2, value);
		
		double res = result.getValue().get(2) - result.getValue().get(1);
		result.getValue().set(0, res);
	}
	
	static public void finish(Double min, Double max)
	{
		result.getValue().set(0, max - min);
	}
}

