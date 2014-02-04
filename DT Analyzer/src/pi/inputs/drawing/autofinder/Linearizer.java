package pi.inputs.drawing.autofinder;

import java.util.ArrayList;
import java.util.Iterator;

import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.utilities.Range;

public class Linearizer
{
	public void linearize(Drawing drawing, int steps)
	{
		Figure[] figure = drawing.getCompleteFigure();
		Iterator<Segment> itSeg;
		Figure fig;
		Segment seg;

		ArrayList<PacketData> packet = drawing.getPacket();
		Range range;
		int size;

		for (int i = 0; i < figure.length; i++)
		{
			fig = figure[i];
			if (fig == null)
				continue;
			if (fig.getSegment() == null)
				continue;

			itSeg = fig.getSegment().iterator();

			while (itSeg.hasNext())
			{
				seg = itSeg.next();
				range = seg.getRange();

				if ((range == null) || (range.getInterval() < 2))
					continue;

				size = packet.size();
				ArrayList<PacketData> linear = new ArrayList<PacketData>(size);

				for (int j = range.getLeft(); j <= range.getRight(); j++)
				{
					linear.add(packet.get(j).getCopy());
				}

				this.linearizeVectorDynamic(linear, steps);
				seg.setLinearized(linear);
			}
		}
	}

	public void linearizeVectorDynamic(ArrayList<PacketData> input, int steps)
	{
		int size = input.size();
		PacketData pckg = null;
		int div = steps;

		int sX, sY;
		int contX, contY;

		int minFlag = 0;

		double angle;
		double distance = 0.0d;
		double newX, newY;
		double deg = Math.PI / 180.0d;
		double fi;
		double tX, tY, tD;

		for (int i = 0; i < size; i += div)
		{

			sX = 0;
			sY = 0;
			minFlag = 0;

			pckg = input.get(i);
			contX = pckg.getPkX();
			contY = pckg.getPkY();

			for (div = 1; div <= steps; div++)
			{
				if (i + div >= size)
				{
					minFlag = -1;
					break;
				}

				pckg = input.get(i + div);
				sX += (pckg.getPkX() - contX);
				sY += (pckg.getPkY() - contY);
			}

			if (div != 1)
				div--;

			angle = this.getAngle((double) sX, (double) sY);
			distance = Linearizer.getDistance(input.get(i),
					input.get(i + (div + minFlag)));

			tX = input.get(i + (div + minFlag)).getPkX() - contX;
			tY = input.get(i + (div + minFlag)).getPkY() - contY;
			fi = this.difAngles(angle, this.getAngle(tX, tY));

			tD = Math.cos(fi * deg) * distance;
			distance = tD;

			distance /= (double) (div + minFlag);

			for (int j = 1; j <= (div + minFlag); j++)
			{
				pckg = input.get(i + j);
				pckg.setPkTime(input.get(i + j).getPkTime());
				pckg.setPkPressure(input.get(i + j).getPkPressure());

				newX = (double) contX + Math.cos((angle) * deg) * distance
						* (double) j;
				newY = (double) contY + Math.sin((angle) * deg) * distance
						* (double) j;

				pckg.setPkX((int) newX);
				pckg.setPkY((int) newY);
			}

		}

	}

	public double difAngles(double A, double B)
	{
		double result = A - B;

		if (result < 0)
		{
			if (result < -180.0d)
				result = 360.0d + result;
			else
				result *= -1;
		} else if (result > 180.0d)
			result = 360.0d - result;

		return result;
	}

	public static double getDistance(PacketData A, PacketData B)
	{
		double dx = A.getPkX() - B.getPkX();
		dx *= dx;

		double dy = A.getPkY() - B.getPkY();
		dy *= dy;

		double result = Math.sqrt(dx + dy);
		return result;
	}

	public int compareAngles(double A, double B)
	{
		A += 360.0d;
		B += 360.0d;
		double dA = B - A;
		if (dA < 0.0d)
		{
			if (dA < -180.0d)
				return 1;
			else
				return -1;

		} else
		{
			if (dA > 180.0d)
				return 1;
			else
				return -1;
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
