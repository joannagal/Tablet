package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class Median
{
	static StatisticResult result;

	static public void init(StatisticResult input, int size)
	{
		result = input;
		input.setValue(new ArrayList<Double>(size));
	}

	static public void iterate(Double value)
	{
		result.getValue().add(value);
		int size = result.getValue().size();
		double tmp = 0.0d;
		for (int i = size - 2; i >= 0; i--)
		{
			tmp = result.getValue().get(i);
			if (tmp > value)
			{
				result.getValue().set(i, value);
				result.getValue().set(i + 1, tmp);
			}
		}
	}

	static public void finish()
	{
		int size = result.getValue().size();
		int pntr = size / 2;
		result.getValue().set(0, result.getValue().get(pntr));
	}

}
