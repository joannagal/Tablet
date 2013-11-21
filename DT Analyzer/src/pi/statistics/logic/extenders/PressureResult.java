package pi.statistics.logic.extenders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import pi.inputs.drawing.Figure;
import pi.inputs.drawing.PacketData;
import pi.inputs.drawing.Segment;
import pi.statistics.functions.Amplitude;
import pi.statistics.functions.Average;
import pi.statistics.functions.Collector;
import pi.statistics.functions.Max;
import pi.statistics.functions.Min;
import pi.statistics.functions.StandardDev;
import pi.statistics.functions.Variance;
import pi.statistics.logic.AttributeResult;
import pi.statistics.logic.StatisticResult;

public class PressureResult extends AttributeResult
{
	private ArrayList<PacketData> data;
	
	public PressureResult(ArrayList<PacketData> data)
	{
		this.data = data;
	}

	@Override
	public void calculateResult()
	{
		int size = data.size();

		StatisticResult collectorResult = new StatisticResult();
		StatisticResult minResult = new StatisticResult();
		StatisticResult maxResult = new StatisticResult();
		StatisticResult amplitudeResult = new StatisticResult();
		StatisticResult avgResult = new StatisticResult();

		Collector.init(collectorResult, size);
		Min.init(minResult);
		Max.init(maxResult);
		Amplitude.init(amplitudeResult);
		Average.init(avgResult);
		
		
		for (int i = 0; i < size; i++)
		{
			double value = (double) data.get(i).getPkPressure();
			Collector.iterate(value);
			Min.iterate(value);
			Max.iterate(value);
			Amplitude.iterate(value);
			Average.iterate(value);
		}
		
		Average.finish();
		StatisticResult varianceResult = new StatisticResult();
		Variance.init(varianceResult, avgResult.getValue().get(0));
		
		for (int i = 0; i < size; i++)
		{
			double value = (double) data.get(i).getPkPressure();
			Variance.iterate(value);
		}
		
		Variance.finish();
		
		StatisticResult standardDevResult = new StatisticResult();
		StandardDev.init(standardDevResult, varianceResult.getValue().get(0));
		
		
		this.value.put("Collector", collectorResult);
		this.value.put("Min", minResult);
		this.value.put("Max", maxResult);
		this.value.put("Amplitude", amplitudeResult);
		this.value.put("Average", avgResult);
		this.value.put("Variance", varianceResult);
		this.value.put("StandardDev", standardDevResult);
	}
}
