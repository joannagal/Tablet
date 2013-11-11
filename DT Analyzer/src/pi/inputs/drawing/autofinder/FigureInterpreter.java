package pi.inputs.drawing.autofinder;

import java.util.ArrayList;

import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;

public class FigureInterpreter
{
	public void interprate(Drawing drawing)
	{
		/*ArrayList <Figure> figure = drawing.getFigure();
		Iterator <Figure> it = figure.iterator();
		
		Figure fig;
		
		while (it.hasNext())
		{
			fig = it.next();
			this.interprateFigure(fig);
		}*/
		
	}

	public void interprateFigure(Figure figure)
	{
		/*if ((figure.getPacket() == null) || 
				(figure.getPacket().size() < 5)) return;
		
		ArrayList <Double> angleDeriv = this.getAngleDeriv(figure.getPacket());*/
		
		//ArrayList <PacketData> linearized = new ArrayList <PacketData> ();
		//figure.setLinearized(linearized);
		
		/*ArrayList <PacketData> packet = figure.getPacket();
		int size = packet.size();
		
		ArrayList <Double> angleDeriv = new ArrayList <Double> (size - 2);
		
		double dx, dy;
		Double A, B;
		for (int i = 1; i < size - 1; i++)
		{
			dx = packet.get(i).getPkX() - packet.get(i - 1).getPkX();
			dy = packet.get(i).getPkY() - packet.get(i - 1).getPkY();
			A = new Double(this.getAngle(dx, dy));
			dx = packet.get(i + 1).getPkX() - packet.get(i).getPkX();
			dy = packet.get(i + 1).getPkY() - packet.get(i).getPkY();
			B = new Double(this.getAngle(dx, dy));
			angleDeriv.add(this.difAngles(A, B));
		}*/
		
	}
	
	public ArrayList <Double> getAngleDeriv(ArrayList<PacketData> packet)
	{
		int size = packet.size();
		ArrayList <Double> angleDeriv = new ArrayList <Double> (size - 2);
		
		double dx, dy;
		Double A, B;
		for (int i = 1; i < size - 1; i++)
		{
			dx = packet.get(i).getPkX() - packet.get(i - 1).getPkX();
			dy = packet.get(i).getPkY() - packet.get(i - 1).getPkY();
			A = new Double(this.getAngle(dx, dy));
			dx = packet.get(i + 1).getPkX() - packet.get(i).getPkX();
			dy = packet.get(i + 1).getPkY() - packet.get(i).getPkY();
			B = new Double(this.getAngle(dx, dy));
			angleDeriv.add(this.difAngles(A, B));
			
		//	System.out.printf("%f ", this.difAngles(A, B));
		}
		//System.out.printf("\n");
		
		return angleDeriv;
	}

	public double difAngles(double A, double B)
	{
		double result = A - B;
		
		if (result < 0) {
			if (result < -180.0d) result = 360.0d + result;
			else result *= -1;
		}
		else if (result > 180.0d) result = 360.0d - result;
		
		return result;
	}
	
	public double getAngle(double dx, double dy)
	{		
		if ((dx > -0.001d) && (dx < 0.001d))
		{
			if (dy >= 0) return 90.0d;
			else return 270.0d;
		}
		else 
		{
			double x = (double) dx;
			if (x < 0) x *= -1.0d;
			
			double y = (double) dy;
			if (y < 0) y *= -1.0d;
			
			double angle = Math.atan(y / x);
			angle /= (Math.PI / 180.0d);
			
			if (dx < 0)
			{
				if (dy > 0) return (180.0d  - angle);
				else return 180.0d + angle;
			}
			else
			{
				if (dy > 0) return angle;
				else return 360.0d - angle;
			}
		}	
	}
	
}
