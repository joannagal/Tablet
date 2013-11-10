package pi.graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.shared.schemes.drawing.DrawingScheme;

public class DrawingAdapter
{
	private Graph graph;
	private DrawingScheme scheme;
	
	private boolean thicknessShow = false;
	private boolean drawingAngleShow = false;
	private boolean linearizedShow = true;
	
	BasicStroke stroke[] = new BasicStroke[5];
	
	public DrawingAdapter(Graph graph)
	{
		this.graph = graph;
		
		for (int i = 0; i < 5; i++) 
		{
			stroke[i] = new BasicStroke(i + 1);
		}
	}
	
	public void draw(Graphics graphics, int time)
	{
		ArrayList <Figure> figure = this.graph.getDrawing().getFigure();
		int size = figure.size();
		
		
		for (int i = 0; i < size; i++)
		{
			this.drawFigure(graphics, figure.get(i), time);
		}
	}
	
	public void drawFigure(Graphics graphics, Figure figure, int time)
	{
		Drawing drawing = graph.getDrawing();
		Transformations transform = graph.getTranform();
		
		ArrayList <PacketData> packet = figure.getPacket();
		PacketData temp;
		int size = packet.size();
		
		Color drawColor = scheme.getDrawingColor();
		Color angleColor = scheme.getAngleColor();
		
		temp = packet.get(0);
		
		Point A = new Point();
		Point B = new Point();
		Point C = new Point();
		
		transform.transformToCanvas(temp.getPkX(), temp.getPkY(), A);
		
		Graphics2D tools = (Graphics2D) graphics;
		
		for (int i = 1; i < size; i++)
		{
			temp = packet.get(i);
			if (temp.getPkTime() > time) break;
			
			transform.transformToCanvas(temp.getPkX(), temp.getPkY(), B);
			
			if ((temp.getPkPressure() > drawing.getPressureAvoid()) && 
					(packet.get(i - 1).getPkPressure() > drawing.getPressureAvoid())) 
			{
				if (this.thicknessShow)
				{
					tools.setStroke(stroke[transform.transformToPressure(temp.getPkPressure())]);		
				}
				else 
				{
					tools.setStroke(stroke[0]);	
				}

					
				graphics.setColor(drawColor);
				graphics.drawLine(A.x, A.y, B.x, B.y);
				
				// ------------------------------
				
				if (this.drawingAngleShow)
				{
					tools.setStroke(stroke[0]);	
					graphics.setColor(angleColor);
					transform.transformAnglePointer(temp.getPkAzimuth(), temp.getPkAltitude(), C);
					graphics.drawLine(B.x, B.y, B.x + C.x, B.y - C.y);	
				}
			}
			
			A.x = B.x;
			A.y = B.y;
		}
		
		if (this.isLinearizedShow())
		{
			this.drawLinearized(graphics, figure, time);
		}
	
	}
	
	public void drawLinearized(Graphics graphics, Figure figure, int time)
	{
		Drawing drawing = graph.getDrawing();
		Transformations transform = graph.getTranform();
		
		ArrayList <PacketData> packet = figure.getLinearized();
		
		if (packet.isEmpty()) return;
		
		PacketData temp;
		int size = packet.size();
		
		Color drawColor = scheme.getLinearizedColor();
		graphics.setColor(drawColor);
		
		temp = packet.get(0);
		
		Point A = new Point();
		Point B = new Point();
		
		transform.transformToCanvas(temp.getPkX(), temp.getPkY(), A);
		
		Graphics2D tools = (Graphics2D) graphics;
		tools.setStroke(stroke[0]);	
		
		for (int i = 1; i < size; i++)
		{
			temp = packet.get(i);
			if (temp.getPkTime() > time) break;
			
			transform.transformToCanvas(temp.getPkX(), temp.getPkY(), B);
			
			if ((temp.getPkPressure() > drawing.getPressureAvoid()) && 
					(packet.get(i - 1).getPkPressure() > drawing.getPressureAvoid())) 
			{
				
				graphics.drawLine(A.x, A.y, B.x, B.y);
			}
			
			A.x = B.x;
			A.y = B.y;
		}
	}
	
	public void recalculate()
	{
		this.scheme = graph.getScheme();
	}

	public boolean isThickness()
	{
		return thicknessShow;
	}

	public void setThickness(boolean thickness)
	{
		this.thicknessShow = thickness;
	}

	public boolean isDrawingAngle()
	{
		return drawingAngleShow;
	}

	public void setDrawingAngle(boolean drawingAngle)
	{
		this.drawingAngleShow = drawingAngle;
	}

	public boolean isLinearizedShow()
	{
		return linearizedShow;
	}

	public void setLinearizedShow(boolean linearizedShow)
	{
		this.linearizedShow = linearizedShow;
	}
}
