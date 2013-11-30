package pi.statistics.logic.extenders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.statistics.functions.Amplitude;
import pi.statistics.functions.Average;
import pi.statistics.functions.Collector;
import pi.statistics.functions.DependencyCollector;
import pi.statistics.functions.FFT;
import pi.statistics.functions.Max;
import pi.statistics.functions.Min;
import pi.statistics.functions.StandardDev;
import pi.statistics.functions.Variance;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.StatisticResult;

public class Acceleration extends AttributeResult
{
	private ArrayList<ArrayList <Double> > velocity;

	public Acceleration(ArrayList<ArrayList <Double> > velocity)
	{
		this.velocity = velocity;
	}

	@Override
	public void calculateResult()
	{
		StatisticResult collectorResult = new StatisticResult();
		StatisticResult dCollectorResult = new StatisticResult();
		StatisticResult minResult = new StatisticResult();
		StatisticResult maxResult = new StatisticResult();
		StatisticResult amplitudeResult = new StatisticResult();
		StatisticResult avgResult = new StatisticResult();
		StatisticResult fft = new StatisticResult();

		Min.init(minResult);
		Max.init(maxResult);
		Amplitude.init(amplitudeResult);
		Average.init(avgResult);

		int size = 0;
		double freq = 10.0d;
		
		for (int i = 0; i < this.velocity.size(); i++)
		{
			ArrayList <Double> data = this.velocity.get(i);
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
		
		Collector.init(collectorResult, size);
		DependencyCollector.init(dCollectorResult, size * 2);
		
		StatisticResult varianceResult = new StatisticResult();
		Variance.init(varianceResult, avgResult.getValue().get(0));
		

		DependencyCollector.iterate( 0.0d , 0.0d );
		
		for (int i = 0; i < this.velocity.size(); i++)
		{
			ArrayList <Double> data = this.velocity.get(i);
			double dv, dt, a;

			for (int j = 2; j < data.size() - 1; j += 2)
			{
				dt = data.get(j) - data.get(j - 2);
				dv = data.get(j + 1) - data.get(j - 1);
				a = dv / dt;
				Collector.iterate(a);
				DependencyCollector.iterate( data.get(j), a);
				Variance.iterate(a);
				size++;
			}
		}
		
		Variance.finish();
		
		StatisticResult standardDevResult = new StatisticResult();
		StandardDev.init(standardDevResult, varianceResult.getValue().get(0));
		
		freq = 1000.0d / freq;
		FFT.init(fft, collectorResult.getValue(), freq);
		
		this.value.put("Collector", collectorResult);
		this.value.put("Dependency Collector", dCollectorResult);
		this.value.put("FFT", fft);
		this.value.put("Min", minResult);
		this.value.put("Max", maxResult);
		this.value.put("Amplitude", amplitudeResult);
		this.value.put("Average", avgResult);
		this.value.put("Variance", varianceResult);
		this.value.put("StandardDev", standardDevResult);
	}



}
