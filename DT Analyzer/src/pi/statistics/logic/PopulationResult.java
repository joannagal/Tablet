package pi.statistics.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import pi.population.Population;
import pi.population.Specimen;
import pi.project.Project;
import pi.shared.SharedController;

public class PopulationResult
{

	private Population population;
	private Map<String, SpecimenResult> value;

	// 1. before/after
	// 2. figure
	// 3. attribute
	// 4. statistic
	private Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> data;

	public PopulationResult(Population population)
	{
		this.population = population;
	}

	public void calculateResult()
	{
		this.value = new HashMap<String, SpecimenResult>();
		
		int size = this.population.getSpecimen().size();

		for (int i = 0; i < size; i++)
		{	
			Specimen specimen = this.population.getSpecimen().get(i);
			specimen.calculateStatistic();
			value.put(String.format("%d", i), specimen.getResult());
		}

		this.calculateDataVectors();
	}

	public void calculateDataVectors()
	{
		this.data = null;

		boolean after = false;
		int type = SharedController.getInstance().getProject().getType();
		if ((type == Project.POPULATION_PAIR)
				|| (type == Project.SPECIMEN_PAIR))
			after = true;

		if (after)
		{
			String[] maps =
			{ "Before", "After", "Diff" };
			this.data = StatMapper.getMap(maps);
		} else
		{
			String[] maps =
			{ "Before" };
			this.data = StatMapper.getMap(maps);
		}

		int size = this.population.getSpecimen().size();
		System.out.printf("POPUL SIZE: %d\n", size);
		
		for (int i = 0; i < size; i++)
		{
			Specimen spec = this.population.getSpecimen().get(i);
			
			this.fillForSpecimen("Before", spec);
			if (after)
				this.fillForSpecimen("After", spec);

		}

		if (after)
		{
			this.calculateDiff();
		}
	}

	public void fillForSpecimen(String side, Specimen spec)
	{
		Map<String, Map<String, Map<String, LinkedList<Double>>>> figureMap = this.data
				.get(side);
		LinkedList<Double> list;

		SpecimenResult specimenResult = spec.getResult();
		
		if (specimenResult == null) return;

		// drawing
		DrawingResult drawingResult = specimenResult.getValue().get(side);
		if (drawingResult == null)
			return;

		int cnt = 0;
		
		// figures
		for (int i = 0; i < 8; i++)
		{
			FigureResult figureResult = drawingResult.getValue().get(
					StatMapper.figureNames[i]);
			if (figureResult == null)
				continue;

			for (int j = 0; j < 5; j++)
			{
				AttributeResult attributeResult = figureResult.getValue().get(
						StatMapper.attributeNames[j]);
				if (attributeResult == null)
					continue;

				for (int k = 0; k < 12; k++)
				{
					StatisticResult statisticResult = attributeResult
							.getValue().get(StatMapper.statisticNames[k]);
					if (statisticResult == null) continue;
					
					list = figureMap.get(StatMapper.figureNames[i])
							.get(StatMapper.attributeNames[j])
							.get(StatMapper.statisticNames[k]);
					
					double value = statisticResult.getValue().get(0);
					list.addLast(value);
					
					cnt++;
				}
			}
		}
	
	}

	public void calculateDiff()
	{
		Map<String, Map<String, Map<String, LinkedList<Double>>>> beforeMap = this.data
				.get("Before");

		LinkedList<Double> beforeList;

		Map<String, Map<String, Map<String, LinkedList<Double>>>> afterMap = this.data
				.get("After");

		LinkedList<Double> afterList;

		Map<String, Map<String, Map<String, LinkedList<Double>>>> diffMap = this.data
				.get("Diff");

		LinkedList<Double> diffList;

		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				for (int k = 0; k < 12; k++)
				{
					beforeList = beforeMap.get(StatMapper.figureNames[i])
							.get(StatMapper.attributeNames[j])
							.get(StatMapper.statisticNames[k]);
					afterList = afterMap.get(StatMapper.figureNames[i])
							.get(StatMapper.attributeNames[j])
							.get(StatMapper.statisticNames[k]);
					diffList = diffMap.get(StatMapper.figureNames[i])
							.get(StatMapper.attributeNames[j])
							.get(StatMapper.statisticNames[k]);

					Iterator<Double> B = beforeList.iterator();
					Iterator<Double> A = afterList.iterator();

					while ((B.hasNext()) && (A.hasNext()))
					{
						Double rA = A.next();
						Double rB = B.next();
						diffList.add(rA - rB);
					}
				}
			}
		}

	}

	public Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> getData()
	{
		return data;
	}

	public void setData(
			Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> data)
	{
		this.data = data;
	}

}
