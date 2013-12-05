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

