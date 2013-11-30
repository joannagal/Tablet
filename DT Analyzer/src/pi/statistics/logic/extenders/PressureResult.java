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
		
		DependencyCollector.init(dCollectorResult, size * 2);
		Collector.init(collectorResult, size);
		
		Average.finish();
		StatisticResult varianceResult = new StatisticResult();
		Variance.init(varianceResult, avgResult.getValue().get(0));
		
		it = this.segment.iterator();
		
		boolean first = true;
		double baseTime = 0.0d;
		double freq = 0.010d;
		
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
					}
					
				}

				value = (double) packet.get(i).getPkPressure();
				Collector.iterate(value);
				DependencyCollector.iterate( packet.get(i).getPkTime() - baseTime, value);
				Variance.iterate(value);
			}
		}
		
		/*ArrayList <Double> dummy = new ArrayList <Double> (36);
		for (int i = 0; i < 36; i++)
		dummy.add(Math.sin((double)i * 20 * (Math.PI / 180.0d))) ;*/
		
		freq = freq / 1000;
		freq = 1.0d / freq;
		
		FFT.init(fft, collectorResult.getValue(), freq);
		
		Variance.finish();
		
		StatisticResult standardDevResult = new StatisticResult();
		StandardDev.init(standardDevResult, varianceResult.getValue().get(0));
		
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
