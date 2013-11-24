package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class DependencyCollector
{

	static StatisticResult result;
	
	static public void init(StatisticResult input, int size)
	{
		result = input;
		input.setValue(new ArrayList <Double> (size));
	}
	
	static public void iterate(Double argument, Double value)
	{
		result.getValue().add(argument);
		result.getValue().add(value);
	}

}
