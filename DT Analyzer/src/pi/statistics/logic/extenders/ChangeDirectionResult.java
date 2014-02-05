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

public class ChangeDirectionResult extends AttributeResult
{
	private ArrayList<PacketData> packet;
	private LinkedList<Segment> segment;

	public ChangeDirectionResult(ArrayList<PacketData> packet,
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

		Iterator<Segment> it = this.segment.iterator();
		Segment seg;
		double value = 0.0d;
		double prevAngle = -10000.0d;

		while (it.hasNext())
		{
			seg = it.next();
			int prev = seg.getRange().getLeft();

			for (int i = seg.getRange().getLeft() + 1; i <= seg.getRange()
					.getRight(); i++)
			{
				if (((this.packet.get(i).getPkX() == this.packet.get(i - 1)
						.getPkX()) && (this.packet.get(i).getPkY() == this.packet
						.get(i - 1).getPkY())))
				{
					continue;
				}

				double thisAngle = this.getAngle(this.packet.get(i).getPkX()
						- this.packet.get(prev).getPkX(), this.packet.get(i)
						.getPkY() - this.packet.get(prev).getPkY());

				if (prevAngle < -1000.0d)
				{
					prevAngle = thisAngle;
					continue;
				} else
				{
					value = this.getDiffAngles(thisAngle, prevAngle);
					prevAngle = thisAngle;
				}

				if (avaible[4])
					Min.iterate(value);
				if (avaible[5])
					Max.iterate(value);
				if (avaible[6])
					Average.iterate(value);

				size++;
				prev = i;
			}
		}

		if (avaible[6])
			Amplitude.finish(minResult.getValue().get(0), maxResult.getValue()
					.get(0));
		if (avaible[7])
			Average.finish();
		if (avaible[8])
			Median.init(median, size);
		if (avaible[9])
			Variance.init(varianceResult, avgResult.getValue().get(0));

		if (avaible[11])
			Collector.init(histogramResult, size);

		if (!projectLevel)
		{
			DependencyCollector.init(dependencyResult, size * 2 + 2);
			DependencyCollector.iterate(0.0d, 0.0d);
		}

		it = this.segment.iterator();
		value = 0.0d;
		prevAngle = -10000.0d;

		boolean first = true;
		double baseTime = 0.0d;
		double freq = 0.010d;

		while (it.hasNext())
		{
			seg = it.next();
			int prev = seg.getRange().getLeft();

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
					}

				}

				if (((this.packet.get(i).getPkX() == this.packet.get(i - 1)
						.getPkX()) && (this.packet.get(i).getPkY() == this.packet
						.get(i - 1).getPkY())))
				{
					continue;
				}

				double thisAngle = this.getAngle(this.packet.get(i).getPkX()
						- this.packet.get(prev).getPkX(), this.packet.get(i)
						.getPkY() - this.packet.get(prev).getPkY());

				if (prevAngle < -1000.0d)
				{
					prevAngle = thisAngle;
					continue;
				} else
				{
					value = this.getDiffAngles(thisAngle, prevAngle);
					prevAngle = thisAngle;
				}

				if (avaible[8])
					Median.iterate(value);
				if (avaible[9])
					Variance.iterate(value);

				if (avaible[11])
					Collector.iterate(value);

				if (!projectLevel)
					DependencyCollector.iterate(packet.get(i).getPkTime()
							- baseTime, value);

				size++;
				prev = i;
			}
		}

		freq = freq / 1000;
		freq = 1.0d / freq;

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

	public double getDiffAngles(double A, double B)
	{
		A += 360.0d;
		B += 360.0d;
		double dA = B - A;
		if (dA < 0.0d)
		{
			if (dA < -180.0d)
				return 360.0d + dA;
			else
				return dA;

		} else
		{
			if (dA > 180.0d)
				return -(360.0d - dA);
			else
				return dA;
		}
	}

	public double getAngle(double dx, double dy)
	{
		if ((dx > -0.001d) && (dx < 0.001d))
		{
			if (dy >= 0)
				return 90.0d;
			else
				return 270.0d;
		} else
		{
			double x = (double) dx;
			if (x < 0)
				x *= -1.0d;

			double y = (double) dy;
			if (y < 0)
				y *= -1.0d;

			double angle = Math.atan(y / x);
			angle /= (Math.PI / 180.0d);

			if (dx < 0)
			{
				if (dy > 0)
					return (180.0d - angle);
				else
					return 180.0d + angle;
			} else
			{
				if (dy > 0)
					return angle;
				else
					return 360.0d - angle;
			}
		}
	}
}
