package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class StandardDev
{

	static StatisticResult result;

	static public void init(StatisticResult input, Double avg)
	{
		result = input;
		input.setValue(new ArrayList<Double>(1));
		input.getValue().add(Math.sqrt(avg));
	}

}
