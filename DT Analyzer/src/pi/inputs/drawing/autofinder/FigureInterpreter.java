package pi.inputs.drawing.autofinder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;

public class FigureInterpreter
{
	public void interprate(Drawing drawing)
	{
		if (drawing.getFigure() == null) return;
		
		ArrayList <Figure> figure = drawing.getFigure();
		Iterator <Figure> it = figure.iterator();
		
		Figure fig;
		
		while (it.hasNext())
		{
			fig = it.next();
			this.interprateFigure(fig);
		}
		
	}

	public void interprateFigure(Figure figure)
	{
		if (figure.getSegment() == null) return;
		if (figure.getBounds() == null) return;
		
		int test = this.circleTest(figure);
		if (test != -1) { figure.setType(test); return;}
		test = this.spiralTest(figure);
		if (test != -1) { figure.setType(test); return;}
		
	}
	
	public int spiralTest(Figure figure)
	{
		if (!this.isSquare(figure)) return -1;

		double scale = 1.0d;
		scale = (double) figure.getBounds().width / (double) figure.getBounds().height;
		
		Point m = new Point(0, 0);
		int div = 0;
		int size = 0;
		
		ArrayList <PacketData> packet;
		Iterator <Segment> it = figure.getSegment().iterator();
		Segment seg;

		int total = 0;
		
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
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
		double [] distDown = new double[7];
		double [] partDown = new double[7];
		double [] distUp = new double[7];
		double [] partUp = new double[7];
		
		for (int i = 0; i < 7; i++)
		{
			distDown[i] = (3.5d - (double) i * 0.5);
			if (i == 0) partDown[i] = distDown[i];
			else partDown[i] = distDown[i] + partDown[i - 1];
			
			distUp[i] = (double) (i + 1) * 0.5;
			if (i == 0) partUp[i] = distUp[i];
			else partUp[i] = distUp[i] + partUp[i - 1];
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
			
			if (packet == null) continue;
			size = packet.size();
			
			for (int i = 0; i < size; i++)
			{
				current++;
				rad = this.getDistance(m, new Point(packet.get(i).getPkX(), packet.get(i).getPkY()));
				
				for (int j = 0; j < 7; j++)
					if ((current < partDown[j]) || (j == 6)) 
					{
						if (this.isInRange(rad, distDown[j], accRange)) downAccu++;
						break;
					}
				
				for (int j = 0; j < 7; j++)
					if ((current < partUp[j]) || (j == 6)) 
					{
						if (this.isInRange(rad, distUp[j], accRange)) upAccu++;
						break;
					}
			}
		}
		
		double resultDown = (double) downAccu / (double) total;
		double resultUp = (double) upAccu / (double) total;
		
		System.out.printf("--- %f %f\n", resultDown, resultUp);
	
		
		if ((resultDown > resultUp) && (resultDown > accPercent))
		{
			return Figure.SPIRALIN;
		}
		else if ((resultUp > resultDown) && (resultUp > accPercent))
		{
			return Figure.SPIRALOUT;
		}

		return -1;
	}
	
	public int circleTest(Figure figure)
	{
		
		if (!this.isSquare(figure)) return -1;

		
		double scale = 1.0d;
		scale = (double) figure.getBounds().width / (double) figure.getBounds().height;
		
		Double dist = 0.0d;
		Point m = new Point(0, 0);
		int div = 0;
		int size = 0;
		
		ArrayList <PacketData> packet;
		Iterator <Segment> it = figure.getSegment().iterator();
		Segment seg;

		int total = 0;
		double scalledY = 0.0d;
		
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
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
			
			if (packet == null) continue;
			size = packet.size();
			
			for (int i = 0; i < size; i++)
			{
				scalledY = (double) m.y + (packet.get(i).getPkY() - m.y) * scale;
				dist += this.getDistance(m, new Point(packet.get(i).getPkX(), (int)scalledY));
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
		int accRange = (int)(dist * 0.2d);
		Double accPercent = 0.5d;
		
		int current = 0;
		int [][] accu = new int[4][4]; // 4 rundy po 4 cwiartki
		int [] visited = new int[3];
		
		Double rad = 0.0d;
		it = figure.getSegment().iterator();
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
			size = packet.size();
			
			for (int i = 0; i < size; i++)
			{
				scalledY = (double) m.y + (packet.get(i).getPkY() - m.y) * scale;
				rad = this.getDistance(m, new Point(packet.get(i).getPkX(), (int)scalledY));
				if ((rad > dist - accRange) && (rad < dist + accRange))
				{
					accepted++;
				}
				div++;
				current++;
				
				int where = 0;
				if (current < (double)total * 0.25d) where = 0;
				else if (current < (double)total * 0.5d) where = 1;
				else if (current < (double)total * 0.75d) where = 2;
				else where = 3;
			
				if (packet.get(i).getPkX() < m.x)
				{
					if (packet.get(i).getPkY() < m.y) accu[where][0]++;
					else accu[where][1]++;
				}
				else
				{
					if (packet.get(i).getPkY() < m.y) accu[where][3]++;
					else accu[where][2]++;
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
			
			if ( (visited[1] == (visited[0] + 1) % 4) && 
					(visited[2] == (visited[1] + 1) % 4) )
			{
				return Figure.CIRCLELEFT;	
			}
			else return Figure.CIRCLERIGHT;	
			
		}
		
		
		return -1;
	}
	
	public boolean isSquare(Figure figure)
	{
		if (
				(!this.isInRange(figure.getBounds().width, figure.getBounds().height, 0.2d * figure.getBounds().height)) ||
				(!this.isInRange(figure.getBounds().height, figure.getBounds().width, 0.2d * figure.getBounds().width))
			){
			return false;
		}
		
		return true;
	}
	
	public boolean isInRange(double value, double base, double dev)
	{
		if ((value > base - dev) && 
				(value < base + dev)) return true;
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
	
	/*
	 public int spiralTest(Figure figure)
	{
		if (!this.isSquare(figure)) return -1;

		double scale = 1.0d;
		scale = (double) figure.getBounds().width / (double) figure.getBounds().height;
		
		Double dist = 0.0d;
		Point m = new Point(0, 0);
		int div = 0;
		int size = 0;
		
		ArrayList <PacketData> packet;
		Iterator <Segment> it = figure.getSegment().iterator();
		Segment seg;

		int total = 0;
		
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
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

		double scalledY = 0.0d;
		total = div;
		dist = 0.0d;
		it = figure.getSegment().iterator();
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
			size = packet.size();
			
			for (int i = 0; i < size; i++)
			{
				scalledY = (double) m.y + (packet.get(i).getPkY() - m.y) * scale;
				double d = this.getDistance(m, new Point(packet.get(i).getPkX(), (int) scalledY) );
				if (d > dist) dist = d;
			}
		}
		
		
		Double accRange = (dist * 0.20d);
		Double accPercent = 0.5d;
		Double shift = (dist / 8.0d);
		int downAccu = 0;
		int upAccu = 0;
		
		Double downA = (dist - shift) / (double) total;
		//System.out.printf(" (%f   %f) %d ",dist, downA, figure.getSegment().size());
		
		Double rad;
		it = figure.getSegment().iterator();
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
			size = packet.size();
			
			for (int i = 0; i < size; i++)
			{
				scalledY = (double) m.y + (packet.get(i).getPkY() - m.y) * scale;
				rad = this.getDistance(m, new Point(packet.get(i).getPkX(), (int)scalledY));
				
				if (this.isInRange(rad, dist - i * downA, accRange)) downAccu++;
				if (this.isInRange(rad, shift + i * downA, accRange)) upAccu++;
			}
		}
		
		double resultDown = (double) downAccu / (double) total;
		double resultUp = (double) upAccu / (double) total;
		
		System.out.printf("--- %f %f\n", resultDown, resultUp);
	
		
		if ((resultDown > resultUp) && (resultDown > accPercent))
		{
			System.out.printf(":: %d %d %d %d\n", m.x, m.y, figure.getBounds().x, figure.getBounds().y);
			return Figure.SPIRALIN;
	
		}
		else if ((resultUp > resultDown) && (resultUp > accPercent))
		{
			System.out.printf(":: %d %d %d %d\n", m.x, m.y, figure.getBounds().x, figure.getBounds().y);
			return Figure.SPIRALOUT;
		}

		return -1;
	}
	 */
	
	
	/*
	*/
	
	
	/*public int spiralTest(Figure figure)
	{
		Double dist = 0.0d;
		Point m = new Point(0, 0);
		int div = 0;
		int size = 0;
		
		ArrayList <PacketData> packet;
		Iterator <Segment> it = figure.getSegment().iterator();
		Segment seg;

		int total = 0;
		
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
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

		total = div;
		dist = 0.0d;
		div = 0;
		it = figure.getSegment().iterator();
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
			size = packet.size();
			
			for (int i = 0; i < size; i++)
			{
				dist += this.getDistance(m, new Point(packet.get(i).getPkX(), packet.get(i).getPkY()));
				div++;
			}
		}
		
		if (div != 0)
		{
			dist /= div;
		}
		// check if getting down / up

		int downAccu = 0;
		int upAccu = 0;
		Double accPercent = 0.65d;
		
		Double radA, radB;
		it = figure.getSegment().iterator();
		while (it.hasNext())
		{
			seg = it.next();
			packet = seg.getPacket();
			
			if (packet == null) continue;
			size = packet.size();
			
			for (int i = 1; i < size; i++)
			{
				radA = this.getDistance(m, new Point(packet.get(i - 1).getPkX(), packet.get(i - 1).getPkY()));
				radB = this.getDistance(m, new Point(packet.get(i).getPkX(), packet.get(i).getPkY()));
				
				if (radA > radB) downAccu++;
				else upAccu++;
				
			}
		}
		
		double resultDown = (double) downAccu / (double) total;
		double resultUp = (double) upAccu / (double) total;
		
		System.out.printf("--- %f %f\n", resultDown, resultUp);
		
		if (resultDown > accPercent)
		{
			return Figure.SPIRALIN;
		}
		else if (resultUp > accPercent)
		{
			return Figure.SPIRALOUT;
		}

		return -1;
	}*/
	
	

	/**/
}
