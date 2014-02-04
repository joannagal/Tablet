package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;

public class AttributeResult
{

	protected Map<String, StatisticResult> value;

	public void clearMemory()
	{
		if (this.value != null)
		{
			for (Map.Entry<String, StatisticResult> entry : this.value
					.entrySet())
			{
				entry.getValue().clearMemory();
			}

			this.value = null;
		}
	}

	public void calculateResult(boolean projectLevel)
	{
		this.value = new HashMap<String, StatisticResult>();
	}

	public Map<String, StatisticResult> getValue()
	{
		return value;
	}

	public void setValue(Map<String, StatisticResult> value)
	{
		this.value = value;
	}

}
