package pi.shared;

import java.awt.Color;

import pi.shared.schemes.Scheme;
import pi.shared.schemes.drawing.DrawingScheme;

//-------------------------------------------


public class SharedController
{
	private static SharedController instance = null;
	
	private Scheme scheme;
	
	private SharedController()
	{
		this.createScheme();
	}
	
	public static  SharedController getInstance()
	{
		if (instance == null) instance = new SharedController();
		return instance;
	}
	
	public void createScheme()
	{
		setScheme(new Scheme());

		DrawingScheme drawing = getScheme().getDrawingScheme();
		drawing.setBackgroundColor(new Color(255, 255, 255, 255));
		drawing.setBorderColor(new Color(0, 0, 0, 255));
		drawing.setDrawingColor(new Color(0, 0, 0, 255));
		drawing.setAngleColor(new Color(0,0,255,255));
		drawing.setLinearizedColor(new Color(255, 0, 0, 255));
	}

	public Scheme getScheme()
	{
		return scheme;
	}

	public void setScheme(Scheme scheme)
	{
		this.scheme = scheme;
	}
	
}
