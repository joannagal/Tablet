package pi.graph.drawing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import pi.inputs.drawing.Drawing;
import pi.shared.SharedController;
import pi.shared.schemes.drawing.DrawingScheme;

public class Graph extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Drawing drawing;
	private DrawingScheme scheme;
	private SharedController controller;
	private Transformations tranform;
	private DrawingAdapter adapter;
	
	private int currentTime;
	
	public Graph(Dimension size, Drawing drawing)
	{
		this.controller = SharedController.getInstance();
		this.setSize(size);
		this.setDrawing(drawing);
		this.tranform = new Transformations(this);
		this.adapter = new DrawingAdapter(this);
	}


	@Override
	public void paintComponent(Graphics graphics)
	{
		this.drawBackground(graphics);
		this.drawBorder(graphics);
		if (this.drawing != null) 
		{
			this.adapter.draw(graphics, this.currentTime);
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
	
	// ------------------------------------------
	// RECALCULATE ALL GRAPH
	public void recalculate()
	{
		this.setScheme(this.controller.getScheme().getDrawingScheme());
		
		if (this.drawing != null)
		{
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


	public Drawing getDrawing()
	{
		return drawing;
	}


	public void setDrawing(Drawing drawing)
	{
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


}
