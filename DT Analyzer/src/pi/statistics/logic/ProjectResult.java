package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

import pi.project.Project;

public class ProjectResult
{
	private Project project;
	private Map<String, PopulationResult> value = new HashMap<String, PopulationResult>();

	public ProjectResult(Project project)
	{
		this.project = project;
	}

	public void calculateResult()
	{
		PopulationResult first = new PopulationResult(
				this.project.getFirstPopulation());
		first.calculateResult();
		value.put("First", first);

		if (this.project.getSecondPopulation() != null)
		{
			PopulationResult second = new PopulationResult(
					this.project.getSecondPopulation());
			second.calculateResult();
			value.put("Second", second);
		}

		this.project.setResult(this);

	}

}
