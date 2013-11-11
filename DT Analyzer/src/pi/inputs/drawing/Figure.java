package pi.inputs.drawing;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Figure
{
	private LinkedList<Segment> segment;
	private Rectangle bounds;

	private int type = -1;

	public static final int DEFAULT = -1;
	public static final int ZIGZAG = 0;
	public static final int CIRCLELEFT = 1;
	public static final int CIRCLERIGHT = 2;
	public static final int FIRSTLINE = 3;
	public static final int SECONDLINE = 4;
	public static final int BROKENLINE = 5;
	public static final int SPIRALIN = 6;
	public static final int SPIRALOUT = 7;
	
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
			ArrayList<PacketData> packet = seg.getPacket();

			int size = packet.size();
			for (int i = 0; i < size; i++)
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

	
}
