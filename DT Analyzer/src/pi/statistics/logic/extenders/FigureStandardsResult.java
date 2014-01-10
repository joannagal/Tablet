package pi.statistics.logic.extenders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.StatMapper;
import pi.statistics.logic.StatisticResult;

public class FigureStandardsResult extends AttributeResult
{
	private ArrayList<PacketData> packet;
	private LinkedList<Segment> segment;

	public FigureStandardsResult(ArrayList<PacketData> packet,
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

		StatisticResult timeResult = new StatisticResult();
		StatisticResult lengthResult = new StatisticResult();
		StatisticResult avgSpeedResult = new StatisticResult();
		StatisticResult breaksResult = new StatisticResult();

		double beginDraw = 0.0d;
		double endDraw = 0.0d;
		
		double time = 0.0d;
		double length = 0.0d;
		double avgSpeed = 0.0d;

		Iterator<Segment> it = this.segment.iterator();
		Segment seg;
		double dist;

		if ((!avaible[7]) && (!avaible[8]) && (!avaible[10]))
			return;

		
		if (avaible[7])
		{
			seg = this.segment.getFirst();
			if (seg != null)
			{
				beginDraw = this.packet.get(seg.getRange().getLeft()).getPkTime();
				seg = this.segment.getLast();
				endDraw = this.packet.get(seg.getRange().getRight()).getPkTime();
				time = endDraw - beginDraw;
			}
			
		}
		
		while (it.hasNext())
		{
			seg = it.next();

			if (avaible[8])
				for (int i = seg.getRange().getLeft() + 1; i <= seg.getRange()
						.getRight(); i++)
				{
					dist = this.getDistance(this.packet.get(i),
							this.packet.get(i - 1));
					length += dist;

				}
		}

		if (time > 0.0d)
			avgSpeed = length / time;

		ArrayList<Double> result = new ArrayList<Double>(1);
		result.add(time);
		timeResult.setValue(result);

		result = new ArrayList<Double>(1);
		result.add(length);
		lengthResult.setValue(result);

		result = new ArrayList<Double>(1);
		result.add(avgSpeed);
		avgSpeedResult.setValue(result);

		result = new ArrayList<Double>(1);
		double breaks = this.segment.size() - 1;
		result.add(breaks);
		breaksResult.setValue(result);

		if (avaible[7])
			this.value.put("Drawing time", timeResult);
		if (avaible[8])
			this.value.put("Drawing length", lengthResult);
		if (avaible[9])
			this.value.put("Avg Speed", avgSpeedResult);
		if (avaible[10])
			this.value.put("Breaks Amount", breaksResult);
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
}
