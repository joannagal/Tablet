package pi.graph.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.shared.schemes.drawing.DrawingScheme;
import pi.statistics.logic.StatMapper;
import pi.utilities.Range;

public class DrawingAdapter
{
	private Graph graph;
	private DrawingScheme scheme;

	private boolean thicknessShow = false;
	private boolean drawingAngleShow = false;
	private boolean linearizedShow = false;
	private boolean signalShow = false;

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

		if (size == 1)
			return;

		if (this.signalShow)
		{
			this.drawSegment(graphics, graph.getDrawing().getPacket(),
					new Range(0, graph.getDrawing().getPacket().size() - 1),
					time, true, false);
		}

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

		figString = StatMapper.figureNames[figure.getType()];

		graphics.drawString(figString, tBounds.x, tBounds.y - 5);

		while (it.hasNext())
		{
			seg = it.next();
			this.drawSegment(graphics, figure.getParent().getPacket(),
					seg.getRange(), time, false, false);
		}
	}

	public void drawSegment(Graphics graphics, ArrayList<PacketData> packet,
			Range range, int time, boolean isAll, boolean isLinearized)
	{
		Transformations transform = graph.getTranform();
		PacketData pck;

		int size = packet.size();
		if (size < 3)
			return;

		Graphics2D tools = (Graphics2D) graphics;

		Color drawColor = scheme.getDrawingColor();
		Color angleColor = scheme.getAngleColor();
		pck = packet.get(range.getLeft());

		Point A = new Point();
		Point B = new Point();
		Point C = new Point();

		transform.transformToCanvas(pck.getPkX(), pck.getPkY(), A);

		if (isLinearized)
		{
			graphics.setColor(scheme.getLinearizedColor());
			tools.setStroke(stroke[0]);
		} else if (isAll)
		{
			graphics.setColor(scheme.getAllColor());
			tools.setStroke(stroke[0]);
		}

		for (int i = range.getLeft() + 1; i <= range.getRight(); i++)
		{
			pck = packet.get(i);
			if (pck.getPkTime() > time)
				break;

			transform.transformToCanvas(pck.getPkX(), pck.getPkY(), B);

			if (!isLinearized)
			{
				if (!isAll)
				{
					if (this.thicknessShow)
					{
						tools.setStroke(stroke[transform
								.transformToPressure(pck.getPkPressure())]);

						graphics.setColor(getGradientColor(this.graph
								.getDrawing().getPressureAvoid(), 1024, pck
								.getPkPressure()));
					} else
					{
						tools.setStroke(stroke[0]);
						graphics.setColor(drawColor);
					}

				} else
				{
					Range selection = graph.getSelection();
					if ((pck.getPkTime() >= selection.getLeft())
							&& (pck.getPkTime() <= selection.getRight()))
					{
						graphics.setColor(scheme.getSelectionColor());
					} else
						graphics.setColor(scheme.getAllColor());
				}
			}

			graphics.drawLine(A.x, A.y, B.x, B.y);

			if ((!isAll) && (!isLinearized))
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

	public Color getGradientColor(double min, double max, double value)
	{
		double d = max - min;
		value -= min;

		double R = 0.0d;
		double G = 0.0d;
		double B = 0.0d;

		double firstStep = d * 0.33d;
		double secondStep = d * 0.66d;

		if ((value < 0.0d) || (d < 0.0d))
		{
			R = 0.0d;
			G = 0.0d;
			B = 0.0d;
		} else if (value < firstStep)
		{
			double p = value / firstStep;
			B = 255.0d * (1.0d - p);
			G = 255.0d * p;
		} else if (value < secondStep)
		{
			double p = (value - firstStep) / (secondStep - firstStep);
			G = 255.0d;
			R = 255.0d * p;
		} else
		{
			double p = (value - secondStep) / (d - secondStep);
			G = 255.0d * (1.0d - p);
			R = 255.0d;
		}

		Color result = new Color((int) R, (int) G, (int) B);
		return result;
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
