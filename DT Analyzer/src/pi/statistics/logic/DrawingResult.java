package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

import pi.inputs.drawing.Drawing;

public class DrawingResult
{
	private Map<String, FigureResult> value;
	private Drawing drawing;
	
	public DrawingResult(Drawing drawing)
	{
		this.drawing = drawing;
		drawing.createStatus();
	}
	
	public void calculateResult()
	{
		this.value = new HashMap<String, FigureResult>();
		this.drawing.linearize(10);
		
		for (int i = 0; i < 8; i++)
		{
			if (this.drawing.getCompleteFigure()[i] == null) continue;
			if (!StatMapper.figureAvaible.get(StatMapper.figureNames[i])) continue;
			
			FigureResult figResult = new FigureResult(this.drawing.getCompleteFigure()[i]);
			
			String label = StatMapper.figureNames[i];
			
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

