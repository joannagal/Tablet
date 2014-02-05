package pi.statistics.logic.report;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;

import pi.shared.SharedController;
import pi.statistics.logic.ProjectResult;
import pi.statistics.logic.StatMapper;

public class PopulStatistic
{

	private int sampleSize;
	private String firstSample;
	private String secondSample;

	private String tableName;
	private String figureName;
	private String attributeName;

	private String statisticName;
	private double firstVariance;
	private double firstDeviation;
	private double secondVariance;
	private double secondDeviation;
	private double p_value;

	@SuppressWarnings("rawtypes")
	public static Collection getPopulStatistics()
	{
		ProjectResult projectResult = SharedController.getInstance()
				.getProject().getResult();

		Vector<PopulStatistic> statistics = new Vector<PopulStatistic>(3
				* StatMapper.figureNames.length
				* StatMapper.attributeNames.length
				* StatMapper.statisticNames.length);

		Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> map = projectResult
				.getTestResult();

		String[] names =
		{ "P1AB", "P2AB", "dAB", "BB", "AA" };

		for (int c = 0; c < 5; c++)
		{
			Map<String, Map<String, Map<String, LinkedList<Double>>>> columnEntry = map
					.get(names[c]);
			if (columnEntry == null)
				continue;
			
			int s1 = SharedController.getInstance().getProject()
					.getFirstPopulation().getSpecimen().size();
			int max = s1;
			
			if (SharedController.getInstance().getProject()
					.getSecondPopulation() != null)
			{
				int s2 = SharedController.getInstance().getProject()
						.getSecondPopulation().getSpecimen().size();
				if (s2 > max) max = s2;
			}
			
			String firstName = SharedController.getInstance().getProject()
					.getFirstPopulation().getName();
			
			String secondName = "";
			if (SharedController.getInstance().getProject()
					.getSecondPopulation() != null)
			{
				secondName = SharedController.getInstance().getProject()
						.getSecondPopulation().getName();
			}
			

			// dla kazdej figury...
			for (int i = 0; i < StatMapper.figureNames.length; i++)
			{
				if (columnEntry.get(StatMapper.figureNames[i]) == null)
					continue;

				// wyciagamy kazdy atrybut...
				for (int j = 0; j < StatMapper.attributeNames.length; j++)
				{
					// ... dla nie kadza state ...
					for (int k = 0; k < StatMapper.statisticNames.length; k++)
					{
						if (columnEntry.get(StatMapper.figureNames[i]).get(
								StatMapper.attributeNames[j]) == null)
							continue;

						LinkedList<Double> list = columnEntry
								.get(StatMapper.figureNames[i])
								.get(StatMapper.attributeNames[j])
								.get(StatMapper.statisticNames[k]);

						if (list == null)
							continue;

						if (list.size() < 1)
							continue;

						PopulStatistic ps = new PopulStatistic();
						ps.setFirstSample(firstName);
						ps.setSecondSample(secondName);
						ps.setSampleSize(max);
						
						
						String name = "";
						if (names[c].equals("P1AB"))
							name = SharedController.getInstance().getProject()
									.getFirstPopulation().getName();
						
						else if (names[c].equals("P2AB"))
							name = SharedController.getInstance().getProject()
									.getSecondPopulation().getName();
						else if (names[c].equals("dAB"))
							name = "Differences between changes in populations";
						else if (names[c].equals("AA"))
							name = "Differences between populations (Before)";
						else if (names[c].equals("BB"))
							name = "Differences between populations (After)";

						ps.setTableName(name);
						ps.setFigureName(StatMapper.figureNames[i]);
						ps.setAttributeName(StatMapper.attributeNames[j]);
						ps.setStatisticName(StatMapper.statisticNames[k]);

						ps.setFirstVariance(list.get(0));
						ps.setFirstDeviation(list.get(1));
						ps.setSecondVariance(list.get(2));
						ps.setSecondDeviation(list.get(3));
						ps.setP_value(list.get(6));
						statistics.add(ps);

					}
				}
			}

		}


		return statistics;
	}



	public int getSampleSize()
	{
		return sampleSize;
	}

	public void setSampleSize(int sampleSize)
	{
		this.sampleSize = sampleSize;
	}

	public String getFirstSample()
	{
		return firstSample;
	}

	public void setFirstSample(String firstSample)
	{
		this.firstSample = firstSample;
	}

	public String getSecondSample()
	{
		return secondSample;
	}

	public void setSecondSample(String secondSample)
	{
		this.secondSample = secondSample;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getFigureName()
	{
		return figureName;
	}

	public void setFigureName(String figureName)
	{
		this.figureName = figureName;
	}

	public String getAttributeName()
	{
		return attributeName;
	}

	public void setAttributeName(String attributeName)
	{
		this.attributeName = attributeName;
	}

	public String getStatisticName()
	{
		return statisticName;
	}

	public void setStatisticName(String statisticName)
	{
		this.statisticName = statisticName;
	}

	public double getFirstVariance()
	{
		return firstVariance;
	}

	public void setFirstVariance(double firstVariance)
	{
		this.firstVariance = firstVariance;
	}

	public double getFirstDeviation()
	{
		return firstDeviation;
	}

	public void setFirstDeviation(double firstDeviation)
	{
		this.firstDeviation = firstDeviation;
	}

	public double getSecondVariance()
	{
		return secondVariance;
	}

	public void setSecondVariance(double secondVariance)
	{
		this.secondVariance = secondVariance;
	}

	public double getSecondDeviation()
	{
		return secondDeviation;
	}

	public void setSecondDeviation(double secondDeviation)
	{
		this.secondDeviation = secondDeviation;
	}

	public double getP_value()
	{
		return p_value;
	}

	public void setP_value(double p_value)
	{
		this.p_value = p_value;
	}
}
