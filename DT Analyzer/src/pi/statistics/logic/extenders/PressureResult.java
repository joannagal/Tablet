package pi.statistics.logic.extenders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
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

public class PressureResult extends AttributeResult
{
	private ArrayList <PacketData> packet;
	private LinkedList<Segment> segment;
	
	public PressureResult(ArrayList <PacketData> packet, LinkedList<Segment> segment)
	{
		this.packet = packet;
		this.segment = segment;
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
		
		Iterator <Segment> it = this.segment.iterator();
		Segment seg;
		double value;
		while (it.hasNext())
		{
			seg = it.next();
			for (int i = seg.getRange().getLeft(); i <= seg.getRange().getRight(); i++)
			{
				value = (double) packet.get(i).getPkPressure();
				Min.iterate(value);
				Max.iterate(value);
				Amplitude.iterate(value);
				Average.iterate(value);
			}
			size += seg.getRange().getInterval() + 1;
		}
		
		Average.finish();
		
		StatisticResult varianceResult = new StatisticResult();
		
		Variance.init(varianceResult, avgResult.getValue().get(0));
		DependencyCollector.init(dependencyResult, size * 2 + 1);
		Collector.init(histogramResult, size);
		Median.init(median, size);
		
		it = this.segment.iterator();
		
		boolean first = true;
		double baseTime = 0.0d;
		double freq = 1.0d / 100.0d;
		
		DependencyCollector.iterate(0.0d, 0.0d);
		
		while (it.hasNext())
		{
			seg = it.next();
			for (int i = seg.getRange().getLeft(); i <= seg.getRange().getRight(); i++)
			{
				if (first)
				{
					first = false;
					baseTime = packet.get(i).getPkTime();
					
					if (i < seg.getRange().getRight())
					{
						freq = packet.get(i + 1).getPkTime() - baseTime;
						freq = freq / 1000;
						freq = 1.0d / freq;
					}
					
				}

				value = (double) packet.get(i).getPkPressure();
				Collector.iterate(value);
				Median.iterate(value);
				DependencyCollector.iterate( packet.get(i).getPkTime() - baseTime, value);
				Variance.iterate(value);
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
