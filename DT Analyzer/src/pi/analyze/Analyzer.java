package pi.analyze;

import pi.population.Population;

public interface Analyzer
{
	public AnalysisResult analyse(Population firstPopulation,
			Population secondPopulation);
}
