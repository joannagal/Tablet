package pi.statistics.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import pi.project.Project;
import pi.shared.SharedController;

public class ProjectResult
{
	private Project project;
	private Map<String, PopulationResult> value;

	// 1. before/after
	// 2. figure
	// 3. attribute
	// 4. statistic
	private Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> testResult;

	public ProjectResult(Project project)
	{
		this.project = project;
	}

	public void doTests()
	{
		this.testResult = null;
		// 1. before/after
		// 2. figure
		// 3. attribute
		// 4. statistic

		boolean after = false;
		int type = SharedController.getInstance().getProject().getType();
		if ((type == Project.POPULATION_PAIR)
				|| (type == Project.SPECIMEN_PAIR))
			after = true;

		if (after)
		{
			String[] maps =
			{ "P1AB", "P2AB", "BB", "AA", "dAB",  };
			this.testResult = StatMapper.getMap(maps);
			this.performTest("First", "Before", "First", "After",
					this.testResult.get("P1AB"));
			this.performTest("Second", "Before", "Second", "After",
					this.testResult.get("P2AB"));
			this.performTest("First", "Before", "Second", "Before",
					this.testResult.get("BB"));
			this.performTest("First", "After", "Second", "After",
					this.testResult.get("AA"));
			this.performTest("First", "Diff", "Second", "Diff",
					this.testResult.get("dAB"));
		
		} else
		{
			String[] maps =
			{ "BB" };
			this.testResult = StatMapper.getMap(maps);
			this.performTest("First", "Before", "Second", "Before",
					this.testResult.get("BB"));
		}
	}

	public void performTest(String fP, String fS, String sP, String sS,
			Map<String, Map<String, Map<String, LinkedList<Double>>>> map)
	{
		PopulationResult first = this.value.get(fP);
		PopulationResult second = this.value.get(sP);

		Map<String, Map<String, Map<String, LinkedList<Double>>>> fMap = first
				.getData().get(fS);
		Map<String, Map<String, Map<String, LinkedList<Double>>>> sMap = second
				.getData().get(sS);

		LinkedList<Double> fList;
		LinkedList<Double> sList;
		LinkedList<Double> result;

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				for (int k = 0; k < 12; k++)
				{
					fList = fMap.get(StatMapper.figureNames[i])
							.get(StatMapper.attributeNames[j])
							.get(StatMapper.statisticNames[k]);
					sList = sMap.get(StatMapper.figureNames[i])
							.get(StatMapper.attributeNames[j])
							.get(StatMapper.statisticNames[k]);

					if ((fList.isEmpty()) || (sList.isEmpty()))
						continue;

					//System.out.printf("SIZE: %d %s\n", fList.size(), sList.size());
					
					// ----- PERFORM ACTION WITH THIS LISTS :D
					// DUMMY ACTION;
					result = map.get(StatMapper.figureNames[i])
							.get(StatMapper.attributeNames[j])
							.get(StatMapper.statisticNames[k]);

					result.add(fList.get(0));
					// ----
				}
			}
		}
	}

	public void calculateResult()
	{
		this.value = new HashMap<String, PopulationResult>();
		
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
		this.doTests();
	}

	public Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> getTestResult()
	{
		return testResult;
	}

	public void setTestResult(
			Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> testResult)
	{
		this.testResult = testResult;
	}

	public Map<String, PopulationResult> getValue()
	{
		return value;
	}

	public void setValue(Map<String, PopulationResult> value)
	{
		this.value = value;
	}

}
