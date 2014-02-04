package pi.inputs.drawing;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import pi.utilities.Range;

public class Figure
{
	private Drawing parent;
	private LinkedList<Segment> segment;
	private Rectangle bounds;

	private int totalPoints = 0;

	private int type = -1;

	public static final int DEFAULT = -2;
	public static final int ALLFIGURE = 0;
	public static final int ZIGZAG = 1;
	public static final int CIRCLELEFT = 2;
	public static final int CIRCLERIGHT = 3;
	public static final int FIRSTLINE = 4;
	public static final int SECONDLINE = 5;
	public static final int BROKENLINE = 6;
	public static final int SPIRALIN = 7;
	public static final int SPIRALOUT = 8;

	public Figure(Drawing parent)
	{
		this.parent = parent;
	}

	public void calculateBounds()
	{
		if ((getSegment() == null) || (getSegment().size() == 0))
			return;

		Iterator<Segment> it = getSegment().iterator();
		Segment seg;

		int min_x = 1000000;
		int max_x = -1000000;
		int min_y = 1000000;
		int max_y = -1000000;

		while (it.hasNext())
		{
			seg = it.next();
			ArrayList<PacketData> packet = this.parent.getPacket();
			Range range = seg.getRange();

			for (int i = range.getLeft(); i <= range.getRight(); i++)
			{
				if (packet.get(i).getPkX() > max_x)
					max_x = packet.get(i).getPkX();
				if (packet.get(i).getPkX() < min_x)
					min_x = packet.get(i).getPkX();
				if (packet.get(i).getPkY() > max_y)
					max_y = packet.get(i).getPkY();
				if (packet.get(i).getPkY() < min_y)
					min_y = packet.get(i).getPkY();
			}
		}

		this.setBounds(new Rectangle(min_x, max_y, (max_x - min_x),
				(max_y - min_y)));
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void setBounds(Rectangle bounds)
	{
		this.bounds = bounds;
	}

	public LinkedList<Segment> getSegment()
	{
		return segment;
	}

	public void setSegment(LinkedList<Segment> segment)
	{
		this.segment = segment;
	}

	public Drawing getParent()
	{
		return parent;
	}

	public void setParent(Drawing parent)
	{
		this.parent = parent;
	}

	public int getTotalPoints()
	{
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints)
	{
		this.totalPoints = totalPoints;
	}

}
