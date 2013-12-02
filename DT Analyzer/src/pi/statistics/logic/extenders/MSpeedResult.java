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
import pi.statistics.functions.FFTFreq;
import pi.statistics.functions.Max;
import pi.statistics.functions.Median;
import pi.statistics.functions.Min;
import pi.statistics.functions.StandardDev;
import pi.statistics.functions.Variance;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.StatisticResult;

public class MSpeedResult extends AttributeResult
{
	private ArrayList<PacketData> packet;
	private LinkedList<Segment> segment;

	private ArrayList<ArrayList<Double>> toAcceleration;
	private double freq = 1.0d / 100.0d;

	public MSpeedResult(ArrayList<PacketData> packet,
			LinkedList<Segment> segment)
	{
		this.packet = packet;
		this.segment = segment;
	}

	@Override
	public void calculateResult()
	{
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

		Iterator<Segment> it = this.segment.iterator();
		Segment seg;
		
		double value, dist, time;
		double timeOfFragmentation = 1000.0d * (1.0d / 10.0d);

		while (it.hasNext())
		{
			seg = it.next();
			int prev = seg.getRange().getLeft();

			double sumTime = 0.0d;
			double sumDist = 0.0d;

			for (int i = seg.getRange().getLeft() + 1; i <= seg.getRange()
					.getRight(); i++)
			{
				if (((this.packet.get(i).getPkX() == this.packet.get(i - 1)
						.getPkX()) && (this.packet.get(i).getPkY() == this.packet
						.get(i - 1).getPkY())))
				{
					continue;
				}

				dist = this.getDistance(this.packet.get(i),
						this.packet.get(prev));
				time = this.packet.get(i).getPkTime()
						- this.packet.get(prev).getPkTime();

				prev = i;

				sumTime += time;
				sumDist += dist;

				if (sumTime >= timeOfFragmentation)
				{
					value = sumDist / sumTime;
					Min.iterate(value);
					Max.iterate(value);
					Amplitude.iterate(value);
					Average.iterate(value);
					sumTime = 0.0d;
					sumDist = 0.0d;
					size++;
					
				} else if (i == seg.getRange().getRight())
				{
					value = 0.0d;
					Min.iterate(value);
					Max.iterate(value);
					Amplitude.iterate(value);
					Average.iterate(value);
					sumTime = 0.0d;
					sumDist = 0.0d;
					size++;
				}
			}
		}

		Average.finish();
		
		StatisticResult varianceResult = new StatisticResult();
		Variance.init(varianceResult, avgResult.getValue().get(0));
		Collector.init(histogramResult, size);
		DependencyCollector.init(dependencyResult, size * 2 + 1);
		Median.init(median, size);
		
		boolean first = true;
		double baseTime = 0.0d;

		DependencyCollector.iterate(0.0d, 0.0d);

		this.toAcceleration = new ArrayList<ArrayList<Double>>(
				this.segment.size());

		it = this.segment.iterator();
		while (it.hasNext())
		{
			seg = it.next();
			int prev = seg.getRange().getLeft();

			double sumTime = 0.0d;
			double sumDist = 0.0d;

			ArrayList <Double> toAdd = new ArrayList<Double>((seg.getRange()
					.getInterval() + 1) * 2);

			for (int i = seg.getRange().getLeft() + 1; i <= seg.getRange()
					.getRight(); i++)
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

				if (((this.packet.get(i).getPkX() == this.packet.get(i - 1)
						.getPkX()) && (this.packet.get(i).getPkY() == this.packet
						.get(i - 1).getPkY())))
				{
					continue;
				}

				dist = this.getDistance(this.packet.get(i),
						this.packet.get(prev));
				time = this.packet.get(i).getPkTime()
						- this.packet.get(prev).getPkTime();

				prev = i;

				sumTime += time;
				sumDist += dist;

				if (sumTime >= timeOfFragmentation)
				{
					value = sumDist / sumTime;
					Collector.iterate(value);
					DependencyCollector.iterate(packet.get(i).getPkTime()
							- baseTime, value);
					Variance.iterate(value);
					Median.iterate(value);
				
					toAdd.add(packet.get(i).getPkTime()
							- baseTime);
					toAdd.add(value);

					sumTime = 0.0d;
					sumDist = 0.0d;
				} else if (i == seg.getRange().getRight())
				{
					value = 0.0d;
					Collector.iterate(value);
					DependencyCollector.iterate(packet.get(i).getPkTime()
							- baseTime, value);
					Variance.iterate(value);
					Median.iterate(value);
					
					toAdd.add(packet.get(i).getPkTime()
							- baseTime);
					toAdd.add(value);
					sumTime = 0.0d;
					sumDist = 0.0d;
				}
			}

			this.toAcceleration.add(toAdd);
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

	public double getDistance(PacketData A, PacketData B)
	{
		double dx = A.getPkX() - B.getPkX();
		dx *= dx;

		double dy = A.getPkY() - B.getPkY();
		dy *= dy;

		double result = Math.sqrt(dx + dy);
		return result;
	}

	public ArrayList<ArrayList<Double>> getForAcceleration()
	{
		return this.toAcceleration;
	}

	public double getFreq()
	{
		return freq;
	}

	public void setFreq(double freq)
	{
		this.freq = freq;
	}

}
