package pi.inputs.drawing.autofinder;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;

public class FigureInterpreter
{
	private int figureCnt = -1;
	private Rectangle bounds;

	public void interprate(Drawing drawing)
	{
		if (drawing.getFigure() == null)
			return;

		ArrayList<Figure> figure = drawing.getFigure();
		Iterator<Figure> it = figure.iterator();

		Figure fig;
		this.figureCnt = -1;
		this.bounds = drawing.getContent();
		
		while (it.hasNext())
		{
			fig = it.next();
			this.interprateFigure(fig);
		}

	}

	public void interprateFigure(Figure figure)
	{
		if (figure.getSegment() == null)
			return;
		if (figure.getBounds() == null)
			return;

		int test = this.circleTest(figure);
		if (test != -1)
		{
			figure.setType(test);
			return;
		}
		test = this.spiralTest(figure);
		if (test != -1)
		{
			figure.setType(test);
			return;
		}
		test = this.lineTest(figure);
		if (test != -1)
		{
			figure.setType(test);
			return;
		}
		test = this.zigzagTest(figure);
		if (test != -1)
		{
			figure.setType(test);
			return;
		}
	}

	public int zigzagTest(Figure figure)
	{
		if (this.bounds == null) return -1;
		if ( (figure.getBounds().height < 0.65d * this.bounds.height) &&
				 (figure.getBounds().width < 0.65d * this.bounds.width) )
				return -1;
		
		boolean horizont = false;
		double height = figure.getBounds().height;
		double width = 0.0d;
		if (figure.getBounds().width > height)
		{
			horizont = true;
			width = height;
			height = figure.getBounds().width;
		}
		else width = figure.getBounds().width;
		
		int total = 0;
		ArrayList<PacketData> packet;
		Iterator<Segment> it = figure.getSegment().iterator();
		Segment seg;

		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			if (packet == null)
				continue;
			int size = packet.size();
			for (int i = 0; i < size; i++) total++;
		}
		
		it = figure.getSegment().iterator();
		int accept = 0;
		double accPercent = 0.5d;
		
		height /= 7;
		int part = total / 7;
		
		int current = 0;
		double a = 0.0d, b = 0.0d, dist;
		double accDist = width / 5;
		
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			if (packet == null)
				continue;
			int size = packet.size();
			for (int i = 0; i < size; i++) 
			{
				current++;
				a = (height / width);
				for (int j = 1; j <= 7; j++)
				{
					if (current <= j * part + 5) 
					{
						a *= -1;
						break;
					}
				}
				if (horizont) a *= -1;
				b = packet.get(0).getPkY() - a * packet.get(0).getPkX();
				
				dist = this.getDistanceFromLine(a, b, packet.get(i).getPkX(), packet.get(i).getPkY());
				if (dist < accDist) accept++;
			}	
		}
		
		double result = (double)accept / (double)(total);
		System.out.printf("ZIG: %f\n", result);
		
		if (result > accPercent)
		{
			return Figure.ZIGZAG;
		}
		
		
		return -1;
	}
	
	public int lineTest(Figure figure)
	{
		if (this.bounds == null) return -1;
		if ( (figure.getBounds().height < 0.65d * this.bounds.height) &&
			 (figure.getBounds().width < 0.65d * this.bounds.width) )
			return -1;
		
		
		ArrayList<PacketData> packet;
		Iterator<Segment> it = figure.getSegment().iterator();
		Segment seg;

		int size = 0;
		int total = 1;

		double accX = 0.0d;
		double accY = 0.0d;

		double bX = 0.0d, bY = 0.0d;

		// gather avg angle
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();

			if (packet == null)
				continue;
			size = packet.size();

			for (int i = 1; i < size; i++)
			{
				accX += (packet.get(i).getPkX() - packet.get(i - 1).getPkX());
				accY += (packet.get(i).getPkY() - packet.get(i - 1).getPkY());

				bX += packet.get(i).getPkX();
				bY += packet.get(i).getPkY();

				total++;
			}
		}

		bX /= (double) (total - 1);
		bY /= (double) (total - 1);

		int correct = 0;
		double dist = 0.0d;
		if (figure.getBounds().height > figure.getBounds().width)
			dist = figure.getBounds().height / 30.0d;
		else
			dist = figure.getBounds().width / 30.0d;

		if (accX == 0)
		{
			System.out.printf("UPS\n");
		} else
		{
			double a = accY / accX;
			double b = bY - a * bX;

			it = figure.getSegment().iterator();
			while (it.hasNext())
			{
				seg = it.next();
				packet = seg.getPacket();
				if (packet == null)
					continue;
				size = packet.size();
				for (int i = 1; i < size; i++)
				{
					if (this.getDistanceFromLine(a, b, packet.get(i).getPkX(),
							packet.get(i).getPkY()) < dist)
						correct++;
				}
			}
		}
		
		// height / width musi byc > 0.65 drawing bounds

		double accept = 0.65d;

		double result = (double) correct / (double) total;
		//System.out.printf("--- %f\n", result);

		if (result > accept)
		{
			this.figureCnt++;
			if (this.figureCnt == 3)
				return -1;
			
			
			
			return Figure.FIRSTLINE + this.figureCnt;

		}

		return -1;
	}

	public double getDistanceFromLine(double a, double b, double x, double y)
	{
		double result = 0.0d;
		result = (a * x - y + b) / Math.sqrt(a * a + 1);
		if (result < 0)
			return -result;
		return result;
	}

	public int spiralTest(Figure figure)
	{
		if (!this.isSquare(figure))
			return -1;

		Point m = new Point(0, 0);
		int div = 0;
		int size = 0;

		ArrayList<PacketData> packet;
		Iterator<Segment> it = figure.getSegment().iterator();
		Segment seg;

		int total = 0;

		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();

			if (packet == null)
				continue;
			size = packet.size();

			for (int i = 0; i < size; i++)
			{
				m.x += packet.get(i).getPkX();
				m.y += packet.get(i).getPkY();
				div++;
				total++;
			}
		}

		// center of mass
		if (div != 0)
		{
			m.x /= div;
			m.y /= div;
		}

		// spiral struct
		double base = (figure.getBounds().width + figure.getBounds().height) / 14;
		double[] distDown = new double[7];
		double[] partDown = new double[7];
		double[] distUp = new double[7];
		double[] partUp = new double[7];

		for (int i = 0; i < 7; i++)
		{
			distDown[i] = (3.5d - (double) i * 0.5);
			if (i == 0)
				partDown[i] = distDown[i];
			else
				partDown[i] = distDown[i] + partDown[i - 1];

			distUp[i] = (double) (i + 1) * 0.5;
			if (i == 0)
				partUp[i] = distUp[i];
			else
				partUp[i] = distUp[i] + partUp[i - 1];
		}
		for (int i = 0; i < 7; i++)
		{
			partUp[i] = (double) total * (partUp[i]) / 14.0d;
			distUp[i] *= base;

			partDown[i] = (double) total * (partDown[i]) / 14.0d;
			distDown[i] *= base;
		}

		Double accRange = (base * 0.5d);
		Double accPercent = 0.5d;
		int downAccu = 0;
		int upAccu = 0;
		int current = 0;

		Double rad;
		it = figure.getSegment().iterator();
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();

			if (packet == null)
				continue;
			size = packet.size();

			for (int i = 0; i < size; i++)
			{
				current++;
				rad = this.getDistance(m, new Point(packet.get(i).getPkX(),
						packet.get(i).getPkY()));

				for (int j = 0; j < 7; j++)
					if ((current < partDown[j]) || (j == 6))
					{
						if (this.isInRange(rad, distDown[j], accRange))
							downAccu++;
						break;
					}

				for (int j = 0; j < 7; j++)
					if ((current < partUp[j]) || (j == 6))
					{
						if (this.isInRange(rad, distUp[j], accRange))
							upAccu++;
						break;
					}
			}
		}

		double resultDown = (double) downAccu / (double) total;
		double resultUp = (double) upAccu / (double) total;

		// System.out.printf("--- %f %f\n", resultDown, resultUp);

		if ((resultDown > resultUp) && (resultDown > accPercent))
		{
			return Figure.SPIRALIN;
		} else if ((resultUp > resultDown) && (resultUp > accPercent))
		{
			return Figure.SPIRALOUT;
		}

		return -1;
	}

	public int circleTest(Figure figure)
	{

		if (!this.isSquare(figure))
			return -1;

		double scale = 1.0d;
		scale = (double) figure.getBounds().width
				/ (double) figure.getBounds().height;

		Double dist = 0.0d;
		Point m = new Point(0, 0);
		int div = 0;
		int size = 0;

		ArrayList<PacketData> packet;
		Iterator<Segment> it = figure.getSegment().iterator();
		Segment seg;

		int total = 0;
		double scalledY = 0.0d;

		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();

			if (packet == null)
				continue;
			size = packet.size();

			for (int i = 0; i < size; i++)
			{
				m.x += packet.get(i).getPkX();
				m.y += packet.get(i).getPkY();
				div++;
				total++;
			}
		}

		// center of mass
		if (div != 0)
		{
			m.x /= div;
			m.y /= div;
		}

		dist = 0.0d;
		div = 0;
		it = figure.getSegment().iterator();
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();

			if (packet == null)
				continue;
			size = packet.size();

			for (int i = 0; i < size; i++)
			{
				scalledY = (double) m.y + (packet.get(i).getPkY() - m.y)
						* scale;
				dist += this.getDistance(m, new Point(packet.get(i).getPkX(),
						(int) scalledY));
				div++;
			}
		}

		if (div != 0)
		{
			dist /= div;
		}
		// check if circle

		div = 0;
		int accepted = 0;
		int accRange = (int) (dist * 0.2d);
		Double accPercent = 0.5d;

		int current = 0;
		int[][] accu = new int[4][4]; // 4 rundy po 4 cwiartki
		int[] visited = new int[3];

		Double rad = 0.0d;
		it = figure.getSegment().iterator();
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();

			if (packet == null)
				continue;
			size = packet.size();

			for (int i = 0; i < size; i++)
			{
				scalledY = (double) m.y + (packet.get(i).getPkY() - m.y)
						* scale;
				rad = this.getDistance(m, new Point(packet.get(i).getPkX(),
						(int) scalledY));
				if ((rad > dist - accRange) && (rad < dist + accRange))
				{
					accepted++;
				}
				div++;
				current++;

				int where = 0;
				if (current < (double) total * 0.25d)
					where = 0;
				else if (current < (double) total * 0.5d)
					where = 1;
				else if (current < (double) total * 0.75d)
					where = 2;
				else
					where = 3;

				if (packet.get(i).getPkX() < m.x)
				{
					if (packet.get(i).getPkY() < m.y)
						accu[where][0]++;
					else
						accu[where][1]++;
				} else
				{
					if (packet.get(i).getPkY() < m.y)
						accu[where][3]++;
					else
						accu[where][2]++;
				}
			}
		}

		double result = (double) accepted / (double) div;

		if (result > accPercent)
		{
			// parse visited
			for (int i = 0; i < 3; i++)
			{
				int p = 0;
				int max = -1;
				for (int j = 0; j < 4; j++)
				{
					if (accu[i][j] > max)
					{
						max = accu[i][j];
						p = j;
					}
				}
				visited[i] = p;
			}

			if ((visited[1] == (visited[0] + 1) % 4)
					&& (visited[2] == (visited[1] + 1) % 4))
			{
				return Figure.CIRCLELEFT;
			} else
				return Figure.CIRCLERIGHT;

		}

		return -1;
	}

	public boolean isSquare(Figure figure)
	{
		if ((!this.isInRange(figure.getBounds().width,
				figure.getBounds().height, 0.2d * figure.getBounds().height))
				|| (!this.isInRange(figure.getBounds().height,
						figure.getBounds().width,
						0.2d * figure.getBounds().width)))
		{
			return false;
		}

		return true;
	}

	public boolean isInRange(double value, double base, double dev)
	{
		if ((value > base - dev) && (value < base + dev))
			return true;
		return false;
	}

	public double getDistance(Point A, Point B)
	{
		double dx = A.x - B.x;
		dx *= dx;

		double dy = A.y - B.y;
		dy *= dy;

		double result = Math.sqrt(dx + dy);
		return result;
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

	/*
	 * public int lineTest(Figure figure) { ArrayList <PacketData> packet;
	 * Iterator <Segment> it = figure.getSegment().iterator(); Segment seg;
	 * 
	 * int size = 0; int total = 0;
	 * 
	 * double accX = 0.0d; double accY = 0.0d; double avgAngle = 0.0d;
	 * 
	 * // gather avg angle while (it.hasNext()) { seg = it.next(); packet =
	 * seg.getPacket();
	 * 
	 * if (packet == null) continue; size = packet.size();
	 * 
	 * for (int i = 1; i < size; i++) { accX += (packet.get(i).getPkX() -
	 * packet.get(i - 1).getPkX()); accY += (packet.get(i).getPkY() -
	 * packet.get(i - 1).getPkY()); avgAngle = total++; } } avgAngle =
	 * this.getAngle(accX, accY);
	 * 
	 * int correct = 0; double accept = 0.5d;
	 * 
	 * it = figure.getSegment().iterator(); while (it.hasNext()) { seg =
	 * it.next(); packet = seg.getPacket();
	 * 
	 * if (packet == null) continue; size = packet.size();
	 * 
	 * for (int i = 1; i < size; i++) { double dx = (packet.get(i).getPkX() -
	 * packet.get(i - 1).getPkX()); double dy = (packet.get(i).getPkY() -
	 * packet.get(i - 1).getPkY()); if (this.isAngleInRange(this.getAngle(dx,
	 * dy), avgAngle, 25.0d)) correct++; } }
	 * 
	 * double result = (double) correct / (double) total;
	 * System.out.printf("--- %f\n", result);
	 * 
	 * if (result > accept) { return Figure.FIRSTLINE; }
	 * 
	 * return -1; } public boolean isAngleInRange(double value, double base,
	 * double range) { value += 360; base += 360;
	 * 
	 * if ( (value >= base - range) && (value - 360 <= (int)(base + range) % 360
	 * ) ) return true;
	 * 
	 * return false; }
	 * 
	 * public double getAngle(double dx, double dy) { if ((dx > -0.001d) && (dx
	 * < 0.001d)) { if (dy >= 0) return 90.0d; else return 270.0d; } else {
	 * double x = (double) dx; if (x < 0) x *= -1.0d;
	 * 
	 * double y = (double) dy; if (y < 0) y *= -1.0d;
	 * 
	 * double angle = Math.atan(y / x); angle /= (Math.PI / 180.0d);
	 * 
	 * if (dx < 0) { if (dy > 0) return (180.0d - angle); else return 180.0d +
	 * angle; } else { if (dy > 0) return angle; else return 360.0d - angle; } }
	 * }
	 */
}
