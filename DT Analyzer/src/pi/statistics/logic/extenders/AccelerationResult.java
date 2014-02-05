package pi.statistics.logic.extenders;

import java.util.ArrayList;
import java.util.HashMap;

import pi.statistics.functions.Amplitude;
import pi.statistics.functions.Average;
import pi.statistics.functions.Collector;
import pi.statistics.functions.DependencyCollector;
import pi.statistics.functions.FFT;
import pi.statistics.functions.FFTFreq;
import pi.statistics.functions.Max;
import pi.statistics.functions.Median;
import pi.statistics.functions.Min;
import pi.statistics.functions.StandardDev;
import pi.statistics.functions.Variance;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.StatMapper;
import pi.statistics.logic.StatisticResult;

public class AccelerationResult extends AttributeResult
{
	private ArrayList<ArrayList<Double>> velocity;
	private double freq = 1.0d / 100.0d;

	public AccelerationResult(ArrayList<ArrayList<Double>> velocity, double freq)
	{
		this.velocity = velocity;
		this.freq = freq;
	}

	@Override
	public void calculateResult(boolean projectLevel)
	{
		this.value = new HashMap<String, StatisticResult>();

		boolean[] avaible = new boolean[StatMapper.statisticNames.length];
		for (int i = 0; i < avaible.length; i++)
		{
			if (!projectLevel)
			{
				avaible[i] = true;
			} else
			{
				avaible[i] = StatMapper.statisticAvaible
						.get(StatMapper.statisticNames[i]);
			}
		}

		StatisticResult histogramResult = new StatisticResult();
		StatisticResult dependencyResult = new StatisticResult();
		StatisticResult minResult = new StatisticResult();
		StatisticResult maxResult = new StatisticResult();
		StatisticResult amplitudeResult = new StatisticResult();
		StatisticResult avgResult = new StatisticResult();
		StatisticResult fft = new StatisticResult();
		StatisticResult median = new StatisticResult();
		StatisticResult fftFreq = new StatisticResult();
		StatisticResult varianceResult = new StatisticResult();
		StatisticResult standardDevResult = new StatisticResult();

		if (avaible[4])
			Min.init(minResult);
		if (avaible[5])
			Max.init(maxResult);
		if (avaible[6])
			Amplitude.init(amplitudeResult);
		if (avaible[7])
			Average.init(avgResult);

		int size = 0;

		for (int i = 0; i < this.velocity.size(); i++)
		{
			ArrayList<Double> data = this.velocity.get(i);
			double dv, dt, a;
			for (int j = 2; j < data.size() - 1; j += 2)
			{
				dt = data.get(j) - data.get(j - 2);
				dv = data.get(j + 1) - data.get(j - 1);
				a = dv / dt;
				if (avaible[4])
					Min.iterate(a);
				if (avaible[5])
					Max.iterate(a);
				if (avaible[7])
					Average.iterate(a);
				size++;
			}
		}

		if (size == 0)
			return;

		if (avaible[4])
			Amplitude.finish(minResult.getValue().get(0), maxResult.getValue()
					.get(0));
		if (avaible[5])
			Average.finish();
		if (avaible[6])
			Median.init(median, size);
		if (avaible[7])
			Variance.init(varianceResult, avgResult.getValue().get(0));

		if (avaible[11])
			Collector.init(histogramResult, size);

		if (!projectLevel)
		{
			DependencyCollector.init(dependencyResult, size * 2 + 2);
			DependencyCollector.iterate(0.0d, 0.0d);
		}

		for (int i = 0; i < this.velocity.size(); i++)
		{
			ArrayList<Double> data = this.velocity.get(i);
			double dv, dt, a;

			for (int j = 2; j < data.size() - 1; j += 2)
			{
				dt = data.get(j) - data.get(j - 2);
				dv = data.get(j + 1) - data.get(j - 1);
				a = dv / dt;

				if (avaible[11])
					Collector.iterate(a);

				if (!projectLevel)
					DependencyCollector.iterate(data.get(j), a);

				if (avaible[8])
					Median.iterate(a);
				if (avaible[9])
					Variance.iterate(a);
			}
		}

		if (avaible[8])
			Median.finish();
		if (avaible[9])
			Variance.finish();
		if (avaible[10])
			StandardDev.init(standardDevResult, varianceResult.getValue()
					.get(0));

		if (avaible[11])
		{
			this.value.put("Collector", histogramResult);
			FFT.init(fft, histogramResult.getValue(), freq);
			FFTFreq.init(fftFreq, fft.getValue());
		}

		if (!projectLevel)
			this.value.put("Dependency Collector", dependencyResult);

		if (avaible[4])
			this.value.put("Min", minResult);
		if (avaible[5])
			this.value.put("Max", maxResult);
		if (avaible[6])
			this.value.put("Amplitude", amplitudeResult);
		if (avaible[7])
			this.value.put("Average", avgResult);
		if (avaible[8])
			this.value.put("Median", median);
		if (avaible[9])
			this.value.put("Variance", varianceResult);
		if (avaible[10])
			this.value.put("StandardDev", standardDevResult);
		if (avaible[11])
			this.value.put("FFT", fft);
		if (avaible[11])
			this.value.put("FFT Freq", fftFreq);
	}

}
