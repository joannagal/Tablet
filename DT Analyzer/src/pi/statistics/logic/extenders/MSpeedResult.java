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
import pi.statistics.logic.StatMapper;
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
	public void calculateResult(boolean projectLevel)
	{
		this.value = new HashMap<String, StatisticResult>();

		boolean[] avaible = new boolean[StatMapper.statisticNames.length];
		for (int i = 0; i < avaible.length; i++)
		{
			if (!projectLevel)
			{
				avaible[i] = true;
			}
			else
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

		if (avaible[0])
			Min.init(minResult);
		if (avaible[1])
			Max.init(maxResult);
		if (avaible[2])
			Amplitude.init(amplitudeResult);
		if (avaible[3])
			Average.init(avgResult);

		int size = 0;

		Iterator<Segment> it = this.segment.iterator();
		Segment seg;

		double value, dist, time;
		double timeOfFragmentation = 1000.0d * (1.0d / 10.0d);

		if ((avaible[0]) || (avaible[1]) || (avaible[3]))
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
						if (avaible[0])
							Min.iterate(value);
						if (avaible[1])
							Max.iterate(value);
						if (avaible[3])
							Average.iterate(value);
						sumTime = 0.0d;
						sumDist = 0.0d;
						size++;

					} else if (i == seg.getRange().getRight())
					{
						value = 0.0d;
						if (avaible[0])
							Min.iterate(value);
						if (avaible[1])
							Max.iterate(value);
						if (avaible[3])
							Average.iterate(value);
						sumTime = 0.0d;
						sumDist = 0.0d;
						size++;
					}
				}
			}

		if (avaible[2])
			Amplitude.finish(minResult.getValue().get(0), maxResult.getValue()
					.get(0));
		if (avaible[3])
			Average.finish();
		if (avaible[4])
			Median.init(median, size);
		if (avaible[5])
			Variance.init(varianceResult, avgResult.getValue().get(0));

		if (avaible[11])
			Collector.init(histogramResult, size);

		if (!projectLevel)
		{
			DependencyCollector.init(dependencyResult, size * 2 + 2);
			DependencyCollector.iterate(0.0d, 0.0d);
		}
		
		boolean first = true;
		double baseTime = 0.0d;

		this.toAcceleration = new ArrayList<ArrayList<Double>>(
				this.segment.size());

		it = this.segment.iterator();
		while (it.hasNext())
		{
			seg = it.next();
			int prev = seg.getRange().getLeft();

			double sumTime = 0.0d;
			double sumDist = 0.0d;

			ArrayList<Double> toAdd = new ArrayList<Double>((seg.getRange()
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

					if (avaible[4])
						Median.iterate(value);
					if (avaible[5])
						Variance.iterate(value);
					
					if (avaible[11])
						Collector.iterate(value);
					
					if (!projectLevel)
						DependencyCollector.iterate(packet.get(i).getPkTime()
								- baseTime, value);	

					toAdd.add(packet.get(i).getPkTime() - baseTime);
					toAdd.add(value);

					sumTime = 0.0d;
					sumDist = 0.0d;
				} else if (i == seg.getRange().getRight())
				{
					value = 0.0d;

					if (avaible[4])
						Median.iterate(value);
					if (avaible[5])
						Variance.iterate(value);

					if (avaible[11])
						Collector.iterate(value);
					
					if (!projectLevel)
						DependencyCollector.iterate(packet.get(i).getPkTime()
								- baseTime, value);	

					toAdd.add(packet.get(i).getPkTime() - baseTime);
					toAdd.add(value);
					sumTime = 0.0d;
					sumDist = 0.0d;
				}
			}

			this.toAcceleration.add(toAdd);
		}

		if (avaible[4])
			Median.finish();
		if (avaible[5])
			Variance.finish();
		if (avaible[6])
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

		if (avaible[0])
			this.value.put("Min", minResult);
		if (avaible[1])
			this.value.put("Max", maxResult);
		if (avaible[2])
			this.value.put("Amplitude", amplitudeResult);
		if (avaible[3])
			this.value.put("Average", avgResult);
		if (avaible[4])
			this.value.put("Median", median);
		if (avaible[5])
			this.value.put("Variance", varianceResult);
		if (avaible[6])
			this.value.put("StandardDev", standardDevResult);
		if (avaible[11])
			this.value.put("FFT", fft);
		if (avaible[11])
			this.value.put("FFT Freq", fftFreq);
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
