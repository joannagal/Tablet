package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

import pi.population.Population;
import pi.project.Project;

public class PopulationResult
{

	private Population population;
	private Map<String, SpecimenResult> value = new HashMap <String, SpecimenResult>();
	
	public PopulationResult(Population population)
	{
		this.population = population;
	}
	
	public void calculateResult()
	{
		int size = this.population.getSpecimen().size();
		
		for (int i = 0; i < size; i++)
		{
			SpecimenResult result = new SpecimenResult(this.population.getSpecimen().get(i));
			result.calculateResult();
			value.put(String.format("%d", i), result);
		}
	}
}
