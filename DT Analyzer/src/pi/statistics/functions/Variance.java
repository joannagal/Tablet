package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class Variance
{
	static StatisticResult result;

	static public void init(StatisticResult input, Double avg)
	{
		result = input;
		input.setValue(new ArrayList<Double>(3));
		input.getValue().add(0.0d);
		input.getValue().add(0.0d);
		input.getValue().add(avg);
	}

	static public void iterate(Double value)
	{
		double tmp = value - result.getValue().get(2);
		double accu = result.getValue().get(0);
		accu += (tmp * tmp);
		result.getValue().set(0, accu);

		tmp = result.getValue().get(1) + 1.0d;
		result.getValue().set(1, tmp);
	}

	static public void finish()
	{
		double res = result.getValue().get(0) / result.getValue().get(1);
		result.getValue().set(0, res);
	}
}
