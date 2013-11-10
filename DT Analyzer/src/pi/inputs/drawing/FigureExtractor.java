package pi.inputs.drawing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import pi.utilities.Range;

public class FigureExtractor
{
	public void extract(Drawing drawing)
	{
		ArrayList <PacketData> packet = drawing.getPacket();
		int size = packet.size();
		
		LinkedList <Range> range = new LinkedList <Range> ();
		double maxDist = drawing.getBreakFigureDistance();
		
		int pointer = 0;
		
		for (int i = 0; i < size - 1; i++)
		{
			if ((Linearize.getDistance(packet.get(i), packet.get(i + 1)) > maxDist)
					|| (i== size - 2))
			{
				range.add(new Range(pointer, i));
				pointer = i + 1;
			}
		}
		
		ArrayList <Figure> figure = new ArrayList <Figure> (range.size());
		drawing.setFigure(figure);
		
		Iterator <Range> it = range.iterator();
		Range value;
		
		while (it.hasNext())
		{
			Figure newFigure = new Figure();
			
			value = it.next();
			ArrayList <PacketData> copyPacket = new ArrayList <PacketData> (value.getInterval() + 1);

			
			for (int i = value.getLeft(); i <= value.getRight(); i++)
			{
				copyPacket.add(packet.get(i));
			}
			
			newFigure.setPacket(copyPacket);
			figure.add(newFigure);
		}
		
		
	}
}
