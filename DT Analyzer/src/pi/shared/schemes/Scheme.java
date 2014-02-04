package pi.shared.schemes;

import pi.shared.schemes.drawing.DrawingScheme;


public class Scheme
{
	private DrawingScheme drawingScheme;

	public Scheme()
	{
		drawingScheme = new DrawingScheme();
	}

	public DrawingScheme getDrawingScheme()
	{
		return drawingScheme;
	}

	public void setDrawingScheme(DrawingScheme drawingScheme)
	{
		this.drawingScheme = drawingScheme;
	}

}
