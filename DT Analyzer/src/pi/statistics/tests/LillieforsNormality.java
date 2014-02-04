package pi.statistics.tests;

import org.apache.commons.math3.distribution.NormalDistribution;

import pi.statistics.functions.Average;
import pi.statistics.functions.Variance;
import pi.statistics.logic.StatisticResult;

public class LillieforsNormality
{
	private static double[] value;
	private static double[] frequency;

	public static double statistics = 0.0d;
	private static int populationSize = 0;

	private static double average = 0.0d;
	private static double deviation = 0.0d;

	public static void compute(double[] input, int ranges, boolean debug)
	{
		if (ranges < 4)
			return;

		LillieforsNormality.prepareData(input, ranges);

		if ((LillieforsNormality.deviation >= -0.0001d)
				&& (LillieforsNormality.deviation <= 0.0001d))
			return;

		// calc cumulated
		int size = value.length;
		double[] cumulatedFreq = new double[size];
		cumulatedFreq[0] = LillieforsNormality.value[0];
		for (int i = 1; i < size; i++)
			cumulatedFreq[i] = cumulatedFreq[i - 1]
					+ LillieforsNormality.value[i];

		// calc standarized
		double[] standard = new double[size];
		for (int i = 0; i < size; i++)
			standard[i] = (LillieforsNormality.value[i] - LillieforsNormality.average)
					/ LillieforsNormality.deviation;

		// calc cumulated prob
		NormalDistribution normalDist = new NormalDistribution();
		double[] cumulatedNormal = new double[size];
		for (int i = 0; i < size; i++)
			cumulatedNormal[i] = normalDist.cumulativeProbability(standard[i]);

		// calc cumulated prob from input
		double[] cumulatedInput = new double[size];
		for (int i = 0; i < size; i++)
			cumulatedInput[i] = cumulatedFreq[i] / cumulatedFreq[size - 1];

		double maxDist = -1.0d;

		for (int i = 0; i < size; i++)
		{
			double d = cumulatedNormal[i] - cumulatedInput[i];
			if (d < 0.0d)
				d *= -1.0d;
			if (d > maxDist)
				maxDist = d;
		}

		LillieforsNormality.statistics = maxDist;

		if (debug)
		{
			System.out.printf("A - %f   D  %f\n", LillieforsNormality.average,
					LillieforsNormality.deviation);
			for (int i = 0; i < size; i++)
			{
				System.out.printf("%d. - %f %f %f %f %f %f\n", i,
						LillieforsNormality.value[i],
						LillieforsNormality.frequency[i], cumulatedFreq[i],
						standard[i], cumulatedNormal[i], cumulatedInput[i]);
			}
			System.out.printf("D - %f %f\n", maxDist,
					LillieforsNormality.getCritFromTable(
							LillieforsNormality.populationSize, 0.05d));
		}

	}

	public static boolean isTrueForAlpha(double alpha)
	{
		double crit = LillieforsNormality.getCritFromTable(
				LillieforsNormality.populationSize, alpha);

		if (LillieforsNormality.statistics > crit)
			return false;
		return true;
	}

	private static void prepareData(double[] input, int ranges)
	{
		LillieforsNormality.value = new double[ranges];
		LillieforsNormality.frequency = new double[ranges];
		for (int i = 0; i < ranges; i++)
		{
			LillieforsNormality.value[0] = 0.0d;
			LillieforsNormality.frequency[0] = 0.0d;
		}

		double min = 1000000.0d;
		double max = -1000000.0d;
		int size = input.length;
		LillieforsNormality.populationSize = size;

		StatisticResult avg = new StatisticResult();
		Average.init(avg);

		for (int i = 0; i < size; i++)
		{
			if (input[i] < min)
				min = input[i];
			if (input[i] > max)
				max = input[i];
			Average.iterate(input[i]);
		}
		Average.finish();

		StatisticResult var = new StatisticResult();
		Variance.init(var, avg.getValue().get(0));
		for (int i = 0; i < size; i++)
		{
			Variance.iterate(input[i]);
		}
		Variance.finish();

		LillieforsNormality.average = avg.getValue().get(0);
		LillieforsNormality.deviation = Math.sqrt(var.getValue().get(0));

		double range = (max - min) / (double) ranges;
		double value = 0.0d;

		for (int j = 1; j <= ranges; j++)
		{
			LillieforsNormality.value[j - 1] = min + (double) j * range - range
					/ 2.0d;
		}

		for (int i = 0; i < size; i++)
		{
			value = input[i];
			for (int j = 1; j <= ranges; j++)
			{
				if (value <= min + range * (double) j)
				{
					LillieforsNormality.frequency[j - 1]++;
					break;
				}
			}
		}
	}

	private static double getCritFromTable(int population, double alpha)
	{
		// α = .20 α = .15 α = .10 α = .05 α = .01
		if (population < 4)
			return 1.0d;
		if (alpha > 0.2d)
			return 0.0d;
		if (alpha < 0.01d)
			return 1.0d;

		double[] alphas =
		{ 0.2d, 0.15d, 0.1d, 0.05d, 0.01d };
		double value = 0.0;

		for (int i = 1; i < 5; i++)
		{
			if (alpha >= alphas[i])
			{
				double dY = 0.0d, dX = 0.0d, bY = 0.0d;
				dX = alphas[i] - alphas[i - 1];

				if (population > 50)
				{

					bY = LillieforsNormality.critTable[51][i - 1];
					dY = (LillieforsNormality.critTable[51][i] - LillieforsNormality.critTable[51][i - 1]);
				} else
				{
					bY = LillieforsNormality.critTable[population][i - 1];
					dY = (LillieforsNormality.critTable[population][i] - LillieforsNormality.critTable[population][i - 1]);
				}



				double propX = (alpha - alphas[i - 1]) / (dX);
				dY *= propX;

				value = bY + dY;

				break;
			}
		}

		if (population > 50)
		{
			double fn = (0.83d + (double) population) / Math.sqrt(population)
					- 0.1d;
			return value / fn;

		} else
			return value;
	}

	private static double[][] critTable =
	{
	{ .0, .0, .0, .0, .0 },
	{ .0, .0, .0, .0, .0 },
	{ .0, .0, .0, .0, .0 },
	{ .0, .0, .0, .0, .0 },
	{ .3027, .3216, .3456, .3754, .4129 },
	{ .2893, .3027, .3188, .3427, .3959 },
	{ .2694, .2816, .2982, .3245, .3728 },
	{ .2521, .2641, .2802, .3041, .3504 },
	{ .2387, .2502, .2649, .2875, .3331 },
	{ .2273, .2382, .2522, .2744, .3162 },
	{ .2171, .2273, .2410, .2616, .3037 },
	{ .2080, .2179, .2306, .2506, .2905 },
	{ .2004, .2101, .2228, .2426, .2812 },
	{ .1932, .2025, .2147, .2337, .2714 },
	{ .1869, .1959, .2077, .2257, .2627 },
	{ .1811, .1899, .2016, .2196, .2545 },
	{ .1758, .1843, .1956, .2128, .2477 },
	{ .1711, .1794, .1902, .2071, .2408 },
	{ .1666, .1747, .1852, .2018, .2345 },
	{ .1624, .1700, .1803, .1965, .2285 },
	{ .1589, .1666, .1764, .1920, .2226 },
	{ .1553, .1629, .1726, .1881, .2190 },
	{ .1517, .1592, .1690, .1840, .2141 },
	{ .1484, .1555, .1650, .1798, .2090 },
	{ .1458, .1527, .1619, .1766, .2053 },
	{ .1429, .1498, .1589, .1726, .2010 },
	{ .1406, .1472, .1562, .1699, .1985 },
	{ .1381, .1448, .1533, .1665, .1941 },
	{ .1358, .1423, .1509, .1641, .1911 },
	{ .1334, .1398, .1483, .1614, .1886 },
	{ .1315, .1378, .1460, .1590, .1848 },
	{ .1291, .1353, .1432, .1559, .1820 },
	{ .1274, .1336, .1415, .1542, .1798 },
	{ .1254, .1314, .1392, .1518, .1770 },
	{ .1236, .1295, .1373, .1497, .1747 },
	{ .1220, .1278, .1356, .1478, .1720 },
	{ .1203, .1260, .1336, .1454, .1695 },
	{ .1188, .1245, .1320, .1436, .1677 },
	{ .1174, .1230, .1303, .1421, .1653 },
	{ .1159, .1214, .1288, .1402, .1634 },
	{ .1147, .1204, .1275, .1386, .1616 },
	{ .1131, .1186, .1258, .1373, .1599 },
	{ .1119, .1172, .1244, .1353, .1573 },
	{ .1106, .1159, .1228, .1339, .1556 },
	{ .1095, .1148, .1216, .1322, .1542 },
	{ .1083, .1134, .1204, .1309, .1525 },
	{ .1071, .1123, .1189, .1293, .1512 },
	{ .1062, .1113, .1180, .1282, .1499 },
	{ .1047, .1098, .1165, .1269, .1476 },
	{ .1040, .1089, .1153, .1256, .1463 },
	{ .1030, .1079, .1142, .1246, .1457 },
	{ .741, .775, .819, .895, 1.035 } };

}
