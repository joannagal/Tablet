package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

import pi.population.Specimen;

public class SpecimenResult
{
	public Specimen specimen;
	private Map<String, DrawingResult> value = new HashMap <String, DrawingResult>();
	
	public SpecimenResult(Specimen specimen)
	{
		this.specimen = specimen;
	}
	
	public void calculateResult()
	{
		
		DrawingResult before = new DrawingResult(this.specimen.getBefore());
		before.calculateResult();
		getValue().put("Before", before);
		
		
		if (this.specimen.getAfter() != null)
		{
			DrawingResult after = new DrawingResult(this.specimen.getAfter());
			after.calculateResult();
			getValue().put("After", after);
		}
	
	}

	public Map<String, DrawingResult> getValue()
	{
		return value;
	}

	public void setValue(Map<String, DrawingResult> value)
	{
		this.value = value;
	}

	
}
