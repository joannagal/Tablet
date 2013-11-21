package pi.shared;

import java.awt.Color;

import pi.shared.schemes.Scheme;
import pi.shared.schemes.drawing.DrawingScheme;

//-------------------------------------------

public class SharedController
{
	private static SharedController instance = null;

	private Scheme currentScheme;
	private Scheme whiteScheme;
	private Scheme blackScheme;

	private SharedController()
	{
		this.createWhiteScheme();
		this.createBlackScheme();
		this.currentScheme = this.blackScheme;
	}

	public static SharedController getInstance()
	{
		if (instance == null)
			instance = new SharedController();
		return instance;
	}

	public void createBlackScheme()
	{
		Scheme blackScheme = new Scheme();
		DrawingScheme drawing = blackScheme.getDrawingScheme();
		drawing.setBackgroundColor(new Color(0, 0, 0, 255));
		drawing.setBorderColor(new Color(255, 255, 255, 255));
		drawing.setDrawingColor(new Color(255, 255, 255, 255));
		drawing.setAngleColor(new Color(0, 0, 255, 255));
		drawing.setLinearizedColor(new Color(255, 0, 0, 255));
		drawing.setFigureColor(new Color(0, 255, 0, 255));
		drawing.setAllColor(new Color(120, 120, 120, 255));
		drawing.setSelectionColor(new Color(255, 50, 50, 255));
		drawing.setLabelColor(new Color(255, 255, 255, 255));
		this.blackScheme = blackScheme;
	}

	public void createWhiteScheme()
	{
		Scheme whiteScheme = new Scheme();
		DrawingScheme drawing = whiteScheme.getDrawingScheme();
		drawing.setBackgroundColor(new Color(255, 255, 255, 255));
		drawing.setBorderColor(new Color(0, 0, 0, 255));
		drawing.setDrawingColor(new Color(0, 0, 0, 255));
		drawing.setAngleColor(new Color(0, 0, 255, 255));
		drawing.setLinearizedColor(new Color(255, 0, 0, 255));
		drawing.setFigureColor(new Color(0, 255, 0, 255));
		drawing.setAllColor(new Color(160, 160, 160, 255));
		drawing.setSelectionColor(new Color(255, 50, 50, 255));
		drawing.setLabelColor(new Color(0, 0, 0, 255));
		this.whiteScheme = whiteScheme;
	}

	public Scheme getCurrentScheme()
	{
		return this.currentScheme;
	}

	public void switchScheme()
	{
		if (this.currentScheme == this.whiteScheme)
			this.currentScheme = this.blackScheme;
		else
			this.currentScheme = this.whiteScheme;
	}
}
