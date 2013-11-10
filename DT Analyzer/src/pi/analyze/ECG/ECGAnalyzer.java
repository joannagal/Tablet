package pi.analyze.ECG;

import pi.analyze.AnalysisResult;
import pi.analyze.Analyzer;
import pi.population.Population;

public class ECGAnalyzer implements Analyzer
{

	@Override
	public AnalysisResult analyse(Population firstPopulation,
			Population secondPopulation)
	{
		ECGAnalysisResult result = new ECGAnalysisResult();
		
		return result;
	}
	
}
