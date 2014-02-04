package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

import pi.inputs.drawing.Drawing;

public class DrawingResult
{
	private Map<String, FigureResult> value;
	private Drawing drawing;

	public void clearMemory()
	{
		if (this.value != null)
		{
			for (Map.Entry<String, FigureResult> entry : this.value.entrySet())
			{
				entry.getValue().clearMemory();
			}

			this.value = null;
		}
	}

	public DrawingResult(Drawing drawing)
	{
		this.drawing = drawing;
	}

	public void calculateResult(boolean projectLevel)
	{
		this.value = new HashMap<String, FigureResult>();
		this.drawing.linearize(30);

		for (int i = 0; i < StatMapper.figureNames.length; i++)
		{
			if (projectLevel)
			{
				if (!StatMapper.figureAvaible.get(StatMapper.figureNames[i]))
					continue;
			}
			if (this.drawing.getCompleteFigure()[i] == null)
				continue;

			FigureResult figResult = new FigureResult(
					this.drawing.getCompleteFigure()[i]);
			String label = StatMapper.figureNames[i];
			figResult.calculateResult(projectLevel);
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
