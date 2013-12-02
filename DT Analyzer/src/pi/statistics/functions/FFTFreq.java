package pi.statistics.functions;

import java.util.ArrayList;

import pi.statistics.logic.StatisticResult;

public class FFTFreq
{
	static StatisticResult result;
	
	static public void init(StatisticResult input, ArrayList <Double> data)
	{
		result = input;
		
		double max = -1000000.0d;
		double freq = 0.0d;
		
		int size = data.size();
		for (int i = 0; i < size - 1; i += 2)
		{
			if (data.get(i + 1) > max)
			{
				max = data.get(i + 1);
				freq = data.get(i);
			}
		}
		
		input.setValue(new ArrayList <Double> (1));
		input.getValue().add(freq);
	}
}
