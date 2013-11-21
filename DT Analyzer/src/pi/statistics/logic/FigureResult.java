package pi.statistics.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.statistics.logic.extenders.PressureResult;

public class FigureResult
{
	private Map<String, AttributeResult> value = new HashMap<String, AttributeResult>();
	private Figure figure;
	
	public FigureResult(Figure figure)
	{
		this.setFigure(figure);
	}
	
	public void calculateResult()
	{
		int total = 0;
		
		LinkedList <Segment> seg = this.figure.getSegment();
		Iterator <Segment> it = seg.iterator();
		Segment segment;
		
		while (it.hasNext())
		{
			segment = it.next();
			total += segment.getPacket().size();
		}
		
		ArrayList <PacketData> data = new ArrayList <PacketData> (total);
		
		it = seg.iterator();
		while (it.hasNext())
		{
			segment = it.next();
			ArrayList <PacketData> pck = segment.getPacket();
			int size = segment.getPacket().size();
			
			for (int i = 0; i < size; i++)
			{
				data.add(pck.get(i));
			}
		}
		
		// -----------------------------------------------------
		
		
		PressureResult pressure = new PressureResult(data);
		pressure.calculateResult();
		this.value.put("Pressure", pressure);
		
	}

	
	
	public Map<String, AttributeResult> getValue()
	{
		return value;
	}

	public void setValue(Map<String, AttributeResult> value)
	{
		this.value = value;
	}

	public Figure getFigure()
	{
		return figure;
	}

	public void setFigure(Figure figure)
	{
		this.figure = figure;
	}
	
	
	
	
}
