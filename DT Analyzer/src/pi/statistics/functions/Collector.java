package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class Collector
{
	static StatisticResult result;
	
	static public void init(StatisticResult input, int size)
	{
		result = input;
		input.setValue(new ArrayList <Double> (size));
	}
	
	static public void iterate(Double value)
	{
		result.getValue().add(value);
	}
}
