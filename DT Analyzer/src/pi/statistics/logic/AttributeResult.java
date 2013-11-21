package pi.statistics.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pi.inputs.drawing.PacketData;

public class AttributeResult {
	
		protected Map <String, StatisticResult> value = new HashMap<String, StatisticResult>();
      
		
		public void calculateResult()
		{

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
