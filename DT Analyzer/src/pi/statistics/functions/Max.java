package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class Max
{

	static StatisticResult result;

	static public void init(StatisticResult input)
	{
		result = input;
		input.setValue(new ArrayList<Double>(1));
		input.getValue().add(-1000000.0d);
	}

	static public void iterate(Double value)
	{
		if (value > result.getValue().get(0))
			result.getValue().set(0, value);
	}

}
