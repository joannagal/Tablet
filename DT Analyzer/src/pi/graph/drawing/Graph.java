package pi.graph.drawing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import pi.graph.drawing.popup.PopUp;
import pi.inputs.drawing.Drawing;
import pi.inputs.drawing.Figure;
import pi.shared.SharedController;
import pi.shared.schemes.drawing.DrawingScheme;
import pi.utilities.Range;

public class Graph extends JPanel implements MouseMotionListener
{
	private static final long serialVersionUID = 1L;

	private Drawing drawing;
	private DrawingScheme scheme;
	private SharedController controller;
	private Transformations tranform;
	private DrawingAdapter adapter;
	private PopUp popUp;

	private Range selection = new Range(0, 1);

	private int currentTime;

	public Graph(Dimension size)
	{
		this.controller = SharedController.getInstance();
		this.setSize(size);
		this.tranform = new Transformations(this);
		this.adapter = new DrawingAdapter(this);
		this.popUp = new PopUp(this);
		this.setComponentPopupMenu(this.popUp);
		this.addMouseMotionListener(this);

	}

	@Override
	public void paintComponent(Graphics graphics)
	{
		this.drawBackground(graphics);
		this.drawBorder(graphics);
		if (this.drawing != null)
		{
			if (this.drawing.getFigure() == null)
				return;
			this.adapter.draw(graphics, this.currentTime);
		}
		this.drawLabel(graphics);
	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{

	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		// new Point(arg0.getPoint().x, arg0.getPoint().y)
		popUp.setPoint(arg0.getPoint());
	}

	// ------------------------------------------
	// SIMPLE DRAWING BACKGROUND
	public void drawLabel(Graphics graphics)
	{
		if (this.drawing != null)
		{
			graphics.setColor(getScheme().getLabelColor());
			graphics.drawString(this.drawing.getLabel(), 5, 15);
		}
	}

	// ------------------------------------------
	// SIMPLE DRAWING BORDER
	public void drawBorder(Graphics graphics)
	{
		Rectangle frame = this.getBounds();
		graphics.setColor(getScheme().getBorderColor());
		graphics.drawRect(0, 0, frame.width - 1, frame.height - 1);
	}

	// ------------------------------------------
	// SIMPLE DRAWING BACKGROUND
	public void drawBackground(Graphics graphics)
	{
		Rectangle frame = this.getBounds();
		graphics.setColor(getScheme().getBackgroundColor());
		graphics.fillRect(0, 0, frame.width - 1, frame.height - 1);
	}

	// ------------------------------------------
	public void draw()
	{
		this.repaint();
	}

	public void deleteFigure(int x, int y)
	{
		if (this.drawing == null)
			return;
		if (this.drawing.getFigure() == null)
			return;

		Point p = new Point();
		this.tranform.transformToRealPoint(x, y, p);

		ArrayList<Figure> figure = this.drawing.getFigure();
		int size = figure.size();

		for (int i = 0; i < size; i++)
		{
			if (figure.get(i).getBounds() == null)
				continue;
			if (this.tranform.isInside(figure.get(i).getBounds(), p))
			{
				figure.remove(i);
				this.recalculate();
				this.draw();
				return;
			}
		}

	}

	// ------------------------------------------
	// RECALCULATE ALL GRAPH
	public void recalculate()
	{
		this.setScheme(this.controller.getCurrentScheme().getDrawingScheme());

		if (this.drawing != null)
		{
			if (this.drawing.getFigure() == null)
				return;
			this.tranform.recalculate();
			this.adapter.recalculate();
		}
	}

	public void setLinearShow(boolean value)
	{
		this.adapter.setLinearizedShow(value);
	}

	public void setDrawingAngleShow(boolean value)
	{
		this.adapter.setDrawingAngle(value);
	}

	public void setThicknessShow(boolean value)
	{
		this.adapter.setThickness(value);
	}

	public void setSignalShow(boolean value)
	{
		this.adapter.setSignalShow(value);
	}

	public Drawing getDrawing()
	{
		return drawing;
	}

	public void setDrawing(Drawing drawing)
	{
		if (drawing != null)
		{
			this.selection.setRange(drawing.getTotalTime(),
					drawing.getTotalTime() + 1);
			
			// DEB
			
			System.out.printf("-DEBUG ---\n");
			//drawing.recalculate(true);
			//System.out.printf("-%d %d\n", drawing.);
			//drawing.linearize(10);
			
		}
		else
			this.selection.setRange(100, 101);
		this.drawing = drawing;
	}

	public Transformations getTranform()
	{
		return tranform;
	}

	public void setTranform(Transformations tranform)
	{
		this.tranform = tranform;
	}

	public DrawingAdapter getAdapter()
	{
		return adapter;
	}

	public void setAdapter(DrawingAdapter adapter)
	{
		this.adapter = adapter;
	}

	public DrawingScheme getScheme()
	{
		return scheme;
	}

	public void setScheme(DrawingScheme scheme)
	{
		this.scheme = scheme;
	}

	public int getCurrentTime()
	{
		return currentTime;
	}

	public void setCurrentTime(int currentTime)
	{
		this.currentTime = currentTime;
		this.draw();
	}

	public Range getSelection()
	{
		return selection;
	}

	public void setSelection(Range selection)
	{
		this.selection = selection;
	}

}
