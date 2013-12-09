package pi.statistics.logic.extenders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.StatisticResult;

public class FigureStandards extends AttributeResult
{
	private ArrayList<PacketData> packet;
	private LinkedList<Segment> segment;

	public FigureStandards(ArrayList<PacketData> packet,
			LinkedList<Segment> segment)
	{
		this.packet = packet;
		this.segment = segment;
	}

	@Override
	public void calculateResult()
	{
		this.value = new HashMap<String, StatisticResult>();
		
		StatisticResult timeResult = new StatisticResult();
		StatisticResult lengthResult = new StatisticResult();
		StatisticResult avgSpeedResult = new StatisticResult();
		StatisticResult breaksResult = new StatisticResult();
		
		double time = 0.0d;
		double length = 0.0d;
		double avgSpeed = 0.0d;

		Iterator<Segment> it = this.segment.iterator();
		Segment seg;
		double dist;

		while (it.hasNext())
		{
			seg = it.next();

			for (int i = seg.getRange().getLeft() + 1; i <= seg.getRange()
					.getRight(); i++)
			{
				dist = this.getDistance(this.packet.get(i),
						this.packet.get(i - 1));
				length += dist;

			}
			time += (this.packet.get(seg.getRange().getRight()).getPkTime() - this.packet
					.get(seg.getRange().getLeft()).getPkTime());
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

		this.value.put("Drawing time", timeResult);
		this.value.put("Drawing length", lengthResult);
		this.value.put("Avg Speed", avgSpeedResult);
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
