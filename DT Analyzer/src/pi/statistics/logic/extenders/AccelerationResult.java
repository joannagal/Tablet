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
	public void calculateResult()
	{
		this.value = new HashMap<String, StatisticResult>();

		StatisticResult histogramResult = new StatisticResult();
		StatisticResult dependencyResult = new StatisticResult();
		StatisticResult minResult = new StatisticResult();
		StatisticResult maxResult = new StatisticResult();
		StatisticResult amplitudeResult = new StatisticResult();
		StatisticResult avgResult = new StatisticResult();
		StatisticResult fft = new StatisticResult();
		StatisticResult median = new StatisticResult();
		StatisticResult fftFreq = new StatisticResult();

		Min.init(minResult);
		Max.init(maxResult);
		Amplitude.init(amplitudeResult);
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
				Min.iterate(a);
				Max.iterate(a);
				Amplitude.iterate(a);
				Average.iterate(a);
				size++;
			}
		}

		Average.finish();

		Collector.init(histogramResult, size);
		DependencyCollector.init(dependencyResult, size * 2 + 1);

		StatisticResult varianceResult = new StatisticResult();
		Variance.init(varianceResult, avgResult.getValue().get(0));

		Median.init(median, size);

		DependencyCollector.iterate(0.0d, 0.0d);

		for (int i = 0; i < this.velocity.size(); i++)
		{
			ArrayList<Double> data = this.velocity.get(i);
			double dv, dt, a;

			for (int j = 2; j < data.size() - 1; j += 2)
			{
				dt = data.get(j) - data.get(j - 2);
				dv = data.get(j + 1) - data.get(j - 1);
				a = dv / dt;
				Collector.iterate(a);
				DependencyCollector.iterate(data.get(j), a);
				Variance.iterate(a);
				Median.iterate(a);
			}
		}

		Variance.finish();
		Median.finish();

		FFT.init(fft, histogramResult.getValue(), freq);
		FFTFreq.init(fftFreq, fft.getValue());

		StatisticResult standardDevResult = new StatisticResult();
		StandardDev.init(standardDevResult, varianceResult.getValue().get(0));

		this.value.put("Collector", histogramResult);
		this.value.put("Dependency Collector", dependencyResult);
		this.value.put("FFT", fft);
		this.value.put("FFT Freq", fftFreq);
		this.value.put("Min", minResult);
		this.value.put("Max", maxResult);
		this.value.put("Amplitude", amplitudeResult);
		this.value.put("Average", avgResult);
		this.value.put("Variance", varianceResult);
		this.value.put("Median", median);
		this.value.put("StandardDev", standardDevResult);
	}

}
