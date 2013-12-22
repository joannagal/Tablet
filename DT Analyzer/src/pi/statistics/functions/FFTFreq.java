package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class FFTFreq
{
	static StatisticResult result;

	static public void init(StatisticResult input, ArrayList<Double> data)
	{
		result = input;

		double max[] =
		{ -1000000.0d, -1000000.0d };
		double freq[] =
		{ 0.0d, 0.0d };

		int size = data.size();
		for (int i = 0; i < size - 1; i += 2)
		{
			if (data.get(i + 1) > max[0])
			{
				max[1] = max[0];
				max[0] = data.get(i + 1);
				freq[1] = freq[0];
				freq[0] = data.get(i);
			} else if (data.get(i + 1) > max[1])
			{
				max[1] = data.get(i + 1);
				freq[1] = data.get(i);
			}
		}

		if ((freq[0] < 1.000001d) && (freq[1] < 10.0d))
		{
			if (max[1] > 0.5d * max[0])
				freq[0] = freq[1];
		}

		input.setValue(new ArrayList<Double>(1));
		input.getValue().add(freq[0]);
	}
}
