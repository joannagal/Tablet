package pi.statistics.logic;

import java.util.ArrayList;

public class StatisticResult
{

	private ArrayList<Double> value = new ArrayList<Double>();

	public void clearMemory()
	{
		if (this.value != null)
		{
			this.value = null;
		}
	}

	public ArrayList<Double> getValue()
	{
		return value;
	}

	public void setValue(ArrayList<Double> value)
	{
		this.value = value;
	}

}
