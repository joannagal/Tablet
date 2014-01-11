package pi.statistics.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class StatMapper
{

	public static String[] figureNames =
	{ "All Figures", "ZigZag", "Circle-Left", "Circle-Right", "First Line", "Second Line",
			"Broken Line", "Spiral-In", "Spiral-Out" };

	public static Map<String, Boolean> figureAvaible = new HashMap<String, Boolean>(
			figureNames.length);

	public static String[] attributeNames =
	{ "Figure Standards", "Pressure", "Momentary Speed", "Acceleration",
			"Direction Change (f'')", "Azimuth", "Altitude",
			"Dev. from Average" };

	public static Map<String, Boolean> attributeAvaible = new HashMap<String, Boolean>(
			figureNames.length);

	public static String[] statisticNames =
	{ "Min", "Max", "Amplitude", "Average", "Median", "Variance",
			"StandardDev", "Drawing time", "Drawing length", "Avg Speed",
			"Breaks Amount", "FFT Freq" };

	public static Map<String, Boolean> statisticAvaible = new HashMap<String, Boolean>(
			figureNames.length);

	public static int getFigureAvaibles()
	{
		int result = 0;
		for (int i = 0; i < figureNames.length; i++)
			if (figureAvaible.get(figureNames[i])) result++;
		return result;
	}
	
	public static int getAttributeAvaibles()
	{
		int result = 0;
		for (int i = 0; i < attributeNames.length; i++)
			if (attributeAvaible.get(attributeNames[i])) result++;
		return result;
	}
	
	public static int getStatisticAvaibles()
	{
		int result = 0;
		for (int i = 0; i < statisticNames.length; i++)
			if (statisticAvaible.get(statisticNames[i])) result++;
		return result;
	}
	
	static
	{
		
		for (int i = 0; i < figureNames.length; i++)
			figureAvaible.put(figureNames[i], false);
		
		for (int i = 0; i < attributeNames.length; i++)
			attributeAvaible.put(attributeNames[i], true);
		
		for (int i = 0; i < statisticNames.length; i++)
			statisticAvaible.put(statisticNames[i], true);
		
		figureAvaible.put("All Figures", true);
		statisticAvaible.put("Min", false);
		statisticAvaible.put("Max", false);
		statisticAvaible.put("Amplitude", false);
		statisticAvaible.put("Median", false);
	}

	// amplitude if min, max
	// stdev, variance if average

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
