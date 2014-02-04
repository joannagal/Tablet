package pi.statistics.functions;

import java.util.ArrayList;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

import pi.statistics.logic.StatisticResult;

public class FFT
{

	static StatisticResult result;

	static public void init(StatisticResult input, ArrayList<Double> data,
			double freq)
	{
		result = input;
		input.setValue(data);

		DoubleFFT_1D transform = new DoubleFFT_1D(data.size());

		double[] toArray = new double[data.size() * 2];
		for (int i = 0; i < data.size(); i++)
		{
			toArray[2 * i] = data.get(i);
			toArray[2 * i + 1] = 0;
		}

		transform.complexForward(toArray);

		ArrayList<Double> output = new ArrayList<Double>(data.size() * 2);
		for (int i = 0; i < data.size() / 2; i++)
		{

			output.add((double) i * freq / (double) data.size());
			output.add(FFT.getLength(toArray[2 * i], toArray[2 * i + 1]));
		}

		input.setValue(output);

	}

	static public double getLength(double a, double b)
	{
		return Math.sqrt(a * a + b * b);
	}

}
