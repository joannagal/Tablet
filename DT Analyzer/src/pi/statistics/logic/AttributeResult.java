package pi.statistics.logic;

import java.util.HashMap;
import java.util.Map;


public class AttributeResult {
	
		protected Map <String, StatisticResult> value;
      
		
		public void calculateResult(boolean projectLevel)
		{
			this.value = new HashMap<String, StatisticResult>();
		}

		public Map <String, StatisticResult> getValue()
		{
			return value;
		}

		public void setValue(Map <String, StatisticResult> value)
		{
			this.value = value;
		}
        
 
}
