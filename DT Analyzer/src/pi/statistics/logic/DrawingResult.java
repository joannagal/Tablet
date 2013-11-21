package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

import pi.inputs.drawing.Drawing;

public class DrawingResult
{
	private Map<String, FigureResult> value = new HashMap<String, FigureResult>();
	private Drawing drawing;
	
	public DrawingResult(Drawing drawing)
	{
		this.drawing = drawing;
	}
	
	public void calculateResult()
	{
		for (int i = 0; i < 8; i++)
		{
			if (this.drawing.getCompleteFigure()[i] == null) continue;
			
			FigureResult figResult = new FigureResult(this.drawing.getCompleteFigure()[i]);
			
			String label = "";
			switch (i)
			{
			case 0: label = "ZigZag"; break;
			case 1: label = "Circle-Left"; break;
			case 2: label = "Circle-Right"; break;
			case 3: label = "First Line"; break;
			case 4: label = "Second Line"; break;
			case 5: label = "Broken Line"; break;
			case 6: label = "Spiral-In"; break;
			case 7: label = "Spiral-Out"; break;
			}
			
			figResult.calculateResult();
			value.put(label, figResult);
		}
	}
	
	public Map<String, FigureResult> getValue()
	{
		return value;
	}

	public void setValue(Map<String, FigureResult> value)
	{
		this.value = value;
	}

}

