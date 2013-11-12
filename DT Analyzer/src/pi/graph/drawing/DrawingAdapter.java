package pi.graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.shared.schemes.drawing.DrawingScheme;

public class DrawingAdapter
{
	private Graph graph;
	private DrawingScheme scheme;

	private boolean thicknessShow = false;
	private boolean drawingAngleShow = false;
	private boolean linearizedShow = true;
	private boolean signalShow = true;

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
		ArrayList<Figure> figure = this.graph.getDrawing().getFigure();
		int size = figure.size();

		if (size == 1) return;
		
		for (int i = 0; i < size; i++)
		{
			this.drawFigure(graphics, figure.get(i), time);
		}
	}

	public void drawFigure(Graphics graphics, Figure figure, int time)
	{
		
		if ((figure.getBounds() == null) || (figure.getSegment() == null)
				|| (figure.getSegment().size() == 0))
			return;

		Iterator<Segment> it = figure.getSegment().iterator();
		Segment seg;

		Transformations transform = graph.getTranform();
		Graphics2D tools = (Graphics2D) graphics;
		Color figureColor = scheme.getFigureColor();
		graphics.setColor(figureColor);
		tools.setStroke(stroke[0]);

		Rectangle bounds = figure.getBounds();
		Rectangle tBounds = new Rectangle(bounds.x, bounds.y, bounds.width,
				bounds.height);
		transform.transformToCanvas(tBounds);
		graphics.drawRect(tBounds.x, tBounds.y, tBounds.width, tBounds.height);

		String figString = "";
		
		switch (figure.getType())
		{
			case Figure.DEFAULT: figString = "UNKNOWN";	break;
			case Figure.ZIGZAG: figString = "ZIGZAG"; break;
			case Figure.CIRCLELEFT: figString = "CIRCLE-LEFT"; break;
			case Figure.CIRCLERIGHT: figString = "CIRCLE-RIGHT"; break;
			case Figure.FIRSTLINE: figString = "FIRST-LINE"; break;
			case Figure.SECONDLINE: figString = "SECOND-LINE"; break;
			case Figure.BROKENLINE: figString = "BROKEN-LINE"; break;
			case Figure.SPIRALIN: figString = "SPIRAL-IN"; break;
			case Figure.SPIRALOUT: figString = "SPIRAL-OUT"; break;
		}
		
		graphics.drawString(figString, tBounds.x, tBounds.y - 15);
		
		
		while (it.hasNext())
		{
			seg = it.next();
			
			if (this.isSignalShow())
			{
				this.drawSegment(graphics, seg.getPacket(), time, false);		
			}
			
			/*if (this.isLinearizedShow()) 
			{ 
				this.drawSegment(graphics, seg.getLinearized(), time, true);
			}*/
		}
	}

	public void drawSegment(Graphics graphics, ArrayList<PacketData> packet,
			int time, boolean isLinearized)
	{
		Transformations transform = graph.getTranform();
		PacketData pck;

		int size = packet.size();
		if (size < 3)
			return;

		Graphics2D tools = (Graphics2D) graphics;

		Color drawColor = scheme.getDrawingColor();
		Color angleColor = scheme.getAngleColor();
		pck = packet.get(0);

		Point A = new Point();
		Point B = new Point();
		Point C = new Point();

		transform.transformToCanvas(pck.getPkX(), pck.getPkY(), A);
		
		if (isLinearized)
		{
			graphics.setColor(scheme.getLinearizedColor());
			tools.setStroke(stroke[0]);
		}

		for (int i = 1; i < size; i++)
		{
			pck = packet.get(i);
			if (pck.getPkTime() > time)
				break;

			transform.transformToCanvas(pck.getPkX(), pck.getPkY(), B);

			if (!isLinearized)
			{
				if (this.thicknessShow)
					tools.setStroke(stroke[transform.transformToPressure(pck
							.getPkPressure())]);
				else
					tools.setStroke(stroke[0]);

				graphics.setColor(drawColor);
			}

			graphics.drawLine(A.x, A.y, B.x, B.y);

			if (!isLinearized)
			{
				if (this.drawingAngleShow)
				{
					tools.setStroke(stroke[0]);
					graphics.setColor(angleColor);
					transform.transformAnglePointer(pck.getPkAzimuth(),
							pck.getPkAltitude(), C);
					graphics.drawLine(B.x, B.y, B.x + C.x, B.y - C.y);
				}
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

	public boolean isSignalShow()
	{
		return signalShow;
	}

	public void setSignalShow(boolean signalShow)
	{
		this.signalShow = signalShow;
	}
}
