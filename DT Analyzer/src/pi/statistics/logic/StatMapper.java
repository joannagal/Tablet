package pi.statistics.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StatMapper
{

	public static String[] figureNames =
	{ "ZigZag", "Circle-Left", "Circle-Right", "First Line", "Second Line",
			"Broken Line", "Spiral-In", "Spiral-Out" };

	public static String[] attributeNames =
	{ "Figure Standards", "Pressure", "Momentary Speed", "Acceleration",
			"Direction Change (f'')", "Azimuth", "Altitude",  "Dev. from Average" };

	public static String[] statisticNames =
	{ "Min", "Max", "Amplitude", "Average", "Median", "Variance",
			"StandardDev", "Drawing time", "Drawing length", "Avg Speed",
			"Breaks Amount", "FFT Freq" };

	static Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> getMap(
			String[] where)
	{
		Map<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>> map = new HashMap<String, Map<String, Map<String, Map<String, LinkedList<Double>>>>>();

		for (int i = 0; i < where.length; i++)
		{
			Map<String, Map<String, Map<String, LinkedList<Double>>>> toAdd = new HashMap<String, Map<String, Map<String, LinkedList<Double>>>>();
			map.put(where[i], toAdd);
			StatMapper.initFigureMap(toAdd);
		}

		return map;
	}

	public static void initFigureMap(
			Map<String, Map<String, Map<String, LinkedList<Double>>>> sideMap)
	{
		for (int i = 0; i < figureNames.length; i++)
		{
			Map<String, Map<String, LinkedList<Double>>> figureMap = new HashMap<String, Map<String, LinkedList<Double>>>();
			sideMap.put(figureNames[i], figureMap);
			StatMapper.initAttributeMap(figureMap);
		}
	}

	public static void initAttributeMap(
			Map<String, Map<String, LinkedList<Double>>> figureMap)
	{
		for (int i = 0; i < attributeNames.length; i++)
		{
			Map<String, LinkedList<Double>> attributeMap = new HashMap<String, LinkedList<Double>>();
			figureMap.put(attributeNames[i], attributeMap);
			StatMapper.initStatisticMap(attributeMap);
		}
	}

	public static void initStatisticMap(
			Map<String, LinkedList<Double>> attributeMap)
	{
		for (int i = 0; i < statisticNames.length; i++)
		{
			LinkedList<Double> statistic = new LinkedList<Double>();
			attributeMap.put(statisticNames[i], statistic);
		}
	}

}
