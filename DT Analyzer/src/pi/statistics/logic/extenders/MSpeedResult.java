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
import pi.statistics.functions.Max;
import pi.statistics.functions.Min;
import pi.statistics.functions.StandardDev;
import pi.statistics.functions.Variance;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.StatisticResult;

public class MSpeedResult extends AttributeResult
{
	private ArrayList <PacketData> packet;
	private LinkedList<Segment> segment;
	
	public MSpeedResult(ArrayList <PacketData> packet, LinkedList<Segment> segment)
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

		Min.init(minResult);
		Max.init(maxResult);
		Amplitude.init(amplitudeResult);
		Average.init(avgResult);
		
		int size = 0;
		
		Iterator <Segment> it = this.segment.iterator();
		Segment seg;
		double value, dist, time;
		while (it.hasNext())
		{
			seg = it.next();
			for (int i = seg.getRange().getLeft() + 1; i <= seg.getRange().getRight(); i++)
			{
				dist = this.getDistance(this.packet.get(i), this.packet.get(i - 1));
				time = this.packet.get(i).getPkTime() - this.packet.get(i - 1).getPkTime();
				value = dist / time;
				
				Min.iterate(value);
				Max.iterate(value);
				Amplitude.iterate(value);
				Average.iterate(value);
				
				/*int end = i;
				for (int j = i; i <= seg.getRange().getRight(); j++)
				{	
					if ( (this.packet.get(j - 1).getPkX() == this.packet.get(j).getPkX()) 
							&& (this.packet.get(j - 1).getPkY() == this.packet.get(j).getPkY()) )
					{
						end = j + 1;
					}
					else break;
				}
				
				dist = this.getDistance(this.packet.get(end), this.packet.get(i - 1));
				time = this.packet.get(end).getPkTime() - this.packet.get(i - 1).getPkTime();
				value = dist / time;
			
				for (int j = i; j <= end; j++)
				{
					Min.iterate(value);
					Max.iterate(value);
					Amplitude.iterate(value);
					Average.iterate(value);
				}*/
			}
			size += seg.getRange().getInterval();
		}
		
		
		Collector.init(collectorResult, size);
		DependencyCollector.init(dCollectorResult, size * 2);
		
		Average.finish();
		StatisticResult varianceResult = new StatisticResult();
		Variance.init(varianceResult, avgResult.getValue().get(0));
		
		boolean first = true;
		double baseTime = 0.0d;
		
		
		it = this.segment.iterator();
		while (it.hasNext())
		{
			seg = it.next();
			for (int i = seg.getRange().getLeft() + 1; i <= seg.getRange().getRight(); i++)
			{
				if (first)
				{
					first = false;
					baseTime = packet.get(i).getPkTime();
				}
				
				/*int end = i;
				for (int j = i; i <= seg.getRange().getRight(); j++)
				{	
					if ( (this.packet.get(j - 1).getPkX() == this.packet.get(j).getPkX()) 
							&& (this.packet.get(j - 1).getPkY() == this.packet.get(j).getPkY()) )
					{
						end = j + 1;
					}
					else break;
				}
				
				dist = this.getDistance(this.packet.get(end), this.packet.get(i - 1));
				time = this.packet.get(end).getPkTime() - this.packet.get(i - 1).getPkTime();
				value = dist / time;
			
				for (int j = i; j <= end; j++)
				{
					Collector.iterate(value);
					DependencyCollector.iterate( packet.get(end).getPkTime() - baseTime, value);
					Variance.iterate(value);
				}*/
				
				
				dist = this.getDistance(this.packet.get(i), this.packet.get(i - 1));
				time = this.packet.get(i).getPkTime() - this.packet.get(i - 1).getPkTime();
				value = dist / time;
				Collector.iterate(value);
				DependencyCollector.iterate( packet.get(i).getPkTime() - baseTime, value);
				Variance.iterate(value);
			}
		}
		
		Variance.finish();
		
		StatisticResult standardDevResult = new StatisticResult();
		StandardDev.init(standardDevResult, varianceResult.getValue().get(0));
		
		this.value.put("Collector", collectorResult);
		this.value.put("Dependency Collector", dCollectorResult);
		this.value.put("Min", minResult);
		this.value.put("Max", maxResult);
		this.value.put("Amplitude", amplitudeResult);
		this.value.put("Average", avgResult);
		this.value.put("Variance", varianceResult);
		this.value.put("StandardDev", standardDevResult);
	}
	
	public  double getDistance(PacketData A, PacketData B)
	{
		double dx = A.getPkX() - B.getPkX();
		dx *= dx;
		
		double dy = A.getPkY() - B.getPkY();
		dy *= dy;
		
		double result = Math.sqrt(dx + dy);
		return result;
	}

}
